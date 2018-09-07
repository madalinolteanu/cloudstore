package org.jhipster.lic.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.lic.service.CloudStoreService;

import org.jhipster.lic.service.UserService;
import org.jhipster.lic.service.dto.CloudStoreDTO;
import org.jhipster.lic.service.dto.DirectoryDTO;
import org.jhipster.lic.service.dto.FileDTO;
import org.jhipster.lic.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/cloudstore")
public class CloudStoreController {

    private final Logger log = LoggerFactory.getLogger(CloudStoreController.class);

    private final UserService userService;

    private final CloudStoreService cloudStoreService;

    public CloudStoreController(UserService userService, CloudStoreService cloudStoreService) {

        this.userService = userService;
        this.cloudStoreService = cloudStoreService;
    }

    @PostMapping("/file/upload")
    @Timed
    public void uploadFile(@RequestParam(value = "token") String token, @Valid @RequestBody FileDTO fileDTO){
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            fileDTO.setUserCode(userService.getUserCodeByToken(token));
            cloudStoreService.uploadFile(fileDTO);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam(value = "token") String token,
                                @RequestParam(value = "directoryId") Integer directoryId,
                                @RequestParam(value = "fileId") Integer fileId,
                                @RequestParam("uploadingFiles") MultipartFile uploadedFile) throws IOException {
            File file = new File(uploadedFile.getOriginalFilename());
        return "redirect:/";
    }

    /**
     * GET  /directories : get all directories
     *
     * @param token the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/directories")
    @Timed
    public CloudStoreDTO getDirectories(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "directoryId") Long directoryId) {
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            if(directoryId.equals((long)-1))
                directoryId = null;
            List<DirectoryDTO> directories = cloudStoreService.getDirectories(userDTO, directoryId);
            response.setDirectories(directories);
            response.setSuccessMessage("Directories Found");
            response.setErrorCode(200);
        }
        return response;
    }

    /**
     * GET  /files : get all files
     *
     * @param token the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/files")
    @Timed
    public CloudStoreDTO getFiles(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "directoryId") Long directoryId) {
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            if(directoryId.equals(-1))
                directoryId = null;
            List<FileDTO> files = cloudStoreService.getFiles(userDTO, directoryId);
            response.setFiles(files);
            response.setSuccessMessage("Files Found!");
            response.setErrorCode(200);
        }
        return response;
    }

    @PostMapping("/directory/create")
    @Timed
    public CloudStoreDTO createDirectory(@RequestParam(value = "token") String token, @Valid @RequestBody DirectoryDTO directoryDTO){
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            directoryDTO.setUserCode(userService.getUserCodeByToken(token));
            if(directoryDTO.getDirectoryParent() == -1)
                directoryDTO.setDirectoryParent(null);
            if(cloudStoreService.createDirectory(directoryDTO)){
                response.setSuccessMessage("SUCCESS");
                response.setSuccessCode(200);
            } else {
                response.setErrorMessage("Error!! Add folder action failed!");
                response.setErrorCode(500);
            }
        } else {
            response.setErrorMessage("Error!! User not found!");
            response.setErrorCode(500);
        }
        return response;
    }

    @PostMapping("/directory/delete")
    @Timed
    public CloudStoreDTO deleteDirectory(@RequestParam(value = "token") String token, @Valid @RequestBody String directoryId){
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            if(cloudStoreService.deleteDirectory(userDTO, Long.parseLong(directoryId))){
                response.setSuccessMessage("SUCCESS");
                response.setSuccessCode(200);
            } else {
                response.setErrorMessage("Error!! Delete folder action failed!");
                response.setErrorCode(500);
            }
        } else {
            response.setErrorMessage("Error!! User not found!");
            response.setErrorCode(500);
        }
        return response;
    }

    @PostMapping("/file/delete")
    @Timed
    public CloudStoreDTO deleteFile(@RequestParam(value = "token") String token, @Valid @RequestBody String fileId){
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            if(cloudStoreService.deleteFile(userDTO, Long.parseLong(fileId))){
                response.setSuccessMessage("SUCCESS");
                response.setSuccessCode(200);
            } else {
                response.setErrorMessage("Error!! Delete folder action failed!");
                response.setErrorCode(500);
            }
        } else {
            response.setErrorMessage("Error!! User not found!");
            response.setErrorCode(500);
        }
        return response;
    }

}
