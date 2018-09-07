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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
}
