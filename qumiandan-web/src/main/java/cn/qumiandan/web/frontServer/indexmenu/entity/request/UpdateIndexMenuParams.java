package cn.qumiandan.web.frontServer.indexmenu.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class UpdateIndexMenuParams implements Serializable{
	
	
	@NotNull(message="首页菜单id不能为空")
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
     * 更新者
     */
    private Long updateId;


    private static final long serialVersionUID = 1L;

}
