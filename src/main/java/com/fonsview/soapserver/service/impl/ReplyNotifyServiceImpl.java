package com.fonsview.soapserver.service.impl;

import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.FieldAlias;
import com.fonsview.soapserver.vo.ReplyTask;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ReplyNotifyServiceImpl implements ReplyNotifyService {

    private LinkedBlockingQueue<ReplyTask> taskQueue;

    @PostConstruct
    private void init() {
        ReplyTaskThread taskThread = new ReplyTaskThread();
        taskThread.start();
    }

    @Override
    public void addReplyTask(ReplyTask task) {
        this.taskQueue.add(task);
    }

    private class ReplyTaskThread extends Thread {
        @Override
        public void run() {
            taskQueue = new LinkedBlockingQueue<>();
            while (true) {
                try {
                    ReplyTask task = taskQueue.take();
                    TaskHandleThread taskHandle = new TaskHandleThread(task);
                    taskHandle.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class TaskHandleThread extends Thread {
        private ReplyTask task;

        TaskHandleThread(ReplyTask task) {
            super();
            this.task = task;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(3) + 1);
                if (ReplyTask.DIST_MIGU.equals(task.getReplyType())) {
                    miguReply(task);
                } else if (ReplyTask.DIST_CE.equals(task.getReplyType())) {
                    ceReply(task);
                } else if (ReplyTask.DIST_CD.equals(task.getReplyType())) {
                    cdReply(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void miguReply(ReplyTask replyTask) {
        SOAPConnection connection = null;
        try {
            // 创建连接
            connection = SOAPConnectionFactory.newInstance().createConnection();
            // 创建消息对象
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            // 创建soap消息主体
            SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ResultNotify", "iptv", "iptv"));

            for (Class<?> cls = replyTask.getReplyMsg().getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(FieldAlias.class)) {
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), cls);
                        Method m = descriptor.getReadMethod();
                        Object oValue = m.invoke(replyTask.getReplyMsg());
                        bodyElement.addChildElement(field.getName()).addTextNode(String.valueOf(oValue == null ? "" : oValue));
                    }
                }
            }

            // Save the message
            message.saveChanges();
            // 创建服务地址
            URL url = getUrl(replyTask.getReplyUrl());
            // 响应消息
            SOAPMessage reply = connection.call(message, url);
            Source source = reply.getSOAPPart().getContent();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
            StreamResult res = new StreamResult();
            res.setOutputStream(myOutStr);
            transformer.transform(source, res);
            String result = myOutStr.toString("UTF-8");
            System.out.println(">>>migu result:" + result);

            Document doc = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
            String resultCode = doc.getElementsByTagName("result").item(0).getTextContent();
            if ("0".equals(resultCode)) {
                System.out.println(">>>migu task:" + replyTask.getReplyMsg().getCorrelateID() + " reply success.");
            } else {
                System.out.println(">>>migu task:" + replyTask.getReplyMsg().getCorrelateID() + " reply fail.");
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

    private void ceReply(ReplyTask replyTask) {
        SOAPConnection connection = null;
        try {
            // 创建连接
            connection = SOAPConnectionFactory.newInstance().createConnection();
            // 创建消息对象
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            // 创建soap消息主体
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ContentDispMngResult", "iptv", "iptv"));

            for (Class<?> cls = replyTask.getReplyMsg().getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(FieldAlias.class)) {
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), cls);
                        Method m = descriptor.getReadMethod();
                        Object oValue = m.invoke(replyTask.getReplyMsg());
                        bodyElement.addChildElement(field.getName()).addTextNode(String.valueOf(oValue == null ? "" : oValue));
                    }
                }
            }

            // Save the message
            message.saveChanges();
            // 创建服务地址
            URL url = getUrl(replyTask.getReplyUrl());
            // 响应消息
            SOAPMessage reply = connection.call(message, url);
            Source source = reply.getSOAPPart().getContent();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
            StreamResult res = new StreamResult();
            res.setOutputStream(myOutStr);
            transformer.transform(source, res);
            String result = myOutStr.toString("UTF-8");
            System.out.println(">>>ce result:" + result);

            Document doc = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
            String resultCode = doc.getElementsByTagName("Result").item(0).getTextContent();
            if ("0".equals(resultCode)) {
                System.out.println(">>>ce task:" + replyTask.getReplyMsg().getCorrelateID() + " reply success.");
            } else {
                System.out.println(">>>ce task:" + replyTask.getReplyMsg().getCorrelateID() + " reply fail.");
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

    private void cdReply(ReplyTask replyTask) {
        SOAPConnection connection = null;
        try {
            // 创建连接
            connection = SOAPConnectionFactory.newInstance().createConnection();
            // 创建消息对象
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            // 创建soap消息主体
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement(envelope.createName("ContentDeployResult", "iptv", "iptv"));

            for (Class<?> cls = replyTask.getReplyMsg().getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(FieldAlias.class)) {
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), cls);
                        Method m = descriptor.getReadMethod();
                        Object oValue = m.invoke(replyTask.getReplyMsg());
                        bodyElement.addChildElement(field.getName()).addTextNode(String.valueOf(oValue == null ? "" : oValue));
                    }
                }
            }

            // Save the message
            message.saveChanges();
            // 创建服务地址
            URL url = getUrl(replyTask.getReplyUrl());
            // 响应消息
            SOAPMessage reply = connection.call(message, url);
            Source source = reply.getSOAPPart().getContent();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
            StreamResult res = new StreamResult();
            res.setOutputStream(myOutStr);
            transformer.transform(source, res);
            String result = myOutStr.toString("UTF-8");
            System.out.println(">>>cd result:" + result);

            Document doc = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
            String resultCode = doc.getElementsByTagName("ResultCode").item(0).getTextContent();
            if ("0".equals(resultCode)) {
                System.out.println(">>>cd task:" + replyTask.getReplyMsg().getCorrelateID() + " reply success.");
            } else {
                System.out.println(">>>cd task:" + replyTask.getReplyMsg().getCorrelateID() + " reply fail.");
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
                connection.setConnectTimeout(10000); // 10 sec
                connection.setReadTimeout(10000); // 10 sec
                return (connection);
            }
        });
        return url;
    }

}