package com.basiliskSB.viewModel.abstraction;
import lombok.Getter;
import org.springframework.ui.Model;

import java.util.List;

@Getter
public abstract class IndexViewModel {
    private List<Object> grid;
    private Integer page;
    private Integer totalPages;
    private String breadCrumbs;
    private String[] tableHeaders;
    private Integer columnSpan;
    private String actionUrl;

    public IndexViewModel() {}

    public IndexViewModel setGrid(List<Object> grid){
        this.grid = grid;
        return this;
    }
    public IndexViewModel setPage(Integer page){
        this.page = page;
        return this;
    }
    public IndexViewModel setTotalPages(Integer totalPages){
        this.totalPages = totalPages;
        return this;
    }
    public IndexViewModel setBreadCrumbs(String breadCrumbs){
        this.breadCrumbs = breadCrumbs;
        return this;
    }
    public IndexViewModel setTableHeaders(String[] tableHeaders){
        this.tableHeaders = tableHeaders;
        this.columnSpan = tableHeaders.length + 1;
        return this;
    }
    public IndexViewModel setActionUrl(String actionUrl){
        this.actionUrl = actionUrl;
        return this;
    }
    public void build(Model model, String urlParameters){
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("tableHeaders", tableHeaders);
        model.addAttribute("columnSpan", columnSpan);
        model.addAttribute("pageActionUrl", actionUrl);
        model.addAttribute("pageUrlParameters", urlParameters);
    }

    public void build(Model model){
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("tableHeaders", tableHeaders);
        model.addAttribute("columnSpan", columnSpan);
        model.addAttribute("pageActionUrl", actionUrl);
    };
}
