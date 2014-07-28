package cn.com.example.domain;

import java.util.List;

import com.google.gson.Gson;

public class Result {
	Integer total;
	Integer count;
	Integer page_count;
	Integer page;
	Integer page_next;
	Integer page_previous;
	Integer offset;
	Integer limit;
	
	List<House> data;
	List<Info> infos;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPage_count() {
		return page_count;
	}

	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage_next() {
		return page_next;
	}

	public void setPage_next(Integer page_next) {
		this.page_next = page_next;
	}

	public Integer getPage_previous() {
		return page_previous;
	}

	public void setPage_previous(Integer page_previous) {
		this.page_previous = page_previous;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<House> getData() {
		return data;
	}

	public void setData(List<House> data) {
		this.data = data;
	}
	
	public List<Info> getInfos() {
		return infos;
	}

	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

	public static Result convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, Result.class);
	}
	
}
