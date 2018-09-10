package org.jhipster.lic.service.util;

import org.apache.commons.compress.utils.IOUtils;
import org.jhipster.lic.service.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.jhipster.lic.config.Constants.DOWNLOAD_PATH;

/**
 * Created by Madalin on 9/8/2018.
 */
public class CreateFiles {

    public boolean createDir(String path, String name) {
        return new File(path + File.separator + name).mkdirs();
    }

    public String createFile(String path, MultipartFile uploadedFile) {
        String orgName = uploadedFile.getOriginalFilename();
        String filePath = path + orgName;
        try {
            File dest = new File(filePath);
            uploadedFile.transferTo(dest);
            return "SUCCESS";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public MultipartFile getFile(String path, String name) throws IOException {
            String filePath = path + name;
            File file = new File(filePath);
            FileInputStream input = new FileInputStream(file);
            MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
            input.close();
            return mockMultipartFile;
    }

    public boolean deleteFile(String path, String name) {
        String filePath = path + name;
        try {
            Files.delete(Paths.get(filePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean move(String src, String dst){
        try {
            Files.move(Paths.get(src), Paths.get(dst), REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
