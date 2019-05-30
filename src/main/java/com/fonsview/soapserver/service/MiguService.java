package com.fonsview.soapserver.service;

import com.fonsview.soapserver.vo.ResultVO;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "iptv")
public interface MiguService {

    @WebResult
    ResultVO ExecCmd(@WebParam(name = "CSPID") String CSPID,
                     @WebParam(name = "LSPID") String LSPID,
                     @WebParam(name = "CorrelateID") String CorrelateID,
                     @WebParam(name = "CmdFileURL") String CmdFileURL);
}
