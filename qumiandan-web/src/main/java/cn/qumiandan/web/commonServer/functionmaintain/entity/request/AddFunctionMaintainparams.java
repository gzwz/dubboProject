package cn.qumiandan.web.commonServer.functionmaintain.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 添加维护功能信息参数类
 * @author lrj
 *
 */
@Data
public class AddFunctionMaintainparams implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 维护类型（1正常，2升级维护）
     */
	@NotNull(message = "维护类型不能为空")
//	@Max()
    private Byte type;

    /**
     * 功能名称
     */
	@NotBlank(message = "功能名称不能为空")
    private String name;

    /**
     * 所属模块
     */
	@NotBlank(message = "所属模块不能为空")
    private String belongModule;

    /**
     * 父类模块
     */
    private Long parentModule;

    /**
     * 所属url
     */
    private String url;


    /**
     * 创建者
     */
    private Long createId;

}
