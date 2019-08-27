package www.app.ypy.com.mvpandroid.network;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
    private int code;
    private int total;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return total;
    }

    public void setCount(int total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}