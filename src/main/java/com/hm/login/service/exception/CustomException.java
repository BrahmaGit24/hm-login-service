package com.hm.login.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
   /* public CustomException(String message, HttpStatus httpStatus) {
    }*/

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String message;
    private HttpStatus status;
}
