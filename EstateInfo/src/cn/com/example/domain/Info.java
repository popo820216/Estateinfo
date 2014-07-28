package cn.com.example.domain;

import com.google.gson.Gson;

public class Info {
	String id;
	String title;
	String crtime;
	String author;
	String content;
	String show;
	String image_s;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCrtime() {
		return crtime;
	}

	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getImage_s() {
		return image_s;
	}

	public void setImage_s(String image_s) {
		this.image_s = image_s;
	}

	public static Info convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, Info.class);
	}
	
	public static String convertToJson(Info info){
		Gson gson = new Gson();
		return gson.toJson(info);
	}
	
}
