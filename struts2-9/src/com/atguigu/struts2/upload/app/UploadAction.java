package com.atguigu.struts2.upload.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<File> ppt;
	private List<String> pptContentType;
	private List<String> pptFileName;
	
	private List<String> pptDesc;
	
	
	public List<File> getPpt() {
		return ppt;
	}


	public void setPpt(List<File> ppt) {
		this.ppt = ppt;
	}


	public List<String> getPptContentType() {
		return pptContentType;
	}


	public void setPptContentType(List<String> pptContentType) {
		this.pptContentType = pptContentType;
	}


	public List<String> getPptFileName() {
		return pptFileName;
	}


	public void setPptFileName(List<String> pptFileName) {
		this.pptFileName = pptFileName;
	}


	public List<String> getPptDesc() {
		return pptDesc;
	}


	public void setPptDesc(List<String> pptDesc) {
		this.pptDesc = pptDesc;
	}


	@Override
	public String execute() throws Exception {
		
		System.out.println(ppt);
		System.out.println(pptContentType);
		System.out.println(pptFileName);
		System.out.println(pptDesc);
		
//		ServletContext servletContext = ServletActionContext.getServletContext();
//		String dir = servletContext.getRealPath("/files/" + pptFileName);
//		System.out.println(dir);
//		
//		FileOutputStream out = new FileOutputStream(dir);
//		FileInputStream in = new FileInputStream(ppt);
//		
//		byte [] buffer = new byte[1024];
//		int len = 0;
//		
//		while((len = in.read(buffer)) != -1){
//			out.write(buffer, 0, len);
//		}
//		
//		out.close();
//		in.close();
		
		return "input";
	}
}
