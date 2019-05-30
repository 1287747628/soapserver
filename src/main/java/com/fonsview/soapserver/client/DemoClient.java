package com.fonsview.soapserver.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.w3c.dom.Document;

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

public class DemoClient {

    public static void main(String[] args) {
        testTwo();
//        testOne();
    }

    public static void testOne() {
        try {
            //动态调用的方式需要在server的接口注解加上targetNamespace，不然会报错
//            JaxWsDynamicClie朱姐ntFactory dcf = JaxWsDynamicClientFactory.newInstance();
//            Client client = dcf.createClient("http://localhost:8080/demo/api?wsdl");
//            Object[] objects = client.invoke("receiveTask", "send");
//            System.out.println("result >>> " + objects[0].toString());

            //代理类调用方式
//            String address = "http://localhost:8080/demo/api";
//            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//            factory.setAddress(address);
//            factory.setServiceClass(DemoService.class);
//            DemoService demoService = (DemoService) factory.create();
//            String result = demoService.receiveTask("send->");
//            System.out.println("result >>> " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTwo() {
        SOAPConnection connection = null;
        String sendMsgURL = "http://172.16.17.151:8081/HNOTTCIP/services/ExecCmdforYsgj";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("CSPID", "mango");
        paramMap.put("LSPID", "lsp_mango");
        paramMap.put("CorrelateID", "correlate_mango");
        paramMap.put("CmdFileURL", "cmd_mango_20190315.xml");
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
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ExecCmd", "iptv", "iptv"));
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
                    System.out.println(">>> send success.");
                } else {
                    System.out.println(">>> send error.");
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

    public static URL getUrl(String sendMsg2CSPURL) throws MalformedURLException {
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
