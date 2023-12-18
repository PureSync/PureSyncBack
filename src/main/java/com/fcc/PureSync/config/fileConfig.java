package com.fcc.PureSync.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:/application-secret.properties")
public class fileConfig implements WebMvcConfigurer{

    @Value("${fileUploadPath}")
    String fileUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(fileUploadPath, registry);
    }

    void exposeDirectory(String dirName,
                         ResourceHandlerRegistry registry)
    {
        Path uploadDir = Paths.get(dirName);
        //업로드 폴더의 물리적 구조(절대경로확인)
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if(dirName.startsWith("../")) //상대적 경로가 왔을띠 ../  => /
            dirName = dirName.replace("../", "");

        //System.out.println(dirName);
        //System.out.println(uploadPath);

        registry
                .addResourceHandler("/"+dirName+"/**")
                .addResourceLocations("file:/"+uploadPath + "/");

        //spring frame
        //특정폴더를 파일서버로 작동시킨다.
        //application.properties  에서  fileUploadPath에
        //지정된 폴더값을 읽어서 그 폴더의 권한을 파일서버로 만든다

    }
}
