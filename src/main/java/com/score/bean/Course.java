package com.score.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/12
 */
@Data
public class Course extends Model<Course> {

	@TableId("id")
	private String id;
	private String term;
	private Double credits;
	private String type;
	@TableField("courseName")
	private String courseName;
	
	@Override	
	protected Serializable pkVal() {
		return this.id;
	}
	
}
