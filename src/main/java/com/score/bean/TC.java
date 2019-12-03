package com.score.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/16
 */
@Data
@TableName("TC")
public class TC extends Model<TC> {
	
	@TableField("teacherId")
	String teacherId;
	@TableField("courseId")
	String courseId;
	
}
