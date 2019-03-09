package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 动态查询店铺参数类
 * 
 * @author lrj
 *
 */
@Data
public class ShopDynamicVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// =====================店铺基础信息=======================
	private Long id; // 店铺编号

	private String name; // 店铺名称

	private String description; // 店铺简介

	private Long shopTypeId; // 店铺类型

	private String code; // 省、市、区编号
	
	private Integer level;//1:省；2：市；3：区/县；4：镇
	
	private Long salemanId; //业务员id
	
	private String salemanName; //业务员名（手机号）
	
	private Long industryId; //行业id
	
	private String inputInfo;//模糊查询条件
		
	private String userLongitude;//用户定位经度
	
	private String userLatitude;//用户定位纬度
	
	private Byte status;//店铺状态
	
	
	/**
	 * 店铺联系人姓名
	 */
	private String merchantPerson;

	private String phone;	//店铺联系电话
	
	/**
	 * 创建时间查询条件：结束时间
	 */
	private Date endCreateDate;
	
	/**
	 * 创建时间查询条件：开始时间
	 */
	private Date startCreateDate;
	
	/**
	 * 店铺状态集合
	 */
	private List<Byte> statusList;
	
	private Byte sortByDistance;//根据距离排序（1：降序； 0：升序；null 不按距离排序）
	
	private Byte sortByCreateTime;//根据创建时间排序（1：降序； 0：升序；不按创建时间排序）

	/**
	 * 附近标识（用户端查询附近店铺100公里以内）
	 */
	private Boolean isNearby;
}
