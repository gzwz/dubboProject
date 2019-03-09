package cn.qumiandan.shop.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 代理查询店铺
 * @author W
 * 2019年1月22日
 */
@Data
public class DLQueryShopVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**代理人员id*/
	private Long dyUserId;
	
    private Integer pageNum;
    
    private Integer pageSize;
}
