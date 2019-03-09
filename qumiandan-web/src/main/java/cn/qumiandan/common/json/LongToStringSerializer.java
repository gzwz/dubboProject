package cn.qumiandan.common.json;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * jackson long转stirng 序列化
 * @author lrj
 *
 */
public class LongToStringSerializer extends JsonSerializer<Long>{

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (Objects.nonNull(value)) {
			gen.writeString(value.toString());
		}
	}
}
