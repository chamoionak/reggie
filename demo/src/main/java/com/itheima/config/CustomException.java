package com.itheima.config;

import com.itheima.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomException extends RuntimeException{
    public CustomException(String msg){
        super(msg);
    }


}
