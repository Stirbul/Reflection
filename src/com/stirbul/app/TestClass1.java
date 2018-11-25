package com.stirbul.app;

@Managed
public class TestClass1 {

    public TestClass1(){
        System.out.println("TestClass1 is created");
    }

    @DefaultValue(name = "Andrew")
    String name = "Izabella";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
