import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory{
	/** Create an instance using a default {@link Persister} instance for conversion. */
	public static StringConverterFactory create() {
		return new StringConverterFactory();
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
			Retrofit retrofit) {
		if (!(type instanceof Class)) {
			return null;
		}
		Class<?> cls = (Class<?>) type;
		return new StringResponseBodyConverter<Object>();
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
			Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		if (!(type instanceof Class)) {
			return null;
		}
		return new StringResponseBodyConverter<RequestBody>();
	}
}
