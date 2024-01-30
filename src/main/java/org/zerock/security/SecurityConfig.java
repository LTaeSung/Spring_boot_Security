package org.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Configuration
@EnableWebSecurity
@Log
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("security config...............");
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/manager/**").hasRole("MANAGER")// **하위 디렉터리 포함
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
		);
		
		http.formLogin().loginPage("/login");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
		
		// 사용자정의 UserDetailsService
		http
			.rememberMe()
			.key("zerock")
			.userDetailsService(zerokUserService)
			.tokenRepository(getJDBCRepository())
			.tokenValiditySeconds(60*60*24);
		return http.build();
	}
	
//	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build Auth global....");
		// 메모리
//		auth.inMemoryAuthentication().withUser("manager").password("1111").roles("MANAGER");
		// JDBC
		String query1 = "select uid username, upw password, true enabled from tbl_member where uid=?";
		String query2 = "select member uid, role_name role from tbl_member_roles where member=?";
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(query1)
		.rolePrefix("ROLE_") // ROLE_ADMIN
		.authoritiesByUsernameQuery(query2)
		.passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ZerokUserService zerokUserService;
	
	private PersistentTokenRepository getJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new PasswordEncoder() {
//			public String encode(CharSequence rawPassword) {
//				return rawPassword.toString();
//			}
//			public boolean matches(CharSequence rawPassword, String encodePassword) {
//				return rawPassword.equals(encodePassword);
//			}
//		};
		return new BCryptPasswordEncoder();
	}
}
