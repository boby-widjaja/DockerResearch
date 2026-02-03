package com.basiliskSB.service.abstraction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService {
    public <T> Page<Object> getGrid(Integer page, T filter);
    public Object getUpdate(Object id);
    public Object save(Object dto);
    public void delete(Object id);
}
