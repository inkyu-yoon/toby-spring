package com.practice.toby.ch6.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

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

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("say*");
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut,new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("inkyu")).isEqualTo("HELLO INKYU");
        assertThat(proxiedHello.sayHi("inkyu")).isEqualTo("HI INKYU");
        assertThat(proxiedHello.sayThankyou("inkyu")).isEqualTo("THANK YOU INKYU");
    }

    static class UppercaseAdvice implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }

    }

}