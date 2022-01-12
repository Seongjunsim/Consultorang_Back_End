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
        SUCCESS("0"), CUSTOM_FAIL("1"), PROGRAMMING_FAIL("2");
        public final String value;
        private StatusCode(final String value){
            this.value = value;
        }
    }
    private String retCode;
    private String errNm;
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


    public RestResponse setCustomFail(Exception e) {
        this.retCode = StatusCode.CUSTOM_FAIL.value;
        this.errNm = e.getClass().getSimpleName();
        this.errMsg = e.getMessage();
        return this;
    }

    public RestResponse setProgrammingFail(Exception e) {
        this.retCode = StatusCode.PROGRAMMING_FAIL.value;
        this.errNm = e.getClass().getSimpleName();
        this.errMsg = e.getMessage();
        return this;
    }
}
