package cn.qumiandan.indexpicture.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 总后台查询轮播图参数
 * @author lrj
 *
 */
@Data
public class QueryIndexPictureParamsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 区编号
	 */
	private String areaCode;
	
	/**
	 * 状态（0：删除 ；1：正常；2禁用；）
	 */
	private Byte status;
	
	/**
	 * 是否有效（1：是；2：不是）
	 */
	private Byte isValid;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	
	
	public Integer getPageNum() {
		return this.pageNum == null ? 1 : this.pageNum;
	}

	public Integer getPageSize() {
		return this.pageSize == null ? 10 : this.pageSize;
	}

}
