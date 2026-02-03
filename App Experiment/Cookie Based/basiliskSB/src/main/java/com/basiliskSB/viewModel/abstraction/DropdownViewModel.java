package com.basiliskSB.viewModel.abstraction;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.utility.MapperHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface DropdownViewModel {
    static List<DropdownDTO> getDropdownDTO(List<Object> dropdownData){
        Stream<Object> dropdownStream = dropdownData.stream();
        var dropdown = dropdownStream.map(row -> {
            return new DropdownDTO(
                MapperHelper.getFieldValue(row, 0),
                MapperHelper.getStringField(row, 1)
            );
        }).collect(Collectors.toList());
        return dropdown;
    }
}
