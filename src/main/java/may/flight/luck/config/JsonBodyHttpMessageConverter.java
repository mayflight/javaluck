package may.flight.luck.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonBodyHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!(object instanceof String)) {
            final HttpHeaders headers = outputMessage.getHeaders();
            headers.setContentType(new MediaType("application","json"));
        }
        super.writeInternal(object, type, outputMessage);
    }
}
