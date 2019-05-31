package com.fonsview.soapserver.vo;

public class CDReplyMsg extends ReplyMsg {

    @FieldAlias
    private String CMSID;
    @FieldAlias
    private String SOPID;
    @FieldAlias
    private String ResultCode;
    @FieldAlias
    private String ErrorDescription;
    @FieldAlias
    private String ResultFileURL;

    public String getCMSID() {
        return CMSID;
    }

    public void setCMSID(String CMSID) {
        this.CMSID = CMSID;
    }

    public String getSOPID() {
        return SOPID;
    }

    public void setSOPID(String SOPID) {
        this.SOPID = SOPID;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }

    public String getResultFileURL() {
        return ResultFileURL;
    }

    public void setResultFileURL(String resultFileURL) {
        ResultFileURL = resultFileURL;
    }
}
