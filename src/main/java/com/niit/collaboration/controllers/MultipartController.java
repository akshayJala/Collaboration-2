package com.niit.collaboration.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

@MultipartConfig(fileSizeThreshold = 26214400)//25MB(Max size of uploaded file)
public class MultipartController {
	
	@CrossOrigin(origins="http://localhost:8081")
	//@CrossOrigin allows all origins and the HTTP methods specified in the @RequestMapping annotation
	@RequestMapping(value="/upload")
	public String uploadFile(@RequestParam("uploadedFile") MultipartFile uploadedFileRef) {
		
		
		String fileName = uploadedFileRef.getOriginalFilename();//name of the uploaded file
		
		
		String path = "E:/" + fileName;//set path for file to be uploaded
		
		
		byte[] buffer = new byte[1000];//store the data from uploadedFileRef
		
		
		File outputFile = new File(path);//create o/p file on server
		
		FileInputStream reader = null;
		
		FileOutputStream writer = null;
		
		int totalBytes = 0;
		
		try{
			outputFile.createNewFile();
			
			reader = (FileInputStream) uploadedFileRef.getInputStream();
			//create i/p and o/p stream to file for reading it from uploadedFileRef
		writer = new FileOutputStream(outputFile);	
		
		int byteRead = 0;
		//start iteration, read data from uploadedFileRef and write it to OutputFile
		while  ((byteRead = reader.read(buffer)) != -1) {
			writer.write(buffer);
			totalBytes += byteRead;
		}
		}catch(IOException e){
			 e.printStackTrace();
		}finally{
			try{
				reader.close();
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return "Successfully uploaded file of size  " + totalBytes;
	}

}
