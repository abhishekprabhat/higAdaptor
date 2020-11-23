package au.com.polonious.integration.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.java.Log;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log
public class FeignConfiguration implements RequestInterceptor{
    String token = "";

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        if (token.isEmpty()) token = PoloniusUtil.getToken();

        if (token != null && !token.isEmpty())
            requestTemplate.header("Authorization", "Bearer "+ token);
        else log.info(String.format("Token (%s) not proper", token));
    }
}
