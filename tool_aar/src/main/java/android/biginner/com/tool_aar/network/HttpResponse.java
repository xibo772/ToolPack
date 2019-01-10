package android.biginner.com.tool_aar.network;

import java.io.Serializable;

public class HttpResponse<T> implements Serializable {
    /*服务器响应数据*/
    //"code":"0",
    //"message":"成功",
    //"data":
    private String code=null;
    private String message=null;
    public T data=null;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
