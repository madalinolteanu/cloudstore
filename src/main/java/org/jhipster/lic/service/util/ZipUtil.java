package org.jhipster.lic.service.util;

/**
 * Created by Madalin on 9/10/2018.
 */
import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.util.ArrayList;

import java.util.zip.ZipEntry;

import java.util.zip.ZipInputStream;

import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static ArrayList<String> fileList = new ArrayList<>();

    private static String sourceFolder = null;

    public ZipUtil() {}

    public static String getSourceFolder() {
        return sourceFolder;
    }

    public static void setSourceFolder(String sourceFolder) {
        ZipUtil.sourceFolder = sourceFolder;
    }

    public static boolean zip(String zipFile, String source) throws IOException{

        byte[] buffer = new byte[1024];

        FileInputStream fis = null;

        ZipOutputStream zos = null;

        fileList = new ArrayList();

        try{

            zos = new ZipOutputStream(new FileOutputStream(zipFile));

            updateSourceFolder(new File(source));

            if (sourceFolder == null) {

                zos.close();

                return false;

            }

            generateFileAndFolderList(new File(source));

            for (String unzippedFile: fileList) {

                System.out.println(sourceFolder + unzippedFile);

                ZipEntry entry = new ZipEntry(unzippedFile);

                zos.putNextEntry(entry);

                if ((unzippedFile.substring(unzippedFile.length()-1)).equals(File.separator))continue;

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

    public static boolean unzip(String zipFile) throws IOException{

        byte[] buffer = new byte[1024];

        ZipInputStream zis = null;

        ZipEntry entry = null;

        FileOutputStream fos = null;

        fileList = new ArrayList();

        try {

            File file = new File(zipFile);

            zis = new ZipInputStream(new FileInputStream(file));

            while((entry = zis.getNextEntry())!= null){

                String dir = entry.getName();

                try{

                    String fileSeparator = dir.substring(dir.length()-1);

                    boolean isFolder=(fileSeparator.equals("/") || fileSeparator.equals("\\"));

                    if (isFolder){

                        dir = dir.substring(0,dir.lastIndexOf(fileSeparator)+1);

                        dir = (file.getParent() == null? "":file.getParent()+ File.separator) + dir;

                        (new File(dir)).mkdirs();

                        continue;

                    }

                }catch(Exception e){}finally{}

                System.out.println("dir:" + dir);

                fos = new FileOutputStream((file.getParent() == null? "":file.getParent()+ File.separator) + entry.getName());

                int len=0;

                while ((len = zis.read(buffer))>0) {

                    fos.write(buffer,0,len);

                }

                fos.close();

            }

            zis.close();

            return true;

        }catch (IOException ioe){

            ioe.printStackTrace();

            return false;

        }finally{

            fileList = null;

            sourceFolder = null;

        }

    }

//Fills filesList with file name and thier paths

    public static void generateFileAndFolderList(File node)

    {

// add file only

        if (node.getName().split(".").length > 0)

        {

            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));

        }

        if (node.getName().split(".").length == 0)

        {

            String dir = node.getAbsoluteFile().toString();

            fileList.add(dir.substring(sourceFolder.length(), dir.length()) + File.separator);

            String[] subNode = node.list();

            if (subNode != null) {
                for (String fileOrFolderName : subNode){

                    generateFileAndFolderList(new File(node, fileOrFolderName));

                }
            }

        }

    }

//Generate file name based on source folder

    private static String generateZipEntry(String file)

    {

        return file.substring(sourceFolder.length(), file.length());

    }

//Updates source folder based on source type: File or Folder

    private static void updateSourceFolder(File node){

        if (node.isFile() || node.isDirectory()) {

            String sf = node.getAbsoluteFile().toString();

            sourceFolder = sf.substring(0,(sf.lastIndexOf("/")>0?sf.lastIndexOf("/"):sf.lastIndexOf("\\")));

            sourceFolder += File.separator;

        }else sourceFolder=null;//the file does not exists

    }

}
