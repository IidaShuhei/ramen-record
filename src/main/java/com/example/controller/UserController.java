package com.example.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Article;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.UserForm;
import com.example.repository.ArticleRepository;
import com.example.service.ArticleService;
import com.example.service.UserService;

/**
 * ユーザーを管理するコントローラー.
 * 
 * @author iidashuhei
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository repository;

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
	 * ユーザー詳細画面を表示する.
	 * 
	 * @return ユーザー詳細
	 */
	@RequestMapping("/toUserDetail")
	public String toUserDetail(Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = service.load(id);
		model.addAttribute("user", user);

		model.addAttribute("icon", service.findIconByUserId(loginUser.getUser().getId()));
		return "user_detail";
	}

	/**
	 * ユーザー詳細更新画面へ遷移.
	 * 
	 * @return ユーザー詳細更新画面
	 */
	@RequestMapping("/toUpdateUserDetail")
	public String toUpdateUserDetail(Integer id, Model model) {
		User user = service.load(id);
		model.addAttribute("user", user);
		return "update_user";
	}

	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param userForm  ユーザーフォーム
	 * @param result    エラーがあれば戻る
	 * @param loginUser ログインユーザー
	 * @return トップページへ
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public String update(@Validated UserForm userForm, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginUser) throws IOException {
		if (result.hasErrors()) {
			return toUpdateUserDetail(userForm.getId(), model);
		}
		User user = new User();
		user.setId(userForm.getId());
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());

		// パスワードと確認用パスワードのチェック
		if (!(userForm.getPassword().equals(userForm.getConfirmationPassword()))) {
			result.rejectValue("confirmationPassword", "null", "パスワードと確認用パスワードが異なります");
		}
		user.setPassword(passwordEncoder.encode(userForm.getPassword()));

		// 画像ファイル形式チェック
		MultipartFile image = userForm.getIcon();
		String fileExtension = null;
		try {
			fileExtension = getExtension(image.getOriginalFilename());
			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("icon", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("icon", "", "拡張子は.jpgか.pngのみに対応しています");
		}
		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(image.getBytes());
		if ("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if ("png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		user.setIcon(base64FileString);

		service.update(user);
		return "redirect:/article";
	}

	/*
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
	 */
	private String getExtension(String originalFileName) throws Exception {
		if (originalFileName == null) {
			throw new FileNotFoundException();
		}
		int point = originalFileName.lastIndexOf(".");
		if (point == -1) {
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}

	/**
	 * ユーザーのラーメン記録を表示.
	 * 
	 * @param model     モデル
	 * @param loginUser ログインユーザー
	 * @return ユーザーのラーメン記録
	 */
	@RequestMapping("/toUserRamenList")
	public String toUserRamenList(Integer start, @AuthenticationPrincipal LoginUser loginUser, Model model) {
		// Maxページ数を求める
		Integer count = repository.countUserArticle(loginUser.getUser().getId());

		// 何番目から表示するかを求める
		if (start == null) {
			start = 0;
		};

		List<Article> articleList = articleService.findByUserId(loginUser.getUser().getId(),start);
		if (articleList.isEmpty()) {
			model.addAttribute("message", "あなたのラーメン記録はまだありません");
		}
		model.addAttribute("articleList", articleList);
		model.addAttribute("start", start);
		model.addAttribute("count", count);
		return "user_ramen_list";
	}
}
