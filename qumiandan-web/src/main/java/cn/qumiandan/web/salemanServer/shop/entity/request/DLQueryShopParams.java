package cn.qumiandan.web.salemanServer.shop.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DLQueryShopParams {

	@NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    
	@NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
	
	public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
