package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.ReplyTask;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ReplyNotifyServiceImpl implements ReplyNotifyService {

    private LinkedBlockingQueue<ReplyTask> taskQueue;

    @PostConstruct
    private void init() {
        ReplyTaskThread taskThread = new ReplyTaskThread();
        taskThread.start();
    }

    private class ReplyTaskThread extends Thread {

        @Override
        public void run() {
            taskQueue = new LinkedBlockingQueue<>();
            //
            while (true) {
                try {
                    ReplyTask task = taskQueue.take();
                    Thread.sleep(1000);
                    if (ReplyTask.DIST_CDN.equals(task.getReplyType())) {
                        sendDistCdn(task);
                    } else if (ReplyTask.DIST_CE.equals(task.getReplyType())) {
                        sendDistCe(task);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addReplyTask(ReplyTask task) {
        this.taskQueue.add(task);
    }

    private void sendDistCdn(ReplyTask replyTask) {
        SOAPConnection connection = null;
        String sendMsgURL = replyTask.getReplyUrl();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("CSPID", replyTask.getCspID());
        paramMap.put("LSPID", replyTask.getLspID());
        paramMap.put("CorrelateID", replyTask.getCorrelateID());
        paramMap.put("CmdResult", replyTask.getCmdResult());
        paramMap.put("ResultFileURL", replyTask.getResultFileURL());
        try {
            // 创建连接
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            connection = soapConnFactory.createConnection();
            // 创建消息对象
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            // 创建soap消息主体
            SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ResultNotify", "iptv", "iptv"));
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                bodyElement.addChildElement(entry.getKey()).addTextNode(entry.getValue());
            }
            // Save the message
            message.saveChanges();
            // 打印客户端发出的soap报文
            URL url = getUrl(sendMsgURL);
            // 响应消息
            SOAPMessage reply = connection.call(message, url);
            Source source = reply.getSOAPPart().getContent();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
            StreamResult res = new StreamResult();
            res.setOutputStream(myOutStr);
            transformer.transform(source, res);
            String result = myOutStr.toString("UTF-8");
            System.out.println(">>> result:" + result);
            //webservice服务使用encoded类型，响应的soup报文解析出错
            try {
                Document doc = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
                String resultCode = doc.getElementsByTagName("result").item(0).getTextContent();
                if ("0".equals(resultCode)) {
                    System.out.println(">>> reply success.");
                } else {
                    System.out.println(">>> reply error.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendDistCe(ReplyTask replyTask) {
        SOAPConnection connection = null;
        String sendMsgURL = replyTask.getReplyUrl();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("COPID", replyTask.getCopId());
        paramMap.put("SOPID", replyTask.getSopId());
        paramMap.put("CorrelateID", replyTask.getCorrelateID());
        paramMap.put("ResultCode", replyTask.getResultCode());
        paramMap.put("ErrorDescription", replyTask.getErrorDescription());
        paramMap.put("ResultFileURL", "");
        try {
            // 创建连接
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            connection = soapConnFactory.createConnection();
            // 创建消息对象
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            // 创建soap消息主体
            SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ContentDispMngResult", "iptv", "iptv"));
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                bodyElement.addChildElement(entry.getKey()).addTextNode(entry.getValue());
            }
            // Save the message
            message.saveChanges();
            // 打印客户端发出的soap报文
            URL url = getUrl(sendMsgURL);
            // 响应消息
            SOAPMessage reply = connection.call(message, url);
            Source source = reply.getSOAPPart().getContent();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
            StreamResult res = new StreamResult();
            res.setOutputStream(myOutStr);
            transformer.transform(source, res);
            String result = myOutStr.toString("UTF-8");
            System.out.println(">>> result:" + result);
            //webservice服务使用encoded类型，响应的soup报文解析出错
            try {
                Document doc = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
                String resultCode = doc.getElementsByTagName("Result").item(0).getTextContent();
                if ("0".equals(resultCode)) {
                    System.out.println(">>> reply success.");
                } else {
                    System.out.println(">>> reply error.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private URL getUrl(String sendMsg2CSPURL) throws MalformedURLException {
        URL url = new URL(new URL(sendMsg2CSPURL), "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                URL target = new URL(url.toString());
                URLConnection connection = target.openConnection();
                // Connection settings
                connection.setConnectTimeout(20000); // 20 sec
                connection.setReadTimeout(20000); // 20sec
                return (connection);
            }
        });
        return url;
    }

}
