package cn.qumiandan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证 shopId
 * 验证操作人是否具有该店铺操作权限
 * @author yuleidian
 * @version 创建时间：2018年12月31日 下午5:29:13
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateShopManager {

}
