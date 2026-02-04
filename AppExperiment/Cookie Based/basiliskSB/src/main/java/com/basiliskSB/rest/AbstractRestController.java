package com.basiliskSB.rest;
import com.basiliskSB.dto.utility.ErrorDTO;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.utility.RowHandler;
import org.springframework.data.domain.Page;
import org.springframework.validation.ObjectError;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractRestController {

    public static List<Object> getGridDTO(Page<Object> pageCollection, RowHandler handler){
        Stream<Object> gridStream = pageCollection.toList().stream();
        return gridStream.map(handler::getRow).collect(Collectors.toList());
    }

    public static List<Object> getDropdownDTO(List<Object> collection, RowHandler handler){
        Stream<Object> optionStream = collection.stream();
        return optionStream.map(handler::getRow).collect(Collectors.toList());
    }

    public static List<ErrorDTO> getErrors(List<ObjectError> errors){
        var dto = new ArrayList<ErrorDTO>();
        for(var error : errors){
            var fieldName = MapperHelper.getStringField(error.getArguments()[0], "defaultMessage");
            fieldName = (fieldName.equals("")) ? "object" : fieldName;
            dto.add(new ErrorDTO(fieldName, error.getDefaultMessage()));
        }
        return dto;
    }
}
