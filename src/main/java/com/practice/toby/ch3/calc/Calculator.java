package com.practice.toby.ch3.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) {
        LineCallBack sumOperation = new LineCallBack() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) + value;
            }
        };

        return lineReadTemplate(filePath, sumOperation, 0);
    }

    public Integer calcMultiply(String filePath) {
        LineCallBack multiplyOperation = new LineCallBack() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) * value;
            }
        };

        return lineReadTemplate(filePath, multiplyOperation, 1);
    }

    public Integer lineReadTemplate(String filePath, LineCallBack callBack, int initVal) {
        return calc(filePath, new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                int result = initVal;
                String line = null;
                while ((line = br.readLine()) != null) {
                    result = callBack.doSomethingWithLine(line, result);
                }
                return result;
            }
        });
    }

    public Integer calc(String filePath, BufferedReaderCallback callback) {
        BufferedReader br = null;
        Integer result = null;
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
