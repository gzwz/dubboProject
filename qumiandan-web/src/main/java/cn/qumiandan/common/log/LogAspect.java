package cn.qumiandan.common.log;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.qumiandan.common.LogInfo;
import lombok.extern.slf4j.Slf4j;
/**
 * 日志切面,打印入参，出参和异常信息<br> 
 * @author WLZ
 * 2018年11月6日
 */
@Aspect
@Slf4j
@Component
public class LogAspect {
    
 	private static final Map<String, String> mthodNames = new ConcurrentHashMap<String, String>();
    
    /* 添加方法英文名称和中文名称映射*/
    static {
        mthodNames.put("userRegister", "客户注册");
    }
    
    /**
     * 根据方法简单签名获取中文名称
     */
    public static String getMethodName(String methodName) {
        return mthodNames.get(methodName);
    }
	    
	    
    @Pointcut("execution(* cn.qumiandan..*.*(..))")
    public void logPoint() {
        
    }
    
    @Before("logPoint()")
    public void beforeInvoking(JoinPoint point) {
        log(point, null);
    }
    
    @AfterReturning(pointcut = "logPoint()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {
        log(point, result);
    }
    
    @AfterThrowing(pointcut = "logPoint()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        log(point, e);
    }
    
    /**
     * 日志打印私有方法
     */
    private void log(JoinPoint point, Object obj) {
        /** 方法中文名*/
        String methodName;
        String sign = "Start";
        if(obj != null) {
            sign = "End";
            if(obj instanceof Exception) {
                sign = "Exception";
            }
        }
        Signature signature = point.getSignature();
        /* 接口名*/
        String interfaceName = signature.getDeclaringType().getSimpleName();
        /* 方法名*/
        String methodNam = signature.getName();
        methodName = getMethodName(methodNam);
        StringBuilder sb = new StringBuilder();
        
        if(sign.equals("Start")) {
            /* 打印入参 */
            log.info(LogInfo.UC.getName()+": " + interfaceName + "." + methodNam + "(" + methodName + ") - 调用开始");
            for(Object param : point.getArgs()) {
                sb.append(param + " ");
            }
            log.info(LogInfo.InterfaceInParam.getName() + sb.toString());
        } else if(sign.equals("End")) {
            /* 打印出参*/
            /*log.info(LogInfo.UC.getName()+": " + interfaceName + "." + methodNam + "(" + methodName + ") - 调用结束");
            for(Object param : point.getArgs()) {
                sb.append(param + " ");
            }
            log.info(LogInfo.InterfaceOutParam.getName()  + sb.toString());*/
        } else {
            /* 打印异常信息 LogInfo.InterfaceException.getName()*/
            log.error(LogInfo.UC.getName()+": " + interfaceName + " 接口发送异常", obj);
        }
    }
}
