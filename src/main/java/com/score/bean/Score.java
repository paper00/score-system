package com.score.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/12
 */
@Data
public class Score extends Model<Score>{

	@TableField("studentNumber")
	private String studentNumber;
	@TableField(exist = false)
	private String studentName;
	@TableField("courseId")
	private String courseId;
	@TableField(exist = false)
	private String courseName;
	private Double total;
	private Double usual;
	private Double experiment;
	@TableField("finalExam")
	private Double finalExam;
	private Double point;
	@TableField(exist = false)
	private String rank;
	@TableField("ifResit")
	private boolean ifResit;
	
	@Override	
	protected Serializable pkVal() {
		return this.studentNumber;
	}
	
	public Score() {}
	
	public Score(String studentNumber, String courseId, Double total, Double usual, 
			Double experiment, Double finalExam, Double point, boolean ifResit) {
		this.studentNumber = studentNumber;
		this.courseId = courseId;
		this.total = total;
		this.usual = usual;
		this.experiment = experiment;
		this.finalExam = finalExam;
		this.point = point;
		this.ifResit = ifResit;
	}

}
