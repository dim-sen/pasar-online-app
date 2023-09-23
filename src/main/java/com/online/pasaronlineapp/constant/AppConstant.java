package com.online.pasaronlineapp.constant;

import lombok.Getter;

public class AppConstant {

    public static final Integer PAGE_MAX = 10;

    public AppConstant() {
    }

    @Getter
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

    }

    @Getter
    public enum Role {
        ADMIN("ADMIN"),
        CUSTOMER("SUPER_ADMIN");

        private final String roleName;

        Role(String roleName) {
            this.roleName = roleName;
        }

    }

    @Getter
    public enum FlashAttribute {
        SAVE_SUCCESS("success", "Successfully Added"),
        SAVE_FAILED("failed", "Failed to Add"),
        UPDATE_SUCCESS("success", "Successfully Updated"),
        UPDATE_FAILED("failed", "Failed to Update"),
        DELETE_SUCCESS("success", "Successfully Deleted"),
        DELETE_FAILED("failed", "Failed to Delete");

        private final String attributeName;
        private final String attributeValue;

        FlashAttribute(String attributeName, String attributeValue) {
            this.attributeName = attributeName;
            this.attributeValue = attributeValue;
        }
    }
}
