package com.basiliskSB.factory.abstraction;

import org.springframework.ui.Model;

public interface DeleteAssociationFactory extends SingleFactory{
    public void setPairId(Object id);
    public Object getPairId();
    public Boolean processDelete(Model model);
}
