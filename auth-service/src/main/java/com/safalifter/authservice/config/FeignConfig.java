package com.safalifter.authservice.config;

import com.safalifter.authservice.exc.CustomException;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Response;

//@Configuration
public class FeignConfig {
//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new CustomErrorDecoder();
//    }
//
//    public class CustomErrorDecoder implements ErrorDecoder {
//        @Override
//        public Exception decode(String methodKey, Response response) {
//            String message = "Unknown error";
//            try {
//                // Assuming the error body contains a JSON object with an "error" field
//                String body = IOUtils.toString(response.body().asReader());
//                JSONObject json = new JSONObject(body);
//                message = json.getString("error");
//            } catch (Exception e) {
//                // Log the error or handle it
//            }
//            return new CustomException(message, response.status());
//        }
//    }
}
