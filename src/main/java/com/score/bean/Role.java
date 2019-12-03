package com.score.bean;

import lombok.Data;

/**
 * @author paper
 * @date 2019/11/12
 */
@Data
public class Role {
	
	private Integer id;
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }
}
