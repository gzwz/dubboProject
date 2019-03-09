package cn.qumiandan.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

@Slf4j
public class DeepCopyBeanUtil {

	private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

	/**
	 * @description 两个类对象之间转换
	 * @param source
	 * @param target
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Object source, Class<T> target) {
		T ret = null;
		if (source != null) {
			if (source instanceof String || 
				source instanceof Integer || 
				source instanceof Long || 
				source instanceof Byte || 
				source instanceof Short || 
				source instanceof Float || 
				source instanceof Double || 
				source instanceof Boolean || 
				source instanceof Character || 
				source instanceof BigDecimal || 
				source instanceof Date) {
			ret = (T) source;
			}else {
				try {
					ret = target.newInstance();
				} catch (ReflectiveOperationException e) {
					throw new RuntimeException("create class[" + target.getName()
							+ "] instance error", e);
				}
			}
			BeanCopier beanCopier = getBeanCopier(source.getClass(), target);
			beanCopier.copy(source, ret, new DeepCopyConverter(target));
		}
		return ret;
	}


	/**
	 * @description 获取BeanCopier
	 * @param source
	 * @param target
	 * @return
	 * @return BeanCopier
	 */
	public static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
		String beanCopierKey = generateBeanKey(source, target);
		if (beanCopierMap.containsKey(beanCopierKey)) {
			return beanCopierMap.get(beanCopierKey);
		} else {
			BeanCopier beanCopier = BeanCopier.create(source, target, true);
			beanCopierMap.putIfAbsent(beanCopierKey, beanCopier);
		}
		return beanCopierMap.get(beanCopierKey);
	}

	/**
	 * @description 生成两个类的key
	 * @param source
	 * @param target
	 * @return
	 * @return String
	 */
	public static String generateBeanKey(Class<?> source, Class<?> target) {
		return source.getName() + "@" + target.getName();
	}
	
	public static class DeepCopyConverter implements net.sf.cglib.core.Converter {

		private Class<?> ret;
		public DeepCopyConverter(Class<?> ret) {
			this.ret = ret;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Object convert(Object value, Class targetClazz, Object methodName) {
			Field field = null;
			if (value instanceof List) {
				//处理List类型
				List<?> values = (List<?>) value;
				List<Object> retList = new ArrayList<>(values.size());
				for (final Object source : values) {
					String tempFieldName = methodName.toString().replace("set","");
					String fieldName = tempFieldName.substring(0, 1)
							.toLowerCase() + tempFieldName.substring(1);
						try {
							field = ret.getDeclaredField(fieldName);
						} catch (NoSuchFieldException | SecurityException e) {
							log.error("获取对象错误：DeepCopyBeanUtil",e);
						}
						Class<?> subClass = null;
						Type genericType = field.getGenericType();
						if(genericType == null) continue;
						if(genericType instanceof ParameterizedType){
	                        ParameterizedType pt = (ParameterizedType) genericType;
	                        //得到泛型里的class类型对象
	                        subClass = (Class<?>)pt.getActualTypeArguments()[0];
	                    }
					if(field.getType().isPrimitive()) {
						//如果List中的泛型是基本类型，则直接赋值
						retList.add(source);
					}else {
						////如果List中的泛型不是基本类型，则进行深拷贝
						retList.add(DeepCopyBeanUtil.convert(source,subClass));
					}
				}
				return retList;
			} else if (value instanceof Map) {
				// 处理Map类型
				log.info("map 类型没有copy");
			} else if (!targetClazz.isPrimitive()) {
				//不是基本类型，则进行深拷贝
				return DeepCopyBeanUtil.convert(value, targetClazz);
			}
			return value;
		}
		}

		/*@SuppressWarnings({"rawtypes"})
		@Override
		public Object convert(Object value, Class targetClazz, Object methodName) {
				if (value instanceof List) {
					//处理List类型
					List values = (List) value;
					List retList = new ArrayList<>(values.size());
					for (final Object source : values) {
						String tempFieldName = methodName.toString().replace("set","");
						String fieldName = tempFieldName.substring(0, 1)
								.toLowerCase() + tempFieldName.substring(1);
						Field[] declaredFields = targetClazz.getDeclaredFields();
						
						//Class clazz =ClassUtils.getElementType(targetClazz, fieldName);
						if(ClassUtils.isPrimitive(clazz)) {
							//如果List中的泛型是基本类型，则直接赋值
							retList.add(source);
						}else {
							////如果List中的泛型不是基本类型，则进行深拷贝
							retList.add(DeepCopyBeanUtil.convert(source, clazz));
						}
						
						System.out.println("qqqqq");
					}
					return retList;
				} else if (value instanceof Map) {
					// 处理Map类型
					
				} else if (!ClassUtils.isPrimitive(targetClazz)) {
					//不是基本类型，则进行深拷贝
					return DeepCopyBeanUtil.convert(value, targetClazz);
				}
				
				return value;
			}
		}*/
}