package com.fonsview.soapserver.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ContentDispMngReq", namespace = "iptv")
@XmlAccessorType(XmlAccessType.FIELD)
public class CEReqMsg {

    @XmlElement
    private String COPID;

    @XmlElement
    private String SOPID;

    @XmlElement
    private String CorrelateID;

    @XmlElement
    private String ContentMngXMLURL;

    public String getCOPID() {
        return COPID;
    }

    public void setCOPID(String COPID) {
        this.COPID = COPID;
    }

    public String getSOPID() {
        return SOPID;
    }

    public void setSOPID(String SOPID) {
        this.SOPID = SOPID;
    }

    public String getCorrelateID() {
        return CorrelateID;
    }

    public void setCorrelateID(String correlateID) {
        CorrelateID = correlateID;
    }

    public String getContentMngXMLURL() {
        return ContentMngXMLURL;
    }

    public void setContentMngXMLURL(String contentMngXMLURL) {
        ContentMngXMLURL = contentMngXMLURL;
    }
}
