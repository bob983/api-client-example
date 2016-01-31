package net.helpscout.core;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static feign.FeignException.errorStatus;

public class CoreApiErrorDecoder implements ErrorDecoder {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class NotFounException extends RuntimeException {}

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new NotFounException();
        }

        return errorStatus(methodKey, response);
    }
}