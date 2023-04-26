package ecommerce.utils;

public class ApiReturn<T> {

    private String status;
    private String message;
    private T data;

    public ApiReturn(String status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ApiReturn<T> success(T data) {
        return new ApiReturn<>("success", null, data);
    }

}