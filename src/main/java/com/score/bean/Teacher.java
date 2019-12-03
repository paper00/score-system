package com.score.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/12
 */
@Data
public class Teacher extends Model<Teacher> {
	
	@TableId("id")
	private String id;
	private String name;
	private int age;
	private String sex;
	private String phone;
	private String majorid;
	@TableField(exist = false)
	private String majorName;
	
	@Override	
	protected Serializable pkVal() {
		return this.id;
	}
	
	public Teacher() {}
	
	public Teacher(String id, String name, int age, String sex, String phone, String major) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.phone = phone;
		this.majorName = major;
	}
	
}