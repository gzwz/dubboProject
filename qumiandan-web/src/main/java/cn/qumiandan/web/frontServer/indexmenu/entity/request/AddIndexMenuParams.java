package cn.qumiandan.web.frontServer.indexmenu.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 添加首页菜单参数
 * @author lrj
 *
 */
@Data
public class AddIndexMenuParams implements Serializable{

    /**
     * 菜单名
     */
	@NotBlank(message="菜单名不能为空")
    private String name;

    /**
     * 跳转url
     */
	@NotBlank(message="跳转url不能为空")
    private String url;

    /**
     * 图片
     */
	@NotBlank(message="图片不能为空")
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
     * 创建者
     */
    private Long createId;

    private static final long serialVersionUID = 1L;
}
