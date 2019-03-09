package cn.qumiandan.common.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.annotation.ValidateShopManager;
import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 切面设置验证参数
 * @author yuleidian
 * @version 创建时间：2018年12月31日 下午5:40:23
 */
@Slf4j
@Aspect
@Component
public class ControllerAopParam {

	@Reference
	private IShopUserService shopUserService;
	
	@Pointcut("execution(* cn.qumiandan.web..*Controller.add*(..))")
	public void controllerAdd() {

	}
	
	@Pointcut("execution(* cn.qumiandan.web..*Controller.update*(..))")
	public void controllerUpdate() throws Exception {
		
	}
	
	/**
	 * 添加方法自动注入 当前操作人
	 * @param point
	 * @throws Exception
	 */
	@Before("controllerAdd()")
	public void controllerAdd(JoinPoint point) throws Exception  {
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			if(args[0] == null) {
				return;
			}
			Class<?> arg = args[0].getClass();
			try {
				Method method = arg.getMethod("setCreateId", Long.class);
				method.invoke(args[0], ShiroUtils.getUserId());
				// 没有找到方法会抛出异常 不必处理 
			} catch (NoSuchMethodException e) {
			}
		}
	}
	
	/**
	 * 修改方法自动注入 当前操作人
	 * @param point
	 * @throws Exception
	 */
	@Before("controllerUpdate()")
	public void controllerUpdate(JoinPoint point) throws Exception  {
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			Class<?> arg = args[0].getClass();
			try {
				Method method = arg.getMethod("setUpdateId", Long.class);
				method.invoke(args[0], ShiroUtils.getUserId());
				// 没有找到方法会抛出异常 不必处理 
			} catch (NoSuchMethodException e) {
			}
		}
	}
	
	/**
	 * 验证当前操作人 是否有操作该店铺的权限
	 * 拦截自定义注解 @ValidateShopManager 修饰的controller方法
	 * @param point
	 * @param validateShopManager
	 * @throws Exception
	 */
	@Before("execution(* cn.qumiandan.web..*Controller.*(..)) && @annotation(validateShopManager)")
	public void controllerValidateShopManager(JoinPoint point, ValidateShopManager validateShopManager) throws Exception {
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			Class<?> arg = args[0].getClass();
			try {
				Method method = arg.getMethod("getShopId");
				Object id = method.invoke(args[0]);
				if (Objects.nonNull(id)) {
					long shopId = (long) id;
					long currentUserId = ShiroUtils.getUserId();
					Set<Long> shopIdsByUserId = shopUserService.getShopIdsByUserId(currentUserId);
					if (!CollectionUtils.isEmpty(shopIdsByUserId)) {
						if (!shopIdsByUserId.contains(shopId)) {
							log.info("操作人|currentUserId:" + currentUserId + "试图修改店铺|shopId:" + shopId + "的业务");
							throw new QmdException(GwErrorCode.GW1003);
						}
					}
				}
			} catch (NoSuchMethodException e) {
				log.info("没有找到getShopId方方法");
				throw new QmdException("shopId不能为空");
			}
		}
	}
	
	
	/**
	 * 验证当前操作人 是否有操作该店铺的权限
	 * 拦截自定义注解 @ValidateShopManager 修饰的controller方法
	 * @param point
	 * @param validateShopManager
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Before("execution(* cn.qumiandan.web..*Controller.*(..)) && @annotation(validateShopsManager)")
	public void controllerValidateShopsManager(JoinPoint point, ValidateShopsManager validateShopsManager) throws Exception {
		Object[] args = point.getArgs();
		if (args != null && args.length > 0) {
			Class<?> arg = args[0].getClass();
			try {
				Method method = arg.getMethod("getShopIds");
				Object ids = method.invoke(args[0]);
				if (Objects.nonNull(ids)) {
					List<Long> shopIds = (List<Long>) ids;
					long currentUserId = ShiroUtils.getUserId();
					Set<Long> shopIdsByUserId = shopUserService.getShopIdsByUserId(currentUserId);
					if (!CollectionUtils.isEmpty(shopIdsByUserId)) {
						if (!shopIdsByUserId.containsAll(shopIds)) {
							log.info("操作人|currentUserId:" + currentUserId + "试图修改店铺|shopId:" + shopIds.toString() + "的业务");
							throw new QmdException(GwErrorCode.GW1003);
						}
					} else {
						log.info("操作人|currentUserId:" + currentUserId + "试图修改店铺|shopId:" + shopIds.toString() + "的业务");
						throw new QmdException(GwErrorCode.GW1003);
					}
				}
			} catch (NoSuchMethodException e) {
				log.info("没有找到getShopIds方方法");
				throw new QmdException("shopIds不能为空");
			}
		}
	}
	
}
