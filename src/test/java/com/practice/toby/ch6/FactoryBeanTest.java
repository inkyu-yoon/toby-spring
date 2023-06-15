package com.practice.toby.ch6;

import com.practice.toby.ch6.factorybean.Message;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;


    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        Assertions.assertThat(message).isInstanceOf(Message.class);
        Assertions.assertThat(((Message) message).getText()).isEqualTo("Factory Bean");
    }
}
