package com.sbosoft.scrap.web.exceptions;

public class MagicCardNotFoundException extends RuntimeException {
    MagicCardNotFoundException(long idCard){
        super("Card not found " + idCard);
    }
}
