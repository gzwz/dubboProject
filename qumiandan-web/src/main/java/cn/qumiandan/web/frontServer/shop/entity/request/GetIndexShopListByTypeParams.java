package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 根据类型获取店铺列表参数
 * @author lrj
 * @version 创建时间：2018年11月12日 15:20
 */
@Setter
@Getter
@ToString
public class GetIndexShopListByTypeParams extends CommonParams implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "店铺类型id不能为空")
	private Long typeId;
	
}
