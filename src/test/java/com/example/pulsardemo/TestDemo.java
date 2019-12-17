package com.example.pulsardemo;

import org.junit.Test;

public class TestDemo {
    @Test
    public void compare() {
        String oldStr = "";
        String newStr = null;

        if (oldStr.equals(newStr)) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }
}
