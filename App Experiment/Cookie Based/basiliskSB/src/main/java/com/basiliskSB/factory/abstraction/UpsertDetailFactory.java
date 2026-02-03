package com.basiliskSB.factory.abstraction;

public interface UpsertDetailFactory extends UpsertFactory{
    public void setParentId(Object parentId);
    public Object getParentId();
}
