package cn.com.example.domain;

import com.google.gson.Gson;

public class SearchResult {
	int errmsg;
	String code;
	Result result;
	
	public int getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(int errmsg) {
		this.errmsg = errmsg;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public static SearchResult convertJsonToBean(String jsonStr){
		if (jsonStr != null && !"".equals(jsonStr)){
			if (jsonStr.indexOf("error") < 0 && jsonStr.indexOf("result") >= 0){
				Gson gson = new Gson();
				return gson.fromJson(jsonStr, SearchResult.class);
			}
		}
		return null;
	}
}
