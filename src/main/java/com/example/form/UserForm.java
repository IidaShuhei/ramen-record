package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {

	private Integer id;
	@NotBlank(message = "名前を入力してください")
	private String name;
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "アドレスが不正です")
	private String email;
	@NotBlank(message = "パスワードを入力してください")
	private String password;
	@NotBlank(message = "確認用パスワードを入力してください")
	private String confirmationPassword;
	private MultipartFile icon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public MultipartFile getIcon() {
		return icon;
	}

	public void setIcon(MultipartFile icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "UserForm [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmationPassword=" + confirmationPassword + ", icon=" + icon + "]";
	}

}