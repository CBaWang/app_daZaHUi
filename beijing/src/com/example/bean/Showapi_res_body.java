package com.example.bean;

import java.util.List;

public class Showapi_res_body {

	private List<newList> newslist;


	public Showapi_res_body(List<newList> newLists) {
		this.newslist = newLists;
	}

	public List<newList> getNewLists() {
		return newslist;
	}

	public void setNewLists(List<newList> newLists) {
		this.newslist = newLists;
	}

	@Override
	public String toString() {
		return "Showapi_res_body{" +
				"newLists=" + newslist +
				'}';
	}
}
