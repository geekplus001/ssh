package com.ben.hibernate.entries;

import java.sql.Blob;
import java.util.Date;

public class News {
	
	private Integer id;
	private String title;
	private String author;
	
	private Date date;
	
	//派生属性：author + ":" + title
	private String desc;

	//大对象
	private String content;
	//二进制
	private Blob image;
	
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}

	public News(String title, String author, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + "]";
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
	

}
