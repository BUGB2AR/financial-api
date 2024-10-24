package com.financial.api.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BussinesException extends RuntimeException {

   public BussinesException(String message) {
       super(message);
   }
}
