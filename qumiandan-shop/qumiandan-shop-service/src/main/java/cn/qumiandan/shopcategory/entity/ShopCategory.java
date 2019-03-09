package cn.qumiandan.shopcategory.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop_category")
public class ShopCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

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
     * 状态（1：正常；0：已删除）
     */
    private Byte status;
}