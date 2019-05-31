package com.fonsview.soapserver.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ContentDeployReq", namespace = "iptv")
@XmlAccessorType(XmlAccessType.FIELD)
public class CDReqMsg {

    @XmlElement
    private String CMSID;
    @XmlElement
    private String SOPID;
    @XmlElement
    private String CorrelateID;
    @XmlElement
    private String ContentMngXMLURL;

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
