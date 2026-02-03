package com.basiliskSB.factory.abstraction;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.utility.RowHandler;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IndexFactory {

    static List<Object> getGridDTO(Page<Object> pageCollection, RowHandler handler){
        Stream<Object> gridStream = pageCollection.toList().stream();
        return gridStream.map(row -> {
            return handler.getRow(row);
        }).collect(Collectors.toList());
    }

    public void setPage(Integer page);
    public Integer getPage();

    public void setUrlParameters(String key, Object value);
    public Map<String, Object> getUrlParameters();

    public void processIndex(Model model);

}
