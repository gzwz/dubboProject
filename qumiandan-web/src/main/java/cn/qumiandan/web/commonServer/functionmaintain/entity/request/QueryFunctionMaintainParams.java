package cn.qumiandan.web.commonServer.functionmaintain.entity.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询维护功能参数类
 * @author lrj
 *
 */
@Data
public class QueryFunctionMaintainParams implements Serializable {

	private static final long serialVersionUID = 1L;


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


}
