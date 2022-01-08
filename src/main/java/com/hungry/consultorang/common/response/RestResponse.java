package com.hungry.consultorang.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestResponse {
    // 상태 enum 코드
    public static enum StatusCode{
        SUCCESS("0"), FAIL("1");
        public final String value;
        private StatusCode(final String value){
            this.value = value;
        }
    }
    private String retCode;
    private String errMsg;
    private Object data;

    public RestResponse setSuccess() {
        this.retCode = StatusCode.SUCCESS.value;
        return this;
    }

    public RestResponse setSuccess(String message) {
        this.retCode = StatusCode.SUCCESS.value;
        this.data = message;

        return this;
    }

    public RestResponse setSuccess(Object o) {
        this.retCode = StatusCode.SUCCESS.value;
        this.data = o;
        return this;
    }


    public RestResponse setFail(Exception e) {
        this.retCode = StatusCode.FAIL.value;
        this.errMsg = e.getMessage();
        return this;
    }

    public RestResponse setFail(String errMsg) {
        this.retCode = StatusCode.FAIL.value;
        this.errMsg = errMsg;
        return this;
    }
}
