package au.com.polonious.integration.utils;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Log
public class FeignConfiguration implements RequestInterceptor, ErrorDecoder{
    String token = "";
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (token.isEmpty()) token = PoloniusUtil.getToken();

        if (token != null && !token.isEmpty())
            requestTemplate.header("Authorization", "Bearer "+ token);
        else log.info(String.format("Token (%s) not proper", token));
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 300) {
            String errorMsg;
            try {
                InputStream is = response.body().asInputStream();
//                byte[] bytes = IOUtils.toByteArray(is); //  Stream is closed error on this line

//                Response copiedResponse = Response.builder().status(response.status()).reason(response.reason()).headers(response.headers()).body(bytes).build();
//
//                errorMsg = String.valueOf(bytes);
                ByteSource byteSource = new ByteSource() {
                    @Override
                    public InputStream openStream() throws IOException {
                        return response.body().asInputStream();
                    }
                };
                // TODO: It seems that the 'response' has already been read once, and cannot be read again on this line.
                errorMsg = byteSource.asCharSource(Charsets.UTF_8).read(); //  Stream is closed error on this line
            }catch (Exception ex){
                errorMsg = "Error reading error message";
                log.info(String.format("Feign Error: %s", ex.getMessage()));
            }

            throw new RuntimeException(errorMsg);
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
