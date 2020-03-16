package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

/**
 * 記事を管理するサービス.
 * 
 * @author iidashuhei
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository repository;

	/**
	 * 記事を投稿する.
	 * 
	 * @param article 記事
	 * @throws IOException 
	 */
	public void insert(Article article) {
		repository.insert(article);
	}

	/**
	 * 記事を全件検索する.
	 * 
	 * @return 全記事
	 */
	public List<Article> findAll(Integer userId, Integer start) {
		return repository.findAll(userId, start);
	}
	
	/**
	 * 記事詳細を表示する.
	 * 
	 * @param id ID
	 * @return 記事詳細
	 */
	public Article load(Integer id) {
		return repository.load(id);
	}
	
	/**
	 * 記事を更新する.
	 * 
	 * @param article 記事
	 */
	public void update(Article article) {
		repository.update(article);
	}
	
	/**
	 * ユーザーIDから記事を表示する.
	 * 
	 * @param userId ユーザーID
	 * @return 記事一覧
	 */
	public List<Article> findByUserId(Integer userId, Integer start) {
		return repository.findByUserId(userId,start);
	}
}
