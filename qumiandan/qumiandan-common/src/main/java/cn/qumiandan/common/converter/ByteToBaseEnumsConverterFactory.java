package cn.qumiandan.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import cn.qumiandan.common.interfaces.IByteEnum;

/**
 * Byte code 枚举转化工厂
 * @author yuleidian
 * @version 创建时间：2018年12月19日 下午2:23:06
 */
public class ByteToBaseEnumsConverterFactory implements ConverterFactory<Byte, IByteEnum>{

	@Override
	public <T extends IByteEnum> Converter<Byte, T> getConverter(Class<T> targetType) {
		if (!targetType.isEnum()) {
			throw new UnsupportedOperationException("只支持转换到枚举类型");
		}
		return new ByteToBaseEnumConverter<T>(targetType);
	}

	private class ByteToBaseEnumConverter<T extends IByteEnum> implements Converter<Byte, T> {
		private final Class<T> enumType;

		public ByteToBaseEnumConverter(Class<T> enumType) {
			this.enumType = enumType;
		}

		@Override
		public T convert(Byte s) {
			for (T t : enumType.getEnumConstants()) {
				if (s.equals(t.getCode())) {
					return t;
				}
			}
			return null;
		}
	}
}
