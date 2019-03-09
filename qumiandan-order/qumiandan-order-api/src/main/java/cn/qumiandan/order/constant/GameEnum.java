package cn.qumiandan.order.constant;

import lombok.Getter;

@Getter
public enum GameEnum {
	
	ShakeGame("摇一摇");
	
	private String name;
	
	private GameEnum(String name) {
		this.name = name;
	}
	
	
}
