package com.marley_store.stock_system.dto.restMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestMessageDTO {
    private int status;
    private String message;
}
