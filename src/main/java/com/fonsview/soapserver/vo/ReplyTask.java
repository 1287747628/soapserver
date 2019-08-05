package com.fonsview.soapserver.vo;

public class ReplyTask {

    public static final String DIST_MIGU = "DIST_MIGU";
    public static final String DIST_CE = "DIST_CE";
    public static final String DIST_CD = "DIST_CD";
    public static final String DIST_CMS = "DIST_CMS";
    //
    private String replyType;
    private String replyUrl;
    private ReplyMsg replyMsg;

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getReplyUrl() {
        return replyUrl;
    }

    public void setReplyUrl(String replyUrl) {
        this.replyUrl = replyUrl;
    }

    public ReplyMsg getReplyMsg() {
        return replyMsg;
    }

    public void setReplyMsg(ReplyMsg replyMsg) {
        this.replyMsg = replyMsg;
    }
}
