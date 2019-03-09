package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：首页轮播图参数对象
 * @author：zhuyangyong
 * @date: 2018/11/13 18:39
 */
@Data
public class IndexFileVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull(message = "区域编号不能为空")
    private String areaCode;
}
