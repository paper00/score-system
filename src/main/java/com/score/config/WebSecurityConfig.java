package com.score.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.score.exception.EXCEPTION_CODE;
import com.score.exception.UserException;
import com.score.service.impl.UserDetailsServiceImpl;
import com.score.utils.JwtFilter;
import com.score.utils.JwtLoginFilter;

/**
 * @author paper
 * @date 2019/11/05
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    UserDetailsService userDetails(){
		return new UserDetailsServiceImpl();
    }
	
	@Bean
	PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/fonts/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .antMatchers("/index.html").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/aaa").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/teacher/**").hasRole("TEACHER")
            .antMatchers("/student/**").hasRole("STUDENT")
            .antMatchers("/loginn").permitAll()
			.antMatchers(HttpMethod.POST, "/loginn").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new JwtLoginFilter("/loginn", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
		    .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
			.csrf().disable();
	}
	
}
