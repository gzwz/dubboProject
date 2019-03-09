package cn.qumiandan.indexmenu.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class IndexMenuVO implements Serializable{
	private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 跳转url
     */
    private String url;

    /**
     * 图片
     */
    private String image;

    /**
     * 地址编码code
     */
    private Integer areaCode;

    /**
     * 地址编码级别（1：省级；2：市级；3：区/县级；4：街道）
     */
    private Byte level;

    /**
     * 排序数
     */
    private Integer sort;

    
    /**
     * 状态（1：正常；0：删除）
     */
    private Byte status;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

}
