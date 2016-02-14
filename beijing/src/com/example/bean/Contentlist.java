package com.example.bean;

public class Contentlist {

	private  String ct;
	
	private String img;
	
	private String title;
	
	
	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Contentlist [ct=" + ct + ", img=" + img + ", title=" + title
				+ "]";
	}
	
	
	
	
	
}
