package com.image.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.Servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication
@RestController
public class FoleimageUploadApplication {

	static	String filepathpath="src/main/resources/static/";

	public static void main(String[] args) {
		SpringApplication.run(FoleimageUploadApplication.class, args);
		System.out.println("hello - world");
		System.out.println(filepathpath);
	}
	
	
	/*
	
	@GetMapping("/folder/{username}")
	public String createfilder(@PathVariable("username") String name) {
		String folderName = name;		
		try {

            Path path = Paths.get(filepathpath+folderName);

            //java.nio.file.Files;
            Files.createDirectories(path);

            System.out.println("Directory is created!");

        } catch (IOException e) {

            System.err.println("Failed to create directory!" + e.getMessage());

        }
		
		
		return "folder create sucessfully" ;
	}
	
	*/
	public boolean uploadFile(MultipartFile file) {

		boolean f = false;
		try {
		Files.copy(file.getInputStream(),Paths.get(filepathpath+File.separator+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
		f=true;
		}catch(Exception e) {e.printStackTrace();}
		
		return f;
	}
	
	 
	@GetMapping("/upload")
	public ResponseEntity<String> uploadimage(@RequestParam("file") MultipartFile file){
			
	if(file.isEmpty()) {
		return ResponseEntity.ok("fle should not be empty");
	}
	
	/*
	if(!file.getContentType().equals("image")) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("could hello not upload file");
		
	}
	*/
		
	//file uploade code
     try {
	    boolean	f = uploadFile(file);
	    if(f) {
	    System.out.println("file uplads");
	    return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("src/main/resources/static/").path(file.getOriginalFilename()).toUriString());
	    }
     }
     catch(Exception e){
    	 e.printStackTrace();
     }
     
     
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("simething went wromng");
     }


	
	

}
