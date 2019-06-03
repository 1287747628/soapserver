package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.constants.ProKey;
import com.fonsview.soapserver.service.ProConfigService;
import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.CEReplyMsg;
import com.fonsview.soapserver.vo.CEReqMsg;
import com.fonsview.soapserver.vo.CERespMsg;
import com.fonsview.soapserver.vo.ReplyTask;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;

@Endpoint
public class EpgCeService {

    @Resource
    private ReplyNotifyService replyNotifyService;
    @Resource
    private ProConfigService proConfigService;

    @PayloadRoot(namespace = "iptv", localPart = "ContentDispMngReq")
    @ResponsePayload
    public CERespMsg ContentDispMngReq(@RequestPayload CEReqMsg req) {
        System.out.println(">>>CE receive COPID:" + req.getCOPID() + ", SOPID:" + req.getSOPID() + ", CorrelateID:" + req.getCorrelateID() + ", ContentMngXMLURL:" + req.getContentMngXMLURL());

        CERespMsg resp = new CERespMsg();
        resp.setResult(0);
        resp.setErrorDescription("success");

        ReplyTask replyTask = new ReplyTask();
        replyTask.setReplyType(ReplyTask.DIST_CE);
        String replyUrl = proConfigService.getString(ProKey.CE_REPLY_URL, "http://172.16.16.25:8080/cms/ws/EpgCeResult");
        replyTask.setReplyUrl(replyUrl);
        replyTask.setReplyMsg(new CEReplyMsg());

        CEReplyMsg replyMsg = (CEReplyMsg) replyTask.getReplyMsg();
        replyMsg.setCorrelateID(req.getCorrelateID());
        replyMsg.setCOPID(req.getCOPID());
        replyMsg.setSOPID(req.getSOPID());
        replyMsg.setResultCode("0");
        replyMsg.setErrorDescription("success");
        replyMsg.setResultFileURL("");
        replyNotifyService.addReplyTask(replyTask);

        return resp;
    }

}
