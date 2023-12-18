package com.fcc.PureSync.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    private FileUploadUtil() {}
    //생성자를 private 로 만들어서 객체생성을 막도록 한다

    //4.gif-> 4(1).gif, 4(2).gif 또는 2022081212222.ㅎgif
    public static String getFileName(String contextPath, String orifilename)
    {
        System.out.println(contextPath);
        System.out.println(orifilename);

        String filePath = contextPath; //내 물리적 경로
        //1.파일명을 확장자와 파일명으로 쪼갠다.
        int pos = orifilename.lastIndexOf(".");
        String ext = orifilename.substring(pos+1);//확장자
        String orifile = orifilename.substring(0, pos);//파일명
        System.out.println(ext);
        System.out.println(orifilename);

        String filename = orifilename;

        File newFile = new File(filePath + "/"+filename);
        int i=1;
        while(newFile.exists()) //파일이 존재하면
        {
            filename = orifile + "("+i+")." +ext;
            i++;
            newFile = new File(filePath + "/"+filename);
        }

        return filename; //새로운 파일명
    }


    public static String upload(String uploadDir, MultipartFile multipartFile )
    {
        //스프링 부트 이전에는 파일 업로드가 반드시 물리적경로(절대적경로)를 알아야 됐었다
        //이제는 바뀐다. 상대경로로 바뀌었음
        String fname = getFileName(uploadDir, multipartFile.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        if( !Files.exists(uploadPath))
        {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(InputStream inStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fname);
            Files.copy( inStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            //기존파일 덮어쓰기
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return fname;
    }



}
