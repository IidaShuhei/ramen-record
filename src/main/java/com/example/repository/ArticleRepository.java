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

import com.example.domain.Article;

/**
 * 記事を管理するリポジトリ.
 * 
 * @author iidashuhei
 *
 */
@Repository
public class ArticleRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	//rowMapper破壊神
	private RowMapper<Article> rowMapper = new BeanPropertyRowMapper<Article>(Article.class);
	
	//sql原本
	private final String basicSql = "select id,user_id,shop_name,ramen_name,price,other1,other2,image,zipcode,address,start_time,rest_time,end_time,holidays,star from articles";
	
	/**
	 * 記事を投稿する.
	 * 
	 * @param article 記事
	 */
	public void insert(Article article) {
		String sql = "insert into articles(user_id,shop_name,ramen_name,price,other1,other2,image,zipcode,address,start_time,rest_time,end_time,holidays,star)values(:userId,:shopName,:ramenName,:price,:other1,:other2,:image,:zipcode,:address,:startTime,:restTime,:endTime,:holidays,:star)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}
	
	/**
	 * 記事を全件検索する.
	 * 
	 * @return 全記事
	 */
	public List<Article> findAll(Integer userId, Integer start) {
		String sql = basicSql + " where user_id <> :userId order by id desc limit 8 offset :start";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("start", start);
		return template.query(sql, param, rowMapper);
	}
	
	/**
	 * 記事詳細を表示する.
	 * 
	 * @param id ID
	 * @return 記事詳細
	 */
	public Article load(Integer id) {
		String sql = basicSql + " where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, rowMapper);
	}
	
	/**
	 * 記事を更新する.
	 * 
	 * @param article 記事
	 */
	public void update(Article article) {
		String sql = "update articles set user_id=:userId,shop_name=:shopName,ramen_name=:ramenName,price=:price,other1=:other1,other2=:other2,image=:image,zipcode=:zipcode,address=:address,start_time=:startTime,rest_time=:restTime,end_time=:endTime,holidays=:holidays,star=:star where id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}
	
	/**
	 * ユーザーIDから記事を表示する.
	 * 
	 * @param userId ユーザーID
	 * @return 記事一覧
	 */
	public List<Article> findByUserId(Integer userId, Integer start) {
		 String sql = basicSql + " where user_id=:userId order by id desc limit 8 offset :start";
		 SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("start", start);
		 return template.query(sql, param, rowMapper);
	}
	
	/**
	 * ログインしているユーザーの記事数を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザーの記事数
	 */
	public Integer countUserArticle(Integer userId) {
		String sql = "select count(*) from articles where user_id =:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.queryForObject(sql, param, Integer.class);
	}
	
	/**
	 * ログインしているユーザー以外の記事数を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー別記事数
	 */
	public Integer countNotUserArticle(Integer userId) {
		String sql = "select count(*) from articles where user_id <> :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		return template.queryForObject(sql, param, Integer.class);
	}
}
