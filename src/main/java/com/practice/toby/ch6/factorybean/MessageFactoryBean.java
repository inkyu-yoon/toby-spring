package com.practice.toby.ch6.factorybean;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

@AllArgsConstructor
public class MessageFactoryBean implements FactoryBean<Message> {
    String text;

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(this.text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
