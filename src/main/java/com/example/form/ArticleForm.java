package com.example.form;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class ArticleForm {

	private Integer id;
	private String userId;
	@NotBlank(message = "店名を入力してください")
	private String shopName;
	@NotBlank(message = "ラーメン名を入力してください")
	private String ramenName;
	private String price;
	private String other1;
	private String other2;
	private MultipartFile image;
	private String zipcode;
	private String address;
	private String startTime;
	private String restTime;
	private String endTime;
	private String holidays;
	private Integer star;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
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

	public String getRestTime() {
		return restTime;
	}

	public void setRestTime(String restTime) {
		this.restTime = restTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Override
	public String toString() {
		return "ArticleForm [id=" + id + ", userId=" + userId + ", shopName=" + shopName + ", ramenName=" + ramenName
				+ ", price=" + price + ", other1=" + other1 + ", other2=" + other2 + ", image=" + image + ", zipcode="
				+ zipcode + ", address=" + address + ", startTime=" + startTime + ", restTime=" + restTime
				+ ", endTime=" + endTime + ", holidays=" + holidays + ", star=" + star + "]";
	}

}
