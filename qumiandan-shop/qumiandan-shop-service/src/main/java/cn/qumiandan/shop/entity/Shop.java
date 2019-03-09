package cn.qumiandan.shop.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop")
public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺编号
     */
    private Long id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺简介
     */
    private String description;

    /**
     * 店铺类型
     */
    private Long shopTypeId;
    
    /**
	 * 行业id
	 */
    private Long industryId;

    /**
     * 营业执照编号
     */
    private String license;
    
	/**
	 * 营业证件类型：0营业执照，1三证合一
	 */
	private String licenseType;
	
	/**
	 * 商户注册名称/公司全称
	 */
	private String merchantCompany;

    /**
     * 店铺logo-url
     */
    private String logo;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 省编号
     */
    private String proCode;

    /**
     * 市编号
     */
    private String cityCode;

    /**
     * 区/县编号
     */
    private String countyCode;

    /**
     * 乡镇编号
     */
    private String townCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 店铺联系人姓名
     */
    private String merchantPerson;
    
    /**
     * 业务员id
     */
	private Long salemanId ;//业务员id

	/**
	 * 初创店铺管理员id
	 */
	private Long shopAdminId;
	
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 店铺状态
     * 1:创建待审核中；
     * 2：创建审核未通过；
     * 3：审核通过正常营业；
     * 4：更新审核中；
     * 5：更新审核未通过（审核通过为3）；
     * 6：主动申请关闭；
     * 7：平台强制关闭
     */
    private Byte status;

}