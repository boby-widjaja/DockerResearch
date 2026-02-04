package com.basiliskSB.viewModel.abstraction;


import org.springframework.ui.Model;

public abstract class UpsertViewModel {
    private String type;
    private Object dto;
    private String breadCrumbs;

    public UpsertViewModel() {}

    public UpsertViewModel setType(String type){
        this.type = type;
        return this;
    }

    public UpsertViewModel setDto(Object dto){
        this.dto = dto;
        return this;
    }

    public UpsertViewModel setBreadCrumbs(String breadCrumbs){
        this.breadCrumbs = breadCrumbs;
        return this;
    }

    public void build(Model model){
        model.addAttribute("dto", dto);
        model.addAttribute("type", type);
        model.addAttribute("breadCrumbs", breadCrumbs);
    };
}
