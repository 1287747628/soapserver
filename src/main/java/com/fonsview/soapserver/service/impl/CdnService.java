package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.constants.ProKey;
import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.CDReplyMsg;
import com.fonsview.soapserver.vo.CDReqMsg;
import com.fonsview.soapserver.vo.CDRespMsg;
import com.fonsview.soapserver.vo.ReplyTask;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;

@Endpoint
public class CdnService {

    @Resource
    private ReplyNotifyService replyNotifyService;
    @Resource
    private ProConfigServiceImpl proConfigService;

    @PayloadRoot(namespace = "iptv", localPart = "ContentDeployReq")
    @ResponsePayload
    public CDRespMsg ContentDeployReq(@RequestPayload CDReqMsg req) {
        System.out.println("CD receive >> CMSID:" + req.getCMSID() + ", SOPID:" + req.getSOPID() + ", CorrelateID:" + req.getCorrelateID() + ", ContentMngXMLURL:" + req.getContentMngXMLURL());

        CDRespMsg resp = new CDRespMsg();
        resp.setResultCode(0);
        resp.setErrorDescription("success");

        ReplyTask replyTask = new ReplyTask();
        replyTask.setReplyType(ReplyTask.DIST_CD);
        String replyUrl = proConfigService.getString(ProKey.CD_REPLY_URL, "http://172.16.16.25:8080/cms/ws/ContentDeployResult");
        replyTask.setReplyUrl(replyUrl);
        replyTask.setReplyMsg(new CDReplyMsg());

        CDReplyMsg replyMsg = (CDReplyMsg) replyTask.getReplyMsg();
        replyMsg.setCorrelateID(req.getCorrelateID());
        replyMsg.setCMSID(req.getCMSID());
        replyMsg.setSOPID(req.getSOPID());
        replyMsg.setResultCode("0");
        replyMsg.setErrorDescription("success");
        replyMsg.setResultFileURL("");
        replyNotifyService.addReplyTask(replyTask);

        return resp;
    }

}
