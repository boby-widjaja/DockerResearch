package com.basiliskSB.dto.utility;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DropdownDTO {
    private Object value;
    private String text;

    public DropdownDTO(Object row){
        this.value = MapperHelper.getFieldValue(row, 0);
        this.text = MapperHelper.getStringField(row, 1);
    }
}
