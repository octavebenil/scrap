package com.sbosoft.scrap.web.exceptions;

import com.sbosoft.scrap.persistances.models.MagicCard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MagicCardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String magicCardNotFound(MagicCardNotFoundException ex){
        return ex.getMessage();
    }
}
