package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.vo.CdnReqMsg;
import com.fonsview.soapserver.vo.CdnRespMsg;
import com.fonsview.soapserver.vo.EpgCeRequestMsg;
import com.fonsview.soapserver.vo.EpgCeResponseMsg;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CdnService {

    @PayloadRoot(namespace = "iptv", localPart = "ExecCmd")
    @ResponsePayload
    public CdnRespMsg ExecCmd(@RequestPayload CdnReqMsg request) {
        CdnRespMsg resp = new CdnRespMsg();
        resp.setResult(3);
        resp.setErrorDescription("success_3r");
        return resp;
    }
}
