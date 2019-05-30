package com.fonsview.soapserver.vo;

import com.fonsview.soapserver.util.ReflectUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ContentDispMngResponse",namespace = "iptv")
public class CeResultVO {

    private Integer Result;

    private String ErrorDescription;

    @XmlElement(name = "Result")
    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer result) {
        Result = result;
    }

    @XmlElement(name = "ErrorDescription")
    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }

    @Override
    public String toString() {
        try{
            return ReflectUtil.toString(this);
        }catch(Exception ex){
            return super.toString();
        }
    }
}
