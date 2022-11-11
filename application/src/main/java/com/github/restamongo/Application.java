package com.github.restamongo;

public class Application {
    public static void main(String[] args) {
        System.out.println(new Application().getGreeting());
    }

    public String getGreeting() {
        return "Hello World!";
    }
}
