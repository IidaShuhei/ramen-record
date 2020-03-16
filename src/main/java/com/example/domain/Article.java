package com.example.domain;

public class Article {

	private Integer id;
	private Integer userId;
	private String shopName;
	private String ramenName;
	private String other1;
	private String other2;
	private String image;
	private Integer price;
	private String zipcode;
	private String address;
	private String startTime;
	private String endTime;
	private String restTime;
	private Integer star;
	private String holidays;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRamenName() {
		return ramenName;
	}

	public void setRamenName(String ramenName) {
		this.ramenName = ramenName;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRestTime() {
		return restTime;
	}

	public void setRestTime(String restTime) {
		this.restTime = restTime;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", userId=" + userId + ", shopName=" + shopName + ", ramenName=" + ramenName
				+ ", other1=" + other1 + ", other2=" + other2 + ", image=" + image + ", price=" + price + ", zipcode="
				+ zipcode + ", address=" + address + ", startTime=" + startTime + ", endTime=" + endTime + ", restTime="
				+ restTime + ", star=" + star + ", holidays=" + holidays + "]";
	}

}
