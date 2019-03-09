package cn.qumiandan.shopmember.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ShopMemberVO implements Serializable {
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