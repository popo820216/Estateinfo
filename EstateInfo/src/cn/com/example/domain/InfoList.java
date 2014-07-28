package cn.com.example.domain;

import java.util.List;

import com.google.gson.Gson;

public class InfoList {
	int error;
	Data data;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}

	class Data{
		int total;
		int limit;
		int count;
		List<Info> infos;
	
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getLimit() {
			return limit;
		}
		public void setLimit(int limit) {
			this.limit = limit;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public List<Info> getInfos() {
			return infos;
		}
		public void setInfos(List<Info> infos) {
			this.infos = infos;
		}
	}
	
	class Info{
		long id;
		String title;
		String image;
		String content;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	}
	
	public static InfoList convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, InfoList.class);
	}
	
}
