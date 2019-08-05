package com.fonsview.soapserver.vo;

public class CMSReplyMsg extends ReplyMsg {

    @FieldAlias
    private String CSPID;
    @FieldAlias
    private String LSPID;
    @FieldAlias
    private String CorrelateID;
    @FieldAlias
    private Integer CmdResult;
    @FieldAlias
    private String ResultFileURL;

    public String getCSPID() {
        return CSPID;
    }

    public void setCSPID(String CSPID) {
        this.CSPID = CSPID;
    }

    public String getLSPID() {
        return LSPID;
    }

    public void setLSPID(String LSPID) {
        this.LSPID = LSPID;
    }

    @Override
    public String getCorrelateID() {
        return CorrelateID;
    }

    @Override
    public void setCorrelateID(String correlateID) {
        CorrelateID = correlateID;
    }

    public Integer getCmdResult() {
        return CmdResult;
    }

    public void setCmdResult(Integer cmdResult) {
        CmdResult = cmdResult;
    }

    public String getResultFileURL() {
        return ResultFileURL;
    }

    public void setResultFileURL(String resultFileURL) {
        ResultFileURL = resultFileURL;
    }
}
