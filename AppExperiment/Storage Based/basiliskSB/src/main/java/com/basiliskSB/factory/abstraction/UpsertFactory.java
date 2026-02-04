package com.basiliskSB.factory.abstraction;

import org.springframework.ui.Model;

public interface UpsertFactory extends SingleFactory{
    public void processUpsertForm(Model model);
    public void processUpsertForm(Model model, Object dto);
    public void save(Object dto);
}
