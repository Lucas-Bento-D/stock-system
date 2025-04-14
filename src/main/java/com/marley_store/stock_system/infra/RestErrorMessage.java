package com.marley_store.stock_system.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private int status;
    private String message;
}
