package com.atguigu.struts2.download.app;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DownLoadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contentType;
	private long contentLength;
	private String contentDisposition;
	private InputStream inputStream;
	
	public String getContentType() {
		return contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public String execute() throws Exception {

		//确定各个成员变量的值
		contentType = "text/html";
		contentDisposition = "attachment;filename=hidden.html";
		
		ServletContext servletContext = 
				ServletActionContext.getServletContext();
		String fileName = servletContext.getRealPath("/files/hidden.html");
		inputStream = new FileInputStream(fileName);
		contentLength = inputStream.available();
		
		return SUCCESS;
	}
}
