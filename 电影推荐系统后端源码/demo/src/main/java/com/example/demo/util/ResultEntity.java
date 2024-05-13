package com.example.demo.util;

import lombok.Data;

@Data
public class ResultEntity {
    private String code;
    private Object data;
    private String message;

    void ResultEntity() {

    }
    void ResultEntity(String code,Object data,String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
