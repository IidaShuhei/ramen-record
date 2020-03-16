package com.example.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.UserForm;
import com.example.service.UserService;

/**
 * ユーザーを登録するコントローラー.
 * 
 * @author iidashuhei
 *
 */
@Controller
@RequestMapping("")
public class RegisterUserController {

	@Autowired
	private UserService service;

	/**
	 * 入力されたものを受け取るフォーム.
	 * 
	 * @return ユーザー情報
	 */
	@ModelAttribute
	private UserForm setUpForm() {
		return new UserForm();
	}

	/**
	 * 新規登録画面へ遷移.
	 * 
	 * @return 新規登録ページ
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register_user";
	}

	/**
	 * ユーザーを登録する.
	 * 
	 * @param userForm ユーザーフォーム
	 * @param result エラーの有無
	 * @return ログイン画面へ遷移
	 */
	@RequestMapping("/insert")
	public String insert(@Validated UserForm userForm, BindingResult result) {
		User user = new User();
		// メールアドレスの重複チェック
		User userEmail = service.findByEmail(userForm.getEmail());
		if (userEmail != null) {
			result.rejectValue("email", "null", "そのメールアドレスはすでに使われています");
		}
		// パスワードと確認用パスワードのチェック
		if (!(userForm.getPassword().equals(userForm.getConfirmationPassword()))) {
			result.rejectValue("confirmationPassword", "null", "パスワードと確認用パスワードが異なります");
		}
		// エラーが一つでもあれば入力画面に戻る
		if (result.hasErrors()) {
			return toRegister();
		}
		BeanUtils.copyProperties(userForm, user);
		service.insert(user);
		return "login";
	}
}
