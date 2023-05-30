package com.practice.toby.ch3.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public String concatTexts(String filePath) {
        LineCallBack<String> concatOperation = new LineCallBack<String>() {
            @Override
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        };
        return lineReadTemplate(filePath, concatOperation, "");
    }

    public Integer calcSum(String filePath) {
        LineCallBack<Integer> sumOperation = new LineCallBack<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) + value;
            }
        };


        return lineReadTemplate(filePath, sumOperation, 0);
    }

    public Integer calcMultiply(String filePath) {
        LineCallBack<Integer> multiplyOperation = new LineCallBack<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) * value;
            }
        };

        return lineReadTemplate(filePath, multiplyOperation, 1);
    }

    public <T> T lineReadTemplate(String filePath, LineCallBack<T> callBack, T initVal) {
        BufferedReaderCallback<T> bufferedReaderCallback = new BufferedReaderCallback() {
            @Override
            public T doSomethingWithReader(BufferedReader br) throws IOException {
                T result = initVal;
                String line = null;
                while ((line = br.readLine()) != null) {
                    result = callBack.doSomethingWithLine(line, result);
                }
                return result;
            }
        };
        return calc(filePath, bufferedReaderCallback);
    }

    public <T> T calc(String filePath, BufferedReaderCallback<T> callback) {
        BufferedReader br = null;
        T result = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            result = callback.doSomethingWithReader(br);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
