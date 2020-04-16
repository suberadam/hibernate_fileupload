package org.apec;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apec.hibernate.DAO.FilesDAO;
import org.apec.hibernate.entity.Files;



@WebServlet("/ImageUpload")
public class ImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String path ="C:\\Users\\zmada\\images";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());
		try {
			List<FileItem> images = upload.parseRequest(request);
			for(FileItem image:images) {
				String name = image.getName();
				name = name.substring(name.lastIndexOf("\\")+1);
				System.out.println(name);
				new FilesDAO().addFileDetails(new Files(name));
				image.write(new File(path + name));
			}
			System.out.println("File Uploading Successful");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}