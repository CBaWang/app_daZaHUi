package com.example.interact_chat;

import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2016/2/27.
 */
public class CommonException extends RuntimeException {

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message,Throwable throwable){
        super(message,throwable);
    }

    public CommonException(Throwable throwable)
    {
        super(throwable);
        // TODO Auto-generated constructor stub
    }
}
