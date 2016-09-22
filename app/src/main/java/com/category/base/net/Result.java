package com.category.base.net;

/**
 * @author yhw(email:861574834@qq.com)
 * @date 2016-09-20 23:27
 * @package com.category.base.net
 * @description Result  TODO(界面功能描述)
 * @params TODO(进入界面传参描述)
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
