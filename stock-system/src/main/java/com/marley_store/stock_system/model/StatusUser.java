package com.marley_store.stock_system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUser {
    int status = 0;
    String message = "";

    public StatusUser(int status, String message){
        this.status = status;
        this.message = message;
    }
}
