package cn.qumiandan.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * gson辅助类
 * @author yld
 *
 */
public abstract class GsonHelper {

	private static final GsonBuilder INSTANCE = new GsonBuilder();  
	
	static {
		INSTANCE.excludeFieldsWithoutExposeAnnotation();
		//INSTANCE.setPrettyPrinting();
	}
	
	public static Gson create() {
		return INSTANCE.create();
	}
	
	public static String toJson(Object o) {
		return create().toJson(o);
	}
	
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return create().fromJson(json, classOfT);
	}
	
	public static <T> T fromJson(String json, Type typeof) {
		return create().fromJson(json, typeof);
	}
}
