package com.fonsview.soapserver.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExecCmd", namespace = "iptv")
@XmlAccessorType(XmlAccessType.FIELD)
public class CMSReqMsg {

    @XmlElement
    private String CSPID;
    @XmlElement
    private String CorrelateID;
    @XmlElement
    private String LSPID;
    @XmlElement
    private String CmdFileURL;

    public String getCSPID() {
        return CSPID;
    }

    public void setCSPID(String CSPID) {
        this.CSPID = CSPID;
    }

    public String getCorrelateID() {
        return CorrelateID;
    }

    public void setCorrelateID(String correlateID) {
        CorrelateID = correlateID;
    }

    public String getLSPID() {
        return LSPID;
    }

    public void setLSPID(String LSPID) {
        this.LSPID = LSPID;
    }

    public String getCmdFileURL() {
        return CmdFileURL;
    }

    public void setCmdFileURL(String cmdFileURL) {
        CmdFileURL = cmdFileURL;
    }
}
