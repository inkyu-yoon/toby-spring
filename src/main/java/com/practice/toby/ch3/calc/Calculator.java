package com.practice.toby.ch3.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) {
        return calc(filePath, new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                int result = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += Integer.parseInt(line);
                }
                return result;
            }
        });
    }

    public Integer calcMultiply(String filePath) {
        return calc(filePath, new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                int result = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    result *= Integer.parseInt(line);
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
