package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザーを管理するサービス.
 * 
 * @author iidashuhei
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param userForm ユーザーフォーム
	 */
	public void insert(User user) {
		// パスワードをハッシュ化する
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.insert(user);
	}
	
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user ユーザー
	 */
	public void update(User user) {
		repository.update(user);
	}
	
	/**
	 * ユーザー詳細を表示する.
	 * 
	 * @param id ID
	 * @return ユーザー詳細
	 */
	public User load(Integer id) {
		return repository.load(id);
	}
	
	/**
	 * ユーザーIDからアイコンを検索する.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public User findIconByUserId(Integer userId) {
		return repository.findIconByUserId(userId);
	}
}
