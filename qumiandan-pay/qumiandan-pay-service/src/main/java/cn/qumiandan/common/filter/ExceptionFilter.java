package cn.qumiandan.common.filter;

import java.lang.reflect.Method;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.service.GenericService;

import cn.qumiandan.common.exception.QmdException;
/**
 * 自定义拦截异常
 * @author yuleidian
 * @version 创建时间：2019年1月6日 下午5:36:39
 */
@Activate(group = {Constants.PROVIDER})
public class ExceptionFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			Result result = invoker.invoke(invocation);
			if (result.hasException() && GenericService.class != invoker.getInterface()) {
				try {
					Throwable exception = result.getException();
					// 项目定义的一个异常
					if (exception instanceof QmdException) {
						return result;
					}

					// 如果是checked异常，直接抛出
					if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
						return result;
					}
					// 在方法签名上有声明，直接抛出
					try {
						Method method = invoker.getInterface().getMethod(invocation.getMethodName(),
								invocation.getParameterTypes());
						Class<?>[] exceptionClassses = method.getExceptionTypes();
						for (Class<?> exceptionClass : exceptionClassses) {
							if (exception.getClass().equals(exceptionClass)) {
								return result;
							}
						}
					} catch (NoSuchMethodException e) {
						return result;
					}

					// 未在方法签名上定义的异常，在服务器端打印ERROR日志

					// 异常类和接口类在同一jar包里，直接抛出
					String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
					String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
					if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
						return result;
					}
					// 是JDK自带的异常，直接抛出
					String className = exception.getClass().getName();
					if (className.startsWith("java.") || className.startsWith("javax.")) {
						return result;
					}
					// 是Dubbo本身的异常，直接抛出
					if (exception instanceof RpcException) {
						return result;
					}

					// 否则，包装成RuntimeException抛给客户端
					return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
				} catch (Throwable e) {
					return result;
				}
			}
			return result;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
