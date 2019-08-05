package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.constants.ProKey;
import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.CMSReplyMsg;
import com.fonsview.soapserver.vo.CMSReqMsg;
import com.fonsview.soapserver.vo.CMSRespMsg;
import com.fonsview.soapserver.vo.ReplyTask;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;

@Endpoint
public class CmsService {

    @Resource
    private ReplyNotifyService replyNotifyService;
    @Resource
    private ProConfigServiceImpl proConfigService;

    @PayloadRoot(namespace = "iptv", localPart = "ExecCmd")
    @ResponsePayload
    public CMSRespMsg ExecCmd(@RequestPayload CMSReqMsg req) {
        System.out.println("CMS receive >> CSPID:" + req.getCSPID() + ", LSPID:" + req.getLSPID() + ", CorrelateID:" + req.getCorrelateID() + ", CmdFileURL:" + req.getCmdFileURL());

        CMSRespMsg resp = new CMSRespMsg();
        resp.setResult(0);
        resp.setErrorDescription("success");

        ReplyTask replyTask = new ReplyTask();
        replyTask.setReplyType(ReplyTask.DIST_CMS);
        String replyUrl = proConfigService.getString(ProKey.CMS_REPLY_URL, "http://172.16.16.25:6070/replyMessageCMS");
        replyTask.setReplyUrl(replyUrl);
        replyTask.setReplyMsg(new CMSReplyMsg());

        CMSReplyMsg replyMsg = (CMSReplyMsg) replyTask.getReplyMsg();
        replyMsg.setCorrelateID(req.getCorrelateID());
        replyMsg.setCSPID(req.getCSPID());
        replyMsg.setLSPID(req.getLSPID());
        replyMsg.setCmdResult(0);
        replyMsg.setResultFileURL("");
        replyNotifyService.addReplyTask(replyTask);

        return resp;
    }

}
