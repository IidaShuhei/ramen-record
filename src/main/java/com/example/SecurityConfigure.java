package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * ログイン認証用の設定.
 * 
 * @author iidashuhei
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 *静的リソースに対するセキュリティ設定を無視する
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/css/**"
						,"/img/**"
						,"/js/**"
						,"/static/**");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//パスの書き方。コントローラのパスとメソッドのパス
			.antMatchers("/login/toLogin**"
						,"/doLogin"
						,"/toRegister"
						,"/insert"
//						,"/article/*"
//						,"/"
//						,"/"
//						,"/"
						).permitAll()//全てのユーザでアクセス化
			.anyRequest().authenticated();//それ以外のパスは認証必須
		
		http.formLogin()//ログインに関する設定
			
			.loginPage("/login/toLogin")//ログイン画面に遷移させるパス
			.loginProcessingUrl("/doLogin")//ログインhtmlのアクション属性のパスと一致させる.このアクションのボタンが押されたらSecurityがログイン認証を行う
			.failureUrl("/login/toLogin?error=true")
			.defaultSuccessUrl("/article",true)//認証後第一引数のパスに遷移（トップページになる）
			.usernameParameter("email")
			.passwordParameter("password");
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
			.logoutSuccessUrl("/")//ログアウト後に遷移させるパス
			.deleteCookies("JSESSIONID")//ログアウト後Cookieに保存されているセッションIDを削除
			.invalidateHttpSession(true);//true:ログアウト後セッションを無効にする
		
		//ajaxを使えるようにする.
//		http.csrf().ignoringAntMatchers("/judge", "/good","/countGood");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
