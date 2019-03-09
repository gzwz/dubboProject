package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午4:35:33
 */
@Data
public class AddShopUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 手机验证码*/
	private String code;
	
	/** 手机号 */
	private String mobile;
	
	/** 用户Id*/
	private Long userId;
	
	/** 角色Id*/
	private Long roleId;
	
    /**
     * 备注名
     */
    private String remarkName;
	
	/** 所管理店铺Id*/
	private List<Long> shopIds;
	
	/** 创建人Id*/
	private Long createId;
}
