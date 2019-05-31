package com.fonsview.soapserver.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ContentDeployReqResponse", namespace = "iptv")
@XmlAccessorType(XmlAccessType.FIELD)
public class CDRespMsg {

    @XmlElement
    private int ResultCode;
    @XmlElement
    private String ErrorDescription;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }
}
