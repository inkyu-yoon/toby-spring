package com.practice.toby.ch6.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTargetTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("inkyu")).isEqualTo("Hello inkyu");
        assertThat(hello.sayHi("inkyu")).isEqualTo("Hi inkyu");
        assertThat(hello.sayThankyou("inkyu")).isEqualTo("Thank you inkyu");
    }

    @Test
    public void upperProxy() {
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget()));

        assertThat(hello.sayHello("inkyu")).isEqualTo("HELLO INKYU");
        assertThat(hello.sayHi("inkyu")).isEqualTo("HI INKYU");
        assertThat(hello.sayThankyou("inkyu")).isEqualTo("THANK YOU INKYU");
    }


}