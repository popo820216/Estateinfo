package cn.com.example.domain;

import java.util.List;

import com.google.gson.Gson;

public class HouseDetail {
	int error;
	List<Detail> result;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}

	public List<Detail> getResult() {
		return result;
	}
	public void setResult(List<Detail> result) {
		this.result = result;
	}
	public static HouseDetail convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, HouseDetail.class);
	}
	
	public class Detail{
		String image;
		String title;
		String addtime;
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
		
	}
}
