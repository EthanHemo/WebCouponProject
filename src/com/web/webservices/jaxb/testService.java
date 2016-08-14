package com.web.webservices.jaxb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;







@Path("/test")
public class testService {
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String test( @FormDataParam("pic") InputStream uploadedInputStream,
						@FormDataParam("pic") FormDataContentDisposition fileDetail){
		
		String uploadedFileLocation = "/images/" + fileDetail.getFileName();
		System.out.println(uploadedFileLocation);
		return "hi";
		
		/*
		String uploadedFileLocation = "/images/" + fileDetail.getFileName();
		System.out.println(uploadedFileLocation);
		
		File  objFile=new File(uploadedFileLocation);
	    if(objFile.exists())
	    {
	        return "file exist";

	    }
		
	    saveToFile(uploadedInputStream, uploadedFileLocation);
	    
	    String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;
	    System.out.println(output);
	    return output;
	    */
	}
	
	private void saveToFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {

	    try {
	        OutputStream out = null;
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	    } catch (IOException e) {

	        e.printStackTrace();
	    }

	}
	
}
