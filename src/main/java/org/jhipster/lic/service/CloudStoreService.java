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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

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

    public List<FileDTO> getFiles(UserDTO userDTO,  Long directoryId) {
        User user = userRepository.findOneByToken(userDTO.getToken());
        List<File> filesDAO = fileRepository.findAllByUserCodeAndDirectoryID(user.getUserCode(), directoryId);
        List<FileDTO> filesDTO = new ArrayList<>();
        for(File file: filesDAO) {
            filesDTO.add(new FileDTO(file));
        }
        return filesDTO;
    }

    public boolean createDirectory(DirectoryDTO directoryDTO){
        Directory directory = new Directory();
        directory.setId(Long.parseLong(getNextDirectoryId() + ""));
        directory.setDirectoryName(directoryDTO.getDirectoryName());
        directory.setDirectoryParent(directoryDTO.getDirectoryParent());
        directory.setUserCode(directoryDTO.getUserCode());
        directory.setDirectoryUrl(directoryDTO.getDirectoryUrl());
        directory.setCreationDate(Instant.now());
        directoryRepository.save(directory);
        return true;
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
                    deleteStoredDirectory(elem.getDirectoryUrl(), elem.getDirectoryName());
                } else {
                    queue.add(elem.getId());
                }
            }
            queue.remove(current);
            if(queue.size() == 0)
                break;
        }
        directoryRepository.delete(directory);
        deleteStoredDirectory(directory.getDirectoryUrl(), directory.getDirectoryName());
        return true;
    }

    public boolean deleteFile(UserDTO userDTO, Long fileId){
        User user = userRepository.findOneByToken(userDTO.getToken());
        File file = fileRepository.findByIdAndUserCode(fileId, user.getUserCode());
        fileRepository.delete(file);
        return deleteStoredDirectory(file.getFileURL(), file.getFileName());
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

    private boolean deleteStoredDirectory(String url, String name){

        return true;
    }

    public boolean moveDirectories(UserDTO userDTO, String[] idList, String parentId){
        User user = userRepository.findOneByToken(userDTO.getToken());
        for(String elem : idList) {
            Directory directory = directoryRepository.findByIdAndUserCode(Long.parseLong(elem), user.getUserCode());
            if(parentId != null)
                directory.setDirectoryParent(Long.parseLong(parentId));
            else {
                directory.setDirectoryParent(null);
            }
            directoryRepository.save(directory);
            deleteStoredDirectory(directory.getDirectoryUrl(), directory.getDirectoryName());
        }

        return true;
    }
}
