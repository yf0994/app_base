package com.category.base.net;

/**
 * @author yhw(email:861574834@qq.com)
 * @date 2016-09-20 22:57
 * @package com.category.base.net
 * @description ReuqestError  TODO(请求错误信息实体对象声明)
 */
public class RequestError {

    private Exception mException;
    private int mErrorCode;
    private String mErrorMsg;

    public RequestError(Exception exception) {
        this.mException = exception;
    }

    public RequestError(int errorCode) {
        this.mErrorCode = errorCode;
    }

    public RequestError(int errorCode, String message) {
        this.mErrorCode = errorCode;
        this.mErrorMsg = message;
    }

    public RequestError(Exception exception, int errorCode) {
        this.mException = exception;
        this.mErrorCode = errorCode;
    }

    public RequestError(Exception exception, int errorCode, String errorMsg) {
        this.mException = exception;
        this.mErrorCode = errorCode;
        this.mErrorMsg = errorMsg;
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }
}
