package com.pradeep.demo_jwt_authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RoleCheckController {
    private static Logger log = LoggerFactory.getLogger(RoleCheckController.class);
    @Value("${video-directory}")
    private String directoryPath;
    private final Path videoDirectory= Paths.get("C:\\Users\\pradeep kumar upadhy\\Desktop\\videoInterview");
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/fetchList")
    public ResponseEntity<List<String>> listOfVideos() {
        log.info("Inside controller to fetch list of videos");
        List<String> videoFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                log.info("FileName is {} ", file.getName());
                videoFiles.add(file.getName());
            }
        }
        log.info("Size of video list is {}" , videoFiles.size());
        return ResponseEntity.ok(videoFiles);
    }

//This API will show video on browser if the user has admin role""
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> showVideo(@PathVariable String filename){
        log.info("Inside api to fetch and show video based on filename");
       try{
           Path filePath=videoDirectory.resolve(filename).normalize();

           Resource resource=new UrlResource(filePath.toUri());
           if(resource.exists() && resource.isReadable()){
               log.info("Inside reaidng and sending reponse back to user");
               return ResponseEntity.ok().contentType(MediaTypeFactory.getMediaType(resource)
                       .orElse(MediaType.APPLICATION_OCTET_STREAM))
                       .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\""+resource.getFilename()+"\"")
                       .body(resource);
           }else {
               log.info("Inside else when no content or file is found at desired place");
               return ResponseEntity.notFound().build();
           }
       }catch (MalformedURLException ex){
return ResponseEntity.badRequest().build();
       }
    }
}
