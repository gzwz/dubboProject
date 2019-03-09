package cn.qumiandan.shop.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户店铺关联信息
 */
@Data
public class ShopUserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户角色关联id
     */
    private Long userRoleId;

    /**
     * 电话
     */
    private String userName;

    /**
     * 人员类型(1:店长；2：店员；3：收银员；4：其他)
     */
//    private Long type;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 状态(1:正常；0：删除)
     */
    private Byte status;

}
