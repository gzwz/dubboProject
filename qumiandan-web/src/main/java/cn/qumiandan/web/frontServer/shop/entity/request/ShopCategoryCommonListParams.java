package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description：店铺分类分页列表参数
 * @author：zhuyangyong
 * @date: 2018/11/21 13:58
 */
@Getter
@Setter
@ToString
public class ShopCategoryCommonListParams extends CommonParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long parentId;  //上级分类id
}
