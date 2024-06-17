package com.thungashoe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	private static final String[] PUBLIC_MATCHERS = {
//			"/css/**",
//			"/js/**",
//			"/images/**",
//			"/",
//			"/new-user",
//			"/login",
//			"/store",
//			"/article-detail",
//			"/uploads/**",
//			"/shopping-cart/**"
//	};

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authConfig -> {
				authConfig.requestMatchers("/admin/**").hasAnyRole("ADMIN", "HOT");
				authConfig.requestMatchers("/shopping/**").hasRole("USER");
				authConfig.requestMatchers("/my-*" ).authenticated();
				authConfig.anyRequest().permitAll();
			})
			.formLogin(login -> {
				login.loginPage("/login");
				login.defaultSuccessUrl("/");
				login.failureUrl("/login?error");
				login.permitAll();
				}
			)
			.logout(logout -> {
				logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
				logout.logoutSuccessUrl("/login");
				logout.deleteCookies("JSESSIONID");
				logout.invalidateHttpSession(true);
			});
		return http.build();
	}
	
	@Bean
    static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
