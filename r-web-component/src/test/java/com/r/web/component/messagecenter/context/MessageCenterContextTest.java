package com.r.web.component.messagecenter.context;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring-messagecenter/*.xml", })
public class MessageCenterContextTest {

    @Resource(name = "messageCenterContext")
    private MessageCenterContext cmContext;

    @Test
    public void test() {
        cmContext.sendMessage("mc://htsms/send/15223152423/xx尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼您好！我司江北区观音桥地址是建新南路中信大厦6楼（爱尔眼科医院旁边），详询88266288，我们将竭诚为您服务。【博达信贷】尊敬的");
        // cmContext.sendMessage("mc://htsms/send/15223152423/尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼您好！我司江北区观音桥地址是建新南路中信大厦6楼（爱尔眼科医院旁边），详询88266288，我们将竭诚为您服务。【博达信贷】尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼");
        // cmContext.sendMessage("mc://htsms/batchsend/15223152423,13527360252,13640521550/3 - 尊敬的客户，您好！我司江北区观音桥地址是建新南路中信大厦6楼（爱尔眼科医院旁边），详询88266288，我们将竭诚为您服务。【博达信贷】");

        System.out.println("完成");
    }

}
