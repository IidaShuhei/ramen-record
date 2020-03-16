package com.example.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import com.example.service.ArticleService;
import com.example.service.UserService;

/**
 * 記事を管理するコントローラー.
 * 
 * @author iidashuhei
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService service;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleRepository repository;

	/**
	 * 入力値を受け取るフォーム.
	 * 
	 * @return 記事フォーム
	 */
	@ModelAttribute
	private ArticleForm setUpForm() {
		return new ArticleForm();
	}

	/**
	 * 記事を一覧表示する.
	 * 
	 * @param model モデル
	 * @return トップページへ
	 */
	@RequestMapping("")
	public String findAll(Integer start, @AuthenticationPrincipal LoginUser loginUser, Model model) {
		// Maxページ数を求める
		Integer count = repository.countNotUserArticle(loginUser.getUser().getId());

		//何番目から表示するかを求める
		if (start == null) {
			start = 0;
		};

		List<Article> articleList = service.findAll(loginUser.getUser().getId(), start);
		if(articleList.isEmpty()) {
			model.addAttribute("message", "他の人はまだ投稿していないようだ…");
		}
		model.addAttribute("articleList", articleList);
		model.addAttribute("start", start);
		model.addAttribute("count", count);

		User user = userService.load(loginUser.getUser().getId());
		model.addAttribute("user", user);
		return "ramen_list";
	}

	/**
	 * 記事投稿画面へ遷移.
	 * 
	 * @return 記事投稿画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "article_insert";
	}

	/**
	 * 記事を投稿する.
	 * 
	 * @param articleForm 記事フォーム
	 * @param result      エラーがあれば戻る
	 * @param loginUser   ログインユーザー
	 * @return トップページへ
	 * @throws IOException
	 */
	@RequestMapping("/insert")
	public String insert(@Validated ArticleForm articleForm, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) throws IOException {
		Article article = new Article();
		article.setUserId(loginUser.getUser().getId());
		article.setShopName(articleForm.getShopName());
		article.setRamenName(articleForm.getRamenName());
		if (!(articleForm.getStar() == null)) {
			article.setStar(articleForm.getStar());
		}

		// 画像ファイル形式チェック
		MultipartFile image = articleForm.getImage();
		String fileExtension = null;
		try {
			fileExtension = getExtension(image.getOriginalFilename());
			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
		}

		if (result.hasErrors()) {
			return toInsert();
		}

		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(image.getBytes());
		if ("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if ("png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		article.setImage(base64FileString);

		if (!(articleForm.getOther1().equals(""))) {
			article.setOther1(articleForm.getOther1());
		}
		if (!(articleForm.getOther2().equals(""))) {
			article.setOther2(articleForm.getOther2());
		}
		if (!(articleForm.getPrice().equals(""))) {
			article.setPrice(Integer.parseInt(articleForm.getPrice()));
		}
		if (!(articleForm.getZipcode().equals(""))) {
			article.setZipcode(articleForm.getZipcode());
		}
		if (!(articleForm.getAddress().equals(""))) {
			article.setAddress(articleForm.getAddress());
		}
		if (!(articleForm.getStartTime().equals(""))) {
			article.setStartTime(articleForm.getStartTime());
		}
		if (!(articleForm.getEndTime().equals(""))) {
			article.setEndTime(articleForm.getEndTime());
		}
		if (!(articleForm.getRestTime().equals(""))) {
			article.setRestTime(articleForm.getRestTime());
		}
		if (!(articleForm.getHolidays().equals(""))) {
			article.setHolidays(articleForm.getHolidays());
		}

		service.insert(article);
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
	 * 記事詳細を表示する.
	 * 
	 * @param id    ID
	 * @param model モデル
	 * @return 記事詳細画面へ
	 */
	@RequestMapping("/detail")
	public String load(Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Article article = service.load(id);
		model.addAttribute("article", article);

		User user = userService.load(loginUser.getUser().getId());
		model.addAttribute("user", user);
		return "article_detail";
	}

	/**
	 * 更新画面へ遷移.
	 * 
	 * @return 更新画面
	 */
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Model model) {
		Article article = service.load(id);
		model.addAttribute("article", article);
		return "article_update";
	}

	/**
	 * 記事を更新する.
	 * 
	 * @param articleForm 記事フォーム
	 * @param result      エラーがあればメッセージを出す
	 * @param loginUser   ログインユーザー
	 * @param model       モデル
	 * @return 記事一覧へフォワード
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public String update(@Validated ArticleForm articleForm, BindingResult result, Model model) throws IOException {
		Article article = new Article();
		article.setId(articleForm.getId());
		article.setUserId(Integer.parseInt(articleForm.getUserId()));
		article.setShopName(articleForm.getShopName());
		article.setRamenName(articleForm.getRamenName());
		if (!(articleForm.getStar() == null)) {
			article.setStar(articleForm.getStar());
		}
		// 画像ファイル形式チェック
		MultipartFile image = articleForm.getImage();
		String fileExtension = null;
		try {
			fileExtension = getExtension(image.getOriginalFilename());
			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
		}

		if (result.hasErrors()) {
			return toInsert();
		}

		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(image.getBytes());
		if ("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if ("png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		article.setImage(base64FileString);

		if (!(articleForm.getOther1().equals(""))) {
			article.setOther1(articleForm.getOther1());
		}
		if (!(articleForm.getOther2().equals(""))) {
			article.setOther2(articleForm.getOther2());
		}
		if (!(articleForm.getPrice().equals(""))) {
			article.setPrice(Integer.parseInt(articleForm.getPrice()));
		}
		if (!(articleForm.getZipcode().equals(""))) {
			article.setZipcode(articleForm.getZipcode());
		}
		if (!(articleForm.getAddress().equals(""))) {
			article.setAddress(articleForm.getAddress());
		}
		if (!(articleForm.getStartTime().equals(""))) {
			article.setStartTime(articleForm.getStartTime());
		}
		if (!(articleForm.getEndTime().equals(""))) {
			article.setEndTime(articleForm.getEndTime());
		}
		if (!(articleForm.getRestTime().equals(""))) {
			article.setRestTime(articleForm.getRestTime());
		}
		if (!(articleForm.getHolidays().equals(""))) {
			article.setHolidays(articleForm.getHolidays());
		}
		service.update(article);
		return "redirect:/article";
	}
	
	@RequestMapping("/practice")
	public String practice() {
		return "practice";
	}
}
