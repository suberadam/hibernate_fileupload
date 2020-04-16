# hibernate_fileupload

Creating File Upload Project with MySQL database using Hibernate

1) Copy the hibernate jar files and the mysql connector jar file inside the "lib" folder of "WEB-INF".
2) Expand Java Resources => Right Click on "src" folder => new => other => XML file => save as "hibernate.cfg.xml". Write the following code inside the XML file.

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
 
<hibernate-configuration>
    <session-factory>
 
        <!-- Connection settings -->
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
              <!-- Sample MySQL URL provided  -->  
        <property name="connection.url">jdbc:mysql://localhost:portnumber/projectname</property>
        <property name="connection.username">root</property> // your username
        <property name="connection.password">root</property> // your password 
           
        <!-- Show SQL on console -->
        <property name="show_sql">true</property>
 
		<!--Setting Session context model -->
		<property name="current_session_context_class">thread</property>
 
    </session-factory>
</hibernate-configuration>
=======================================
3) Configure build path. Right click on the project folder => Build Path => Configure Build Path. Select all libraries => Click Apply and close.
=======================================
4) Create the following table in MySQL:
Table-name : files
id : int => PK AI
file_name => varchar => unique => Not NULL
label => varchar
caption => varchar
=======================================
5) Creating the Entity Class. Expand Java Resources => src => right click on org.apec => New => Class
package name : org.apec.hibernate.entity
class name : Files

=> This command creates Files.java. Write the code as below:
/* source code of Files.java */
package org.apec.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="files")
@Table(name="files")
public class Files {

	@Id
	@Column(name="id")
	int id;
	
	@Column(name="file_name")
	String fileName;
	
	@Column(name="label")
	String label;
	
	@Column(name="caption")
	String caption;
	
	public Files() {
		// super();
	}

	public Files(String fileName) {
		// super();
		this.fileName = fileName;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
} // class
=============================================================
6) Create a Java class that uses hibernate code. For this purpose, add a DAO(Data Access Object) class as below
Expand src => Right Click on org.apec => New => Class

package-name : org.apec.hibernate.DAO
class Name : FilesDAO

=> The file looks as below:
/* source code of FilesDAO.java */

package org.apec.hibernate.DAO;

import org.apec.hibernate.entity.Files;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FilesDAO {
	SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(Files.class)
			.buildSessionFactory();
	
	public void addFileDetails(Files file) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(file);
		session.getTransaction().commit();
		System.out.println(file.getFileName() + "got added");
	}
} // class
===============================================
7) Modify ImageUpload.java(This file now sends the uploaded file information into the database:files table)

/* source code of ImageUpload.java */
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
	public String path ="location you want to save/"; // change this to a path in your local machine 
	
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
============================================
8) Check your MySQL database

select * from files;
=> If the files are available in the "images" folder and the records are added inside the "files" table of MySQL, the project is successful.
