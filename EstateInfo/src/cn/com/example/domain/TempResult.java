package cn.com.example.domain;

import java.util.List;

import com.google.gson.Gson;

public class TempResult {
	List<House> result;

	public List<House> getResult() {
		return result;
	}

	public void setResult(List<House> result) {
		this.result = result;
	}
	
	public static TempResult convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, TempResult.class);
	}
}
