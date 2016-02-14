package com.example.bean;

import java.util.List;

public class Showapi_res_body {

	List<Contentlist> contentlist;
	
	

	public List<Contentlist> getContentlist() {
		return contentlist;
	}

	public void setContentlist(List<Contentlist> contentlist) {
		this.contentlist = contentlist;
	}

	@Override
	public String toString() {
		return "showapi_res_body [contentlist=" + contentlist + "]";
	}
	
}
