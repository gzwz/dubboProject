package cn.qumiandan.web.frontServer.shop.entity.request;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 获取店铺详情参数
 * @author lrj
 * @version 创建时间：2018年11月12日 14:40
 */
@Data
public class ShopIdParams  implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "店铺id不能为空")
	private Long id;
}
