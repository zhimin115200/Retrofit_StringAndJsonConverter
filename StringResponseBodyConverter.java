import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringResponseBodyConverter <T> implements Converter<ResponseBody, T>{

	@SuppressWarnings("unchecked")
	@Override
	public T convert(ResponseBody value) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = value.byteStream();
	  ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		int i=-1; 
		while((i=is.read())!=-1){ 
			baos.write(i); 
		} 
		String response =baos.toString();
		return (T) response;
	}
}
