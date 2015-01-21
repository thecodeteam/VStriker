package com.emccode.vstriker.model;

public class TestInfo {
    private String name;
    private long testId;
    private boolean isTemplate;
    

    @Override
    public String toString() {
        return name;
    }

    public TestInfo(String name, long  id,boolean template) {
        this.name = name;
        this.testId = id;
        this.isTemplate=template;
    }

    public String getName() {
        return name;
    }

    public long geTestID() {
        return testId;
    }

    public boolean getIsTemplate() {
        return this.isTemplate;
    }
}