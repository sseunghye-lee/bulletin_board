package com.seung.pilot.commons.exception;

import com.seung.pilot.commons.enums.ResultCode;
import lombok.Getter;

public class SignInException extends Exception{

    @Getter
    private ResultCode resultCode;

    public SignInException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

}
