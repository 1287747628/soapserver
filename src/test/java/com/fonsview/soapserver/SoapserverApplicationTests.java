package com.fonsview.soapserver;

import com.fonsview.soapserver.service.ReplyNotifyService;
import com.fonsview.soapserver.vo.ReplyTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SoapserverApplicationTests {

    @Autowired
    private ReplyNotifyService replyNotifyService;

    @Test
    public void contextLoads() throws Exception{
        ReplyTask task = new ReplyTask();
        task.setReplyType(ReplyTask.DIST_CDN);
        task.setCspID("mango");
        task.setLspID("mango");
        task.setCorrelateID("123456");
        task.setCmdResult("0");
        task.setResultFileURL("ftp://");
        replyNotifyService.addReplyTask(task);
        Thread.sleep(20000);
    }

}
