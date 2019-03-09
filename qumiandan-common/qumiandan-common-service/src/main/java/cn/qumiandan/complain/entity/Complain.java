package cn.qumiandan.complain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 投诉信息实体
 *
 */
@Data
@TableName("qmd_complain")
public class Complain implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;

    /**
     * 投诉类型ID
     */
    private Long typeId;

    /**
     * 投诉内容
     */
    private String content;

    /**
     * 证据图片（图片url list）
     */
    private String imageUrls;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 处理状态（1.创建，2.投诉成功，3,投诉失败）
     */
    private Byte status;

    /**
     * 投诉失败，一般需要平台给出失败的原因反馈
     */
    private String feedback;

    /**
     * 投诉店铺id（和商品id 二选一必填）
     */
    private Long tagartShopId;

    /**
     * 投诉商品id（和店铺id 二选一必填）
     */
    private Long tagartProductId;

    /**
     * 创建者id
     */
    private Long createId;

    /**
     * 修改者id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;
}