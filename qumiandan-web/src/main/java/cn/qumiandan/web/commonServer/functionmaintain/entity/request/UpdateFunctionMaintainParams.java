package cn.qumiandan.web.commonServer.functionmaintain.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 更新维护功能信息参数类
 * @author lrj
 *
 */
@Data
public class UpdateFunctionMaintainParams implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "功能模块id不能为空")
    private Long id;

    /**
     * 维护类型（1正常，2升级维护）
     */
    private Byte type;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 所属模块
     */
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
     * 更新者
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;


}
