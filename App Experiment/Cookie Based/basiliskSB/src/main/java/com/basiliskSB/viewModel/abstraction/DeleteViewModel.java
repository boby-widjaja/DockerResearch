package com.basiliskSB.viewModel.abstraction;

import lombok.Getter;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class DeleteViewModel {
    public Map<String, Long> dependencies = new HashMap<>();
    public String breadCrumbs;

    public DeleteViewModel setDependencies(String key, Long total){
        this.dependencies.put(key, total);
        return this;
    }
    public DeleteViewModel setBreadCrumbs(String breadCrumbs){
        this.breadCrumbs = breadCrumbs;
        return this;
    }

    public abstract void build(Model model);
}
