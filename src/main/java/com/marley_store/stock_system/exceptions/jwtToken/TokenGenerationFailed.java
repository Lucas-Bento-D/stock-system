package com.marley_store.stock_system.exceptions.jwtToken;

public class TokenGenerationFailed extends RuntimeException {
    public TokenGenerationFailed(){super("Error to generate token.");}
    public TokenGenerationFailed(String message) {
        super(message);
    }
}
