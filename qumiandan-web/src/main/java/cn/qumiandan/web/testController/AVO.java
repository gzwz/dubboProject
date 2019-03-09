package cn.qumiandan.web.testController;

import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class AVO {
	
	@Expose
	private String name;
	
	@Expose
	private List<BVO> list;
	
}
