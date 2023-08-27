package com.online.pasaronlineapp.constant;

public class AppConstant {

    public AppConstant() {
    }

    public static final String DEFAULT_SYSTEM = "SYSTEM";

    public static final String ITEM_DIRECTORY = "D:\\pasar-online-app\\src\\main\\resources\\static\\img\\item-img";

    public enum ResponseCode {

        SUCCESS("SUCCESS", "success"),
        DATA_NOT_FOUND("DATA_NOT_FOUND", "Data Not Found"),
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
        UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown Error"),
        BAD_CREDENTIALS("BAD_CREDENTIALS", "Bad Credentials"),
        ALREADY_EXISTS("ALREADY_EXISTS", "Already Exists");

        private final String code;
        private final String message;

        ResponseCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
