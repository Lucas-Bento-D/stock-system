package com.marley_store.stock_system.exceptions.jwtToken;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(){ super("Token not found");}
    public TokenNotFoundException(String message){ super(message);}
}
