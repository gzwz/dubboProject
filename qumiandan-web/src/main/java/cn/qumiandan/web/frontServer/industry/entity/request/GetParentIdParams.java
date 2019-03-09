package cn.qumiandan.web.frontServer.industry.entity.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 获取行业id参数类
 * @author lrj
 *
 */
@Data
public class GetParentIdParams implements Serializable {

	
	private static final long serialVersionUID = 1L;

	
	/**
	 * 父级id
	 */
	Long parentId;

}
