package ecommerce.utils;

import org.springframework.http.HttpStatus;

public class IException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String errorMessage;
    private final String errorCode;

    public IException(HttpStatus statusCode, String errorMessage, String errorCode) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static IException ofValidation(String errorCode, String errorMessage) {
        return new IException(HttpStatus.BAD_REQUEST, errorMessage, errorCode);
    }

    public static IException ofError(String errorCode, String errorMessage) {
        return new IException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, errorCode);
    }
}
