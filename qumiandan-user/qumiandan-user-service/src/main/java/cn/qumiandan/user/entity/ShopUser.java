package cn.qumiandan.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 店铺用户联合实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "sys_shop_user")
public class ShopUser implements Serializable {
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
     * 账号（电话）
     */
    private Long userId;

    /**
     * 用户角色id
     */
    private Long userRoleId;
    
    /**
     * 备注名
     */
    private String remarkName;

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