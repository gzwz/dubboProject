package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Data
public class ShopOneKeyApplyCashParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "店铺id不能为空")
	private List<Long> shopIds;

}
