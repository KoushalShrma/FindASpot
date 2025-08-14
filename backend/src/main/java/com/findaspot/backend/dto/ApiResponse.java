package com.findaspot.backend.dto;

/**
 * API Response DTO - yahan par API responses ke liye common structure hai
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // yahan par default constructor hai
    public ApiResponse() {}

    // yahan par success response ke liye constructor hai
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // yahan par success response banane ka static method hai
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // yahan par success response without data banane ka static method hai
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // yahan par error response banane ka static method hai
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // yahan par getters aur setters hain
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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