package cn.qumiandan.common.params;

import java.io.Serializable;

import lombok.Data;

 
/**
 * 分页对象
 * @author W
 * 2018年12月30日
 */
@Data
public class PageInfoParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**当前页*/
//	@NotNull(message = "当前页码不能为空")
	private Integer pageNum;
	/**每页大小*/
//	@NotNull(message = "每页大小")
    private Integer pageSize;
    
    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

}
