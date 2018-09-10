package org.jhipster.lic.service;

import org.jhipster.lic.domain.Directory;
import org.jhipster.lic.domain.File;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.DirectoryRepository;
import org.jhipster.lic.repository.FileRepository;
import org.jhipster.lic.repository.UserRepository;
import org.jhipster.lic.security.DataBlocDTO;
import org.jhipster.lic.security.jwt.TokenProvider;
import org.jhipster.lic.service.dto.CloudStoreDTO;
import org.jhipster.lic.service.dto.DirectoryDTO;
import org.jhipster.lic.service.dto.FileDTO;
import org.jhipster.lic.service.dto.UserDTO;
import org.jhipster.lic.service.util.CreateFiles;
import org.jhipster.lic.service.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.jhipster.lic.config.Constants.DOWNLOAD_PATH;
import static org.jhipster.lic.config.Constants.SERVER_PATH;
import static org.jhipster.lic.config.Constants.UPLOAD_PATH;
import static org.jhipster.lic.service.util.ZipUtil.setSourceFolder;

/**
 * Service class for managing DataBlocs.
 */
@Service
@Transactional
public class CloudStoreService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final DirectoryRepository directoryRepository;

    private final FileRepository fileRepository;

    private final UserRepository userRepository;

    private ArrayList<String> fileList;

    private String sourceFolder;

    public CloudStoreService(DirectoryRepository directoryRepository,
                             FileRepository fileRepository, UserRepository userRepository) {
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public void uploadFile(FileDTO fileDTO){
        File file = new File();
        file.setId(Long.parseLong(getNextFileId() + ""));
        file.setFileName(fileDTO.getFileName());
        file.setFileType(fileDTO.getFileType());
        file.setDirectoryID(fileDTO.getDirectoryId());
        file.setFileURL(fileDTO.getFileUrl());
        file.setUserCode(fileDTO.getUserCode());
        file.setCreationDate(Instant.now());
        fileRepository.save(file);
    }

    public List<DirectoryDTO> getDirectories(UserDTO userDTO, Long directoryId) {
        User user = userRepository.findOneByToken(userDTO.getToken());
        List<Directory> directoriesDAO = directoryRepository.findAllByUserCodeAndDirectoryParent(user.getUserCode(), directoryId);
        List<DirectoryDTO> directoriesDTO = new ArrayList<>();
        for(Directory directory: directoriesDAO) {
            directoriesDTO.add(new DirectoryDTO(directory));
        }
        return directoriesDTO;
    }

    public List<FileDTO> getFiles(UserDTO userDTO, Long directoryId, String basePath) {
        User user = userRepository.findOneByToken(userDTO.getToken());
        List<File> filesDAO = fileRepository.findAllByUserCodeAndDirectoryID(user.getUserCode(), directoryId);
        List<FileDTO> filesDTO = new ArrayList<>();
        FileDTO fileDTO;
        for(File file: filesDAO) {
            fileDTO =  new FileDTO(file);
            fileDTO.setData(getStoredFile(basePath + "/" +
                user.getUserCode() + file.getFileURL(), file.getFileName()));
            filesDTO.add(fileDTO);
        }
        return filesDTO;
    }

    public boolean createDirectory(String path, DirectoryDTO directoryDTO){
        Directory directory = new Directory();
        directory.setId(Long.parseLong(getNextDirectoryId() + ""));
        directory.setDirectoryName(directoryDTO.getDirectoryName());
        directory.setDirectoryParent(directoryDTO.getDirectoryParent());
        directory.setUserCode(directoryDTO.getUserCode());
        directory.setDirectoryUrl(directoryDTO.getDirectoryUrl());
        directory.setCreationDate(Instant.now());
        directoryRepository.save(directory);
        return createDirToPath(path, new DirectoryDTO(directory));
    }

    public boolean deleteDirectory(UserDTO userDTO, Long directoryId){
        User user = userRepository.findOneByToken(userDTO.getToken());
        Directory directory = directoryRepository.findByIdAndUserCode(directoryId, user.getUserCode());
        Queue<Long> queue = new PriorityQueue<>();
        Long current;
        queue.add(directoryId);
        while(true){
            current = queue.peek();
            List<Directory> directories = directoryRepository.findByDirectoryParentAndUserCode(current, user.getUserCode());
            for(Directory elem : directories) {
                if(directoryRepository.findByDirectoryParentAndUserCode(elem.getId(), user.getUserCode()).size() == 0){
                    directoryRepository.delete(elem);
                    deleteStoredFile(elem.getDirectoryUrl(), elem.getDirectoryName());
                } else {
                    queue.add(elem.getId());
                }
            }
            queue.remove(current);
            if(queue.size() == 0)
                break;
        }
        directoryRepository.delete(directory);
        deleteStoredFile(directory.getDirectoryUrl(), directory.getDirectoryName());
        return true;
    }

    public boolean deleteFile(UserDTO userDTO, Long fileId, String basePath){
        User user = userRepository.findOneByToken(userDTO.getToken());
        File file = fileRepository.findByIdAndUserCode(fileId, user.getUserCode());
        fileRepository.delete(file);
        return deleteStoredFile(basePath + file.getFileURL(), file.getFileName());
    }

    private Integer getNextFileId(){
        List<File> allBrList = this.fileRepository.findAll();
        File file = null;
        Optional<File> fileStream = allBrList.stream()
            .sorted(Comparator.comparing(File::getId).reversed()).findFirst();


        if(fileStream != null && fileStream.isPresent()){
            file = fileStream.get();
        }

        return (file!=null && file.getId()!=null ? Math.max(Integer.parseInt(file.getId() + ""), 100000) : 100000)+1;
    }

    private Integer getNextDirectoryId(){
        List<Directory> allBrList = this.directoryRepository.findAll();
        Directory directory = null;
        Optional<Directory> fileStream = allBrList.stream()
            .sorted(Comparator.comparing(Directory::getId).reversed()).findFirst();


        if(fileStream != null && fileStream.isPresent()){
            directory = fileStream.get();
        }

        return (directory!=null && directory.getId()!=null ? Math.max(Integer.parseInt(directory.getId() + ""), 100000) : 100000)+1;
    }

    public boolean moveDirectories(UserDTO userDTO, String[] idList, String parentId, String basePath){
        User user = userRepository.findOneByToken(userDTO.getToken());
        String source;
        String dest;
        Directory destDir;
        Directory sourceDir;
        File srcFile;
        for(String elem : idList) {
            sourceDir = directoryRepository.findByIdAndUserCode(Long.parseLong(elem), user.getUserCode());
            if(sourceDir == null){
                srcFile = fileRepository.findByIdAndUserCode(Long.parseLong(elem), userDTO.getUserCode());
                source = srcFile.getFileURL() + srcFile.getFileName();
                if(parentId == null){
                    srcFile.setFileURL("\\");
                    srcFile.setDirectoryID(null);
                    fileRepository.save(srcFile);
                    dest = "\\" + srcFile.getFileName();
                } else {
                    destDir = directoryRepository.findByIdAndUserCode(Long.parseLong(parentId), user.getUserCode());
                    srcFile.setFileURL(destDir.getDirectoryUrl());
                    srcFile.setDirectoryID(Long.parseLong(parentId));
                    fileRepository.save(srcFile);
                    dest = destDir.getDirectoryUrl() + destDir.getDirectoryName() + "\\" + srcFile.getFileName();
                }
            } else {
                source = sourceDir.getDirectoryUrl();
                if (parentId != null) {
                    sourceDir.setDirectoryParent(Long.parseLong(parentId));
                    destDir = directoryRepository.findByIdAndUserCode(Long.parseLong(parentId), user.getUserCode());
                    dest = destDir.getDirectoryUrl() + destDir.getDirectoryName();
                    sourceDir.setDirectoryUrl(dest);
                    directoryRepository.save(sourceDir);
                } else {
                    sourceDir.setDirectoryParent(null);
                    dest = "\\";
                    sourceDir.setDirectoryUrl(dest);
                    directoryRepository.save(sourceDir);
                }
            }
            moveDirectory(basePath + source, basePath + dest);
        }

        return true;
    }

    public boolean createDirToPath(String path, DirectoryDTO directory) {
        CreateFiles createFiles = new CreateFiles();
        path = path + "/" + directory.getUserCode()  + directory.getDirectoryUrl();

        return createFiles.createDir(path, directory.getDirectoryName());
    }

    public boolean createUserDir(String path, String userCode) {
        CreateFiles createFiles = new CreateFiles();
        return createFiles.createDir(path, userCode);
    }

    public String createFileToPath(String path, MultipartFile file, UserDTO userDTO, String url) {
        CreateFiles createFiles = new CreateFiles();
        path = path + "/" + userDTO.getUserCode() + url;
        return createFiles.createFile(path, file);
    }

    public boolean canCreateDir(String name, Long parentId){
        return directoryRepository.findByDirectoryNameAndDirectoryParent(name, parentId) == null;
    }

    public MultipartFile getStoredFile(String path, String fileName){
        CreateFiles createFiles = new CreateFiles();
        try {
            return createFiles.getFile(path, fileName);
        } catch( IOException e){
            return null;
        }
    }

    public String getZip(UserDTO userDTO, Long dirID, String basePath, String sourcePath){
        Directory directory = directoryRepository.findByIdAndUserCode(dirID, userDTO.getUserCode());
        setSourceFolder(sourcePath + "\\" + userDTO.getUserCode() + directory.getDirectoryUrl() + directory.getDirectoryName());
        setFileList(new ArrayList<>());
        try {
            zip(basePath + "\\" + directory.getDirectoryName() + ".zip",
                sourcePath + directory.getDirectoryUrl() + directory.getDirectoryName(), userDTO, dirID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SERVER_PATH + DOWNLOAD_PATH + directory.getDirectoryUrl() + directory.getDirectoryName() + ".zip";
    }

    public boolean deleteStoredFile(String url, String name){
        CreateFiles createFiles = new CreateFiles();
        return createFiles.deleteFile(url, name);
    }

    private boolean moveDirectory(String src, String dest){
        CreateFiles createFiles = new CreateFiles();
        return createFiles.move(src, dest);
    }

    public boolean zip(String zipFile, String source, UserDTO userDTO, Long dirId) throws IOException{

        byte[] buffer = new byte[1024];

        FileInputStream fis = null;

        ZipOutputStream zos = null;

        fileList = new ArrayList<>();

        try{

            zos = new ZipOutputStream(new FileOutputStream(zipFile));

            if (sourceFolder == null) {

                zos.close();

                return false;

            }

            generateFileAndFolderList(new java.io.File(sourceFolder), userDTO, dirId);

            for (String unzippedFile: fileList) {

                System.out.println(sourceFolder + unzippedFile);

                ZipEntry entry = new ZipEntry(unzippedFile);

                zos.putNextEntry(entry);

                if ((unzippedFile.substring(unzippedFile.length()-1)).equals(java.io.File.separator))continue;

                try{

                    fis = new FileInputStream(sourceFolder + unzippedFile);

                    int len=0;

                    while ((len = fis.read(buffer))>0) {

                        zos.write(buffer,0,len);

                    }

                }catch(IOException e){

                    e.printStackTrace();

                    return false;

                }finally{

                    if (fis!=null) fis.close();

                }

            }

            zos.closeEntry();

        }catch(IOException e){

            e.printStackTrace();

            return false;

        }finally{

            zos.close();

            fileList = null;

            sourceFolder = null;

        }

        return true;

    }

    public void generateFileAndFolderList(java.io.File node, UserDTO userDTO, Long dirId)

    {

// add file only

        if (node.getName().split("\\.").length > 1)

        {

            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));

        }

        if (node.getName().split("\\.").length == 1)

        {

            String dir = node.getAbsoluteFile().toString();

            fileList.add(dir.substring(sourceFolder.length(), dir.length()) + java.io.File.separator);

            if(dirId != null) {
                List<File> files = fileRepository.findAllByUserCodeAndDirectoryID(userDTO.getUserCode(), dirId);


                if (files != null) {
                    for (File file : files) {
                        generateFileAndFolderList(new java.io.File(node, file.getFileName()), userDTO, null);
                    }
                }

                List<Directory> directories = directoryRepository.findAllByUserCodeAndDirectoryParent(userDTO.getUserCode(), dirId);

                if (directories != null) {
                    for (Directory directory : directories) {
                        generateFileAndFolderList(new java.io.File(node, directory.getDirectoryName()), userDTO, directory.getId());
                    }
                }
            }

        }

    }

//Generate file name based on source folder

    private String generateZipEntry(String file)

    {

        return file.substring(sourceFolder.length(), file.length());

    }

    private void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    private void setFileList(ArrayList<String> fileList) {
        this.fileList = fileList;
    }
}
