package com.fonsview.soapserver.vo;

public class ReplyTask {

    public static final String DIST_CDN = "DIST_CDN";
    public static final String DIST_CE = "DIST_CE";
    //
    private String replyType;
    private String replyUrl;
    //
    private String cspID;
    private String lspID;
    private String correlateID;
    private String cmdResult;
    private String resultFileURL;
    //
    private String copId;
    private String sopId;
    private String resultCode;
    private String errorDescription;

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getCorrelateID() {
        return correlateID;
    }

    public void setCorrelateID(String correlateID) {
        this.correlateID = correlateID;
    }

    public String getCspID() {
        return cspID;
    }

    public void setCspID(String cspID) {
        this.cspID = cspID;
    }

    public String getLspID() {
        return lspID;
    }

    public void setLspID(String lspID) {
        this.lspID = lspID;
    }

    public String getReplyUrl() {
        return replyUrl;
    }

    public void setReplyUrl(String replyUrl) {
        this.replyUrl = replyUrl;
    }

    public String getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(String cmdResult) {
        this.cmdResult = cmdResult;
    }

    public String getResultFileURL() {
        return resultFileURL;
    }

    public void setResultFileURL(String resultFileURL) {
        this.resultFileURL = resultFileURL;
    }

    public String getCopId() {
        return copId;
    }

    public void setCopId(String copId) {
        this.copId = copId;
    }

    public String getSopId() {
        return sopId;
    }

    public void setSopId(String sopId) {
        this.sopId = sopId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
