package cn.qumiandan.common.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @description：分页对象
 * @author：zhuyangyong
 * @date: 2018/11/14 9:27
 */
@Data
public class CommonParams implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer pageNum;
	
    private Integer pageSize;
    
    private Long userId;

    private String name;
	
	private String mobile;
	
	private Long userRoleId;
	
	private Long roleId;
	
	private Long shopId;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date time;
	
	private List<Long> shopIds;
	
    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

}
