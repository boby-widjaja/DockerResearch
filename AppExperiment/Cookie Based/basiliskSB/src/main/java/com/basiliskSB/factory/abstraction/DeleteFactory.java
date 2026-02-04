package com.basiliskSB.factory.abstraction;

import org.springframework.ui.Model;

public interface DeleteFactory extends SingleFactory{
    public Boolean processDelete(Model model);
}
