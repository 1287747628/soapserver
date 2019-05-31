package com.fonsview.soapserver.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ContentDispMngResponse",namespace="iptv")
@XmlAccessorType(XmlAccessType.FIELD)
public class CERespMsg {

    @XmlElement
    private int Result;

    @XmlElement
    private String ErrorDescription;

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
