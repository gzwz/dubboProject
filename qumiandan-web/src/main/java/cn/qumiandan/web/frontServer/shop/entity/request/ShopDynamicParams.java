package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 店铺动态查询参数
 * 
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class ShopDynamicParams extends PageInfoParams implements Serializable {

	private static final long serialVersionUID = 1L;

	// =====================店铺基础信息=======================

	private Long shopTypeId; // 店铺类型

	private String code; // 省、市、区编号
	
	private Integer level;//1:省；2：市；3：区/县；4：镇
	
	private String salemanName; //业务员用户名

	private Long industryId; // 行业id

	private String inputInfo;// 模糊查询条件

	private String userLongitude;// 用户定位经度

	private String userLatitude;// 用户定位纬度
	
	private Byte status;//店铺状态
	
	/**
	 * 店铺状态集合
	 */
	private List<Byte> statusList;
	
	private Long salemanId; //业务员id
	
	
	private Byte sortByDistance;// 根据距离排序（1：降序； 0：升序；null 不按距离排序）

	private Byte sortByCreateTime;// 根据创建时间排序（1：降序； 0：升序；不按创建时间排序）

	/**
	 * 创建时间查询条件：结束时间
	 */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endCreateDate;
	
	/**
	 * 创建时间查询条件：开始时间
	 */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startCreateDate;
    
	/**
	 * 店铺联系人姓名
	 */
	private String merchantPerson;

	private String phone;	//店铺联系电话
}
