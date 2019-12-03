package com.score.bean;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/02
 */
@Data
@TableName("User")
public class User implements UserDetails {
	
	@TableId("username")
	private String username;
	@TableField("password")
	private String password;
	private String role;
	private String secretkey;
	private boolean enabled;
	private boolean active;
	@TableField(exist = false)
	private List<GrantedAuthority> authorities;
	@TableField(exist = false)
	private List<Role> roles;
	
//	@Override	
//	protected Serializable pkVal() {
//		return this.username;
//	}
	
	@JsonIgnore
	@Override
	public List<GrantedAuthority> getAuthorities() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    for (Role role : roles) {
	    	authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
	    }
	    return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}