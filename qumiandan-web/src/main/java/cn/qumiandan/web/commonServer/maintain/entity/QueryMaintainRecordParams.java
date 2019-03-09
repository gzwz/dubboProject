package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 查询维护记录参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class QueryMaintainRecordParams extends PageInfoParams implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long maintainTypeId;
}
