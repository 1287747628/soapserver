package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.vo.EpgCeRequestMsg;
import com.fonsview.soapserver.vo.EpgCeResponseMsg;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EpgCeService {

    @PayloadRoot(namespace = "iptv", localPart = "ContentDispMngReq")
    @ResponsePayload
    public EpgCeResponseMsg ContentDispMngReq(@RequestPayload EpgCeRequestMsg request) {
        EpgCeResponseMsg resp = new EpgCeResponseMsg();
        resp.setResult(2);
        resp.setErrorDescription("success_error");
        return resp;
    }
}
