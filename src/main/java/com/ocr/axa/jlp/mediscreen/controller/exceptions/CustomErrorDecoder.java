package com.ocr.axa.jlp.mediscreen.controller.exceptions;

import feign.codec.ErrorDecoder;
import feign.Response;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {

        if(reponse.status() == 406 ) {
            return new ProductBadRequestException(
                    "Requête incorrecte "
            );
        }

        return defaultErrorDecoder.decode(invoqueur, reponse);
    }

}
