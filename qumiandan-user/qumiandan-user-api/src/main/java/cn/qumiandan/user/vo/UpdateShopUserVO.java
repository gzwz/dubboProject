package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author yuleidian
 * @version 创建时间：2018年11月30日 下午4:27:06
 */
@Data
public class UpdateShopUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long shopUserId;
	
	private List<Long> shopIds;
	
	private Long updateId;
}
