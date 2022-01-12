package com.hungry.consultorang.common.exception;

import lombok.experimental.StandardException;

// TODO: 2022-01-12 Lombok에서 @StandardException 생성자 미생성 오류 고쳐지면 바꿀것
//@StandardException 보류 : Lombok에서 아직 해당 annotation에 대한 constructer 미생성 오류 고치치 못함
public class CustomException extends Exception{
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }
}
