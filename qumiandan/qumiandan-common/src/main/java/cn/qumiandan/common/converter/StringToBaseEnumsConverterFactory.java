package cn.qumiandan.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import cn.qumiandan.common.interfaces.IStringEnum;

/**
 * stirng code 枚举转化工厂
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午3:24:23
 */
public class StringToBaseEnumsConverterFactory implements ConverterFactory<String, IStringEnum> {

	@Override
	public <T extends IStringEnum> Converter<String, T> getConverter(Class<T> targetType) {
		if (!targetType.isEnum()) {
			throw new UnsupportedOperationException("只支持转换到枚举类型");
		}
		return new StringToBaseEnumConverter<T>(targetType);
	}

	private class StringToBaseEnumConverter<T extends IStringEnum> implements Converter<String, T> {
		private final Class<T> enumType;

		public StringToBaseEnumConverter(Class<T> enumType) {
			this.enumType = enumType;
		}

		@Override
		public T convert(String s) {
			for (T t : enumType.getEnumConstants()) {
				if (s.equals(t.getCode())) {
					return t;
				}
			}
			return null;
		}
	}
}
