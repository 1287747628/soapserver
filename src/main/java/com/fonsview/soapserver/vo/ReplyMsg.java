package com.fonsview.soapserver.vo;

public class ReplyMsg {

    @FieldAlias
    private String CorrelateID;

    public String getCorrelateID() {
        return CorrelateID;
    }

    public void setCorrelateID(String correlateID) {
        CorrelateID = correlateID;
    }
}
