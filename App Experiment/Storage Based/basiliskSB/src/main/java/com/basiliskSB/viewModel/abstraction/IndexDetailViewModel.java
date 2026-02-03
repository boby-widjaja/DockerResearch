package com.basiliskSB.viewModel.abstraction;

import org.springframework.ui.Model;

public abstract class IndexDetailViewModel extends IndexViewModel{

    private Object parentId;

    private Object parent;

    public IndexDetailViewModel setParent(Object parent) {
        this.parent = parent;
        return this;
    }
    public IndexDetailViewModel setParentId(Object parentId){
        this.parentId = parentId;
        return this;
    }
    public Object getParentId() {
        return parentId;
    }

    public Object getParent() {
        return parent;
    }

    @Override
    public void build(Model model, String urlParameters) {
        model.addAttribute("parent", parent);
        model.addAttribute("parentId", parentId);
        super.build(model, urlParameters);
    }

    @Override
    public void build(Model model) {
        model.addAttribute("parent", parent);
        super.build(model);
    }
}
