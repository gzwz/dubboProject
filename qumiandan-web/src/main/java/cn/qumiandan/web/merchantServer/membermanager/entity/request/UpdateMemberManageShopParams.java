package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改店铺成员所管理的店铺请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月3日 上午10:53:12
 */
@Data
public class UpdateMemberManageShopParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 店铺用户编号(主表id)*/
	@NotNull(message = "店铺用户编号不能为空")
	private Long shopUserId;
	
	/** 所修改后店铺id集合*/
	private List<Long> shopIds;
	
	private Long updateId;
}
