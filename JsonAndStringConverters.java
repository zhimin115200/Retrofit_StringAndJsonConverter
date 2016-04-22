package com.fighting.couplealarm.network;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Converter;
import retrofit2.Retrofit;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public final class JsonAndStringConverters {
	@Retention(RUNTIME)
	@interface Json {
	}

	@Retention(RUNTIME)
	@interface string {
	}

	public static class QualifiedTypeConverterFactory extends Converter.Factory {
		private final Converter.Factory jsonFactory;
		private final Converter.Factory stringFactory;

		public QualifiedTypeConverterFactory(Converter.Factory jsonFactory, Converter.Factory stringFactory) {
			this.jsonFactory = jsonFactory;
			this.stringFactory = stringFactory;
		}

		@Override
		public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
				Retrofit retrofit) {
			//	对json或String的数据区别解析
			for (Annotation annotation : annotations) {
				if (annotation instanceof Json) {
					return jsonFactory.responseBodyConverter(type, annotations, retrofit);
				}
				if (annotation instanceof string) {
					return stringFactory.responseBodyConverter(type, annotations, retrofit);
				}
			}
			return null;
		}

    /**请求默认是json，可以自定义成String*/
		@Override public Converter<?, RequestBody> requestBodyConverter(Type type,
				Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
			return jsonFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations,
					retrofit);
		}
	}
}
