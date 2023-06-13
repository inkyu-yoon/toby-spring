package com.practice.toby.ch6.proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HelloUppercase implements Hello {
    private final Hello hello;

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankyou(String name) {
        return hello.sayThankyou(name).toUpperCase();
    }
}
