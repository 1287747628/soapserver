package com.fonsview.soapserver.vo;

import java.lang.reflect.Field;

public class MiguReplyMsg extends ReplyMsg {

    @FieldAlias
    private String CSPID;
    private String LSPID;
    private String CmdResult;
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

    public String getCmdResult() {
        return CmdResult;
    }

    public void setCmdResult(String cmdResult) {
        CmdResult = cmdResult;
    }

    public String getResultFileURL() {
        return ResultFileURL;
    }

    public void setResultFileURL(String resultFileURL) {
        ResultFileURL = resultFileURL;
    }

    public static void main(String[] args) {
        MiguReplyMsg replyMsg = new MiguReplyMsg();
        for (Class<?> cls = replyMsg.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(FieldAlias.class)) {
                    continue;
                }
                System.out.println(" >>> " + field.getName());
            }
        }
    }


}
