package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ユーザーを管理するリポジトリ.
 * 
 * @author iidashuhei
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	//rowMapper破壊神
	private RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
	
	//sql原本
	private final String basicSql = "select id,name,email,password,icon from users";
	
	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param user ユーザー
	 */
	public void insert(User user) {
		String sql = "insert into users(name,email,password,icon)values(:name,:email,:password,:icon)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		String sql = basicSql + " where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql,param,rowMapper);
		if(userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}
	}
	
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user ユーザー
	 */
	public void update(User user) {
		String sql = "update users set name=:name, email=:email, password=:password, icon =:icon where id =:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}
	
	/**
	 * ユーザー詳細を表示する.
	 * 
	 * @param id ID
	 * @return ユーザー詳細
	 */
	public User load(Integer id) {
		String sql = basicSql + " where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param,rowMapper);
	}
	
	/**
	 * ユーザーIDからアイコンを検索する.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public User findIconByUserId(Integer userId) {
		String sql = "select icon from users where id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.queryForObject(sql, param, rowMapper);
	}
}
