package com.asoulfan.asfbbs.exception;

import com.asoulfan.asfbbs.api.IErrorCode;

/**

 * : 自定义API异常

 * @author Cscar
 * @since 2021-07-26 10:20
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
