package com.indracompany.refis.contracts.model.dto.base;

import lombok.Data;

@Data
public class BaseResponse {

    private Status status;

    @Data
    public static class Status {
        private int id;
        private String description;
    }

}
