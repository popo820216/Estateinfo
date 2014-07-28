package cn.com.example.domain;

import com.google.gson.Gson;

public class House {
	String hid;
	String title;
	String area;
	String covers;
	String price;
	String price_rmb;
	String type;
	String address;
	String state;
	String city;
	String longitude;
	String latitude;
	String buildyear;
	String quality;
	String districtscore;
	String regionnature;
	String downpayment;
	String downpayment_rmb;
	String propertyfee;
	String propertyfee_rmb;
	String swimmingpool;
	String memo;
	String crtime;
	String saledtime;
	String image;
	String houseno;
	String isnew;
	
	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}



	public String getHouseno() {
		return houseno;
	}



	public void setHouseno(String houseno) {
		this.houseno = houseno;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getArea() {
		return area;
	}



	public void setArea(String area) {
		this.area = area;
	}



	public String getCovers() {
		return covers;
	}



	public void setCovers(String covers) {
		this.covers = covers;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getPrice_rmb() {
		return price_rmb;
	}



	public void setPrice_rmb(String price_rmb) {
		this.price_rmb = price_rmb;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getBuildyear() {
		return buildyear;
	}



	public void setBuildyear(String buildyear) {
		this.buildyear = buildyear;
	}



	public String getQuality() {
		return quality;
	}



	public void setQuality(String quality) {
		this.quality = quality;
	}



	public String getDistrictscore() {
		return districtscore;
	}



	public void setDistrictscore(String districtscore) {
		this.districtscore = districtscore;
	}



	public String getRegionnature() {
		return regionnature;
	}



	public void setRegionnature(String regionnature) {
		this.regionnature = regionnature;
	}



	public String getDownpayment() {
		return downpayment;
	}



	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}



	public String getDownpayment_rmb() {
		return downpayment_rmb;
	}



	public void setDownpayment_rmb(String downpayment_rmb) {
		this.downpayment_rmb = downpayment_rmb;
	}



	public String getPropertyfee() {
		return propertyfee;
	}



	public void setPropertyfee(String propertyfee) {
		this.propertyfee = propertyfee;
	}



	public String getPropertyfee_rmb() {
		return propertyfee_rmb;
	}



	public void setPropertyfee_rmb(String propertyfee_rmb) {
		this.propertyfee_rmb = propertyfee_rmb;
	}



	public String getSwimmingpool() {
		return swimmingpool;
	}



	public void setSwimmingpool(String swimmingpool) {
		this.swimmingpool = swimmingpool;
	}



	public String getMemo() {
		return memo;
	}



	public void setMemo(String memo) {
		this.memo = memo;
	}



	public String getCrtime() {
		return crtime;
	}



	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}



	public String getSaledtime() {
		return saledtime;
	}



	public void setSaledtime(String saledtime) {
		this.saledtime = saledtime;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image_s) {
		this.image = image_s;
	}



	public static House convertJsonToBean(String jsonStr){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, House.class);
	}
	
	public static String convertToString(House house){
		Gson gson = new Gson();
		return gson.toJson(house);
	}
}
