package cn.qumiandan.shopmember.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop_member")
public class ShopMember implements Serializable {
    /**
     * 编号
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
     * 会员过期时间
     */
    private Date memberDeadline;

    /**
     * 创建时间
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

  
}