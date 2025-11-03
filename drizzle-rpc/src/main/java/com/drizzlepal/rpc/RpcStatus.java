package com.drizzlepal.rpc;

public interface RpcStatus {

    public String getCode();

    public String getMessage();

    public void setMessage(String message);

    public void setCode(String code);

}
