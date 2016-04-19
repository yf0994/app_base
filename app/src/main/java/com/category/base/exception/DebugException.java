package com.category.base.exception;

/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public class DebugException extends RuntimeException {
    public DebugException() {

    }
    public DebugException(String msg){
        super(msg);
    }
}
