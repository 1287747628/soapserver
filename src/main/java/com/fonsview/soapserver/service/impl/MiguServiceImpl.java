package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.service.MiguService;
import com.fonsview.soapserver.vo.ResultVO;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "MiguService", targetNamespace = "iptv",
        endpointInterface = "com.fonsview.soapserver.service.MiguService")
@Component
public class MiguServiceImpl implements MiguService {

    @Override
    public ResultVO ExecCmd(String CSPID, String LSPID, String CorrelateID, String CmdFileURL) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append("CSPID:").append(CSPID);
        sb.append("; LSPID:").append(LSPID);
        sb.append("; CorrelateID:").append(CorrelateID);
        sb.append("; CmdFileURL:").append(CmdFileURL);
        sb.append(" ]");
        System.out.println("MIGU receive >>> " + sb.toString());
        ResultVO response = new ResultVO();
        response.setResultCode(0);
        response.setDescription("success");
        return response;
    }
}
