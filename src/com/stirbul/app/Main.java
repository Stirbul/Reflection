package com.stirbul.app;

public class Main {
    public static void main(String[] args) {
        LoadClass scanner = new LoadClass();
        scanner.init();

//        Class<TestClass1> obj = TestClass1.class;
//        System.out.println(obj.isAnnotationPresent(Managed.class));

        System.out.println(Main.class.isAnnotationPresent(Managed.class));
    }
}
