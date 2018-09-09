package org.jhipster.lic.service.util;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;

/**
 * Created by Madalin on 9/8/2018.
 */
public class CreateFiles {

    public boolean createDir(String path, String name) {
        return new File(path + File.separator + name).mkdirs();
    }

    public String createFile(String path, MultipartFile uploadedFile) {
        try {
            String orgName = uploadedFile.getOriginalFilename();
            String filePath = path + orgName;
            File old = new File(filePath);
            if(old.exists()){
                old.delete();
            }
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
            return new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
    }
}
