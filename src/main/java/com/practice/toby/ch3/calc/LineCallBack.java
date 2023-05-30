package com.practice.toby.ch3.calc;

public interface LineCallBack<T> {
    T doSomethingWithLine(String line, T value);
}
