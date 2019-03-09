package cn.qumiandan.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


/**
 * Bean复制工具
 * @author WLZ
 * 2018年12月13日
 */
@Slf4j
public class  CopyBeanUtil {
	/**
	 * Bean复制
	 * @param fromObj 原对象
	 * @param toObj 目标对象
	 */
	public static <T> T copyBean(Object fromObj, Class<T> target) {
		if (fromObj == null || target == null) {
			return null;
		}
		T result = DeepCopyBeanUtil.convert(fromObj, target);
		return result;
	}
	
	
	/**
	 * copy list对象
	 * @param fromObjList 原数据对象
	 * @param toObjList 目标对象泛型类 T
	 * @return 目标 new List<T> 
	 */
	public static <T,B> List<T> copyList(List<B> fromObjList, Class<T> toObjClass) {
		if (fromObjList == null || fromObjList.size() == 0) {
			return null;
		}
		
		List<T> toObjList = new ArrayList<T>(fromObjList.size());
		
		for (int i = 0; i < fromObjList.size(); i++) {
			try {
				T toObj = toObjClass.newInstance();
				toObj = copyBean(fromObjList.get(i), toObjClass);
				toObjList.add(toObj);
			}catch(InstantiationException e) {
				log.error("copyList error:", e);
			}catch(IllegalAccessException e) {
				log.error("copyList error:", e);
			}
		}
		return toObjList;
	}
	
	/**
	 * copy list对象
	 * @param fromObjList
	 * @param toObjList
	 * @return 
	 */
	public static <T, R> List<T> copyList2(List<R> fromObjList, Class<T> toObjClass) {
		List<T> toObjList = new ArrayList<T>();
		if (fromObjList == null || fromObjList.size() == 0) {
			return toObjList;
		}
		
		for (int i = 0; i < fromObjList.size(); i++) {
			try {
				T toObj = toObjClass.newInstance();
				toObj = copyBean(fromObjList.get(i), toObjClass);
				toObjList.add(toObj);
			}catch(Exception e) {
				log.error("copyList error:", e);
			}
		}
		
		return toObjList;
	}

	/**
	 * str列表拼装到paramMap上当作in条件
	 * 
	 * @param strList
	 * @param paramMap
	 * @param key
	 */
/*	public static void perfectParamMapIns(List<String> strList, HashMap<String, Object> paramMap, String key) {
		if (strList == null || strList.size() == 0 || paramMap == null || !paramMap.containsKey(key)) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("in (");
		for (String str : strList) {
			sb.append(" " + str + ",");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		sb.append(" " + ")");
		paramMap.put(key, sb.toString());
	}
	*/
	/**
	 * bean转化成hashMap，key为属性名，value为属性值
	 * 
	 * @param fromObj 来源bean
	 * @param toObj 转化后的hashMap
	 */
/*	public static void copyBean2Map(Object fromObj, HashMap<String, Object> toObj) {
		if (fromObj == null || toObj == null) {
			return;
		}
		BeanUtil.copyBean2Map(fromObj, toObj);
	}*/

	/**
	 * hashMap转化为bean
	 * 
	 * @param fromObj
	 * @param toObj
	 */
/*	@SuppressWarnings("rawtypes")
	public static void copyMap2Bean(HashMap fromObj, Object toObj) {
		if (fromObj == null || toObj == null) {
			return;
		}
		BeanUtil.copyMap2Bean(fromObj, toObj);
	}*/

	/**
	 * 将实体列表转化成hashMap数组，批量操作时使用
	 * 
	 * @param beans
	 *            bean列表
	 * @return hashMap数组
	 */
/*	public Map<String, Object>[] copyBeanList2Maps(List<Object> beans) {
		if (beans == null || beans.size() == 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object>[] maps = new HashMap[beans.size()];
		for (int i = 0; i < beans.size(); i++) {
			maps[i] = new HashMap<String, Object>();
			copyBean2Map(beans.get(i), maps[i]);
		}
		return maps;
	}*/

//	public static void main(String[] args) {
//		OrderVO vo = new OrderVO();
//		OrderResourceVO resource = new OrderResourceVO();
//		resource.setFrozenSerialNo("1111");
//		resource.setCreateTime(new Date());
//		resource.setDeleteFlag(1);
//		resource.setOrderId(1l);
//		resource.setResourceAmount(new BigDecimal(100));
//		List<OrderResourceVO> list = new ArrayList<OrderResourceVO>();
//		list.add(resource);
//		vo.setOrderResourceList(list);
//		
//		com.ibm.capabilitycenter.order.vo.main.OrderVO copyVo = BeanCopyUtil.copyBean(vo, com.ibm.capabilitycenter.order.vo.main.OrderVO.class);
//		
//		List<com.ibm.capabilitycenter.order.vo.main.OrderResourceVO> toList =BeanCopyUtil.copyList(list, com.ibm.capabilitycenter.order.vo.main.OrderResourceVO.class);
//		
//		System.out.println(copyVo);
//		System.out.println(toList);
//		
//	}
	
}
