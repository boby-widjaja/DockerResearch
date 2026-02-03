package com.basiliskSB.service.implementation;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.service.abstraction.CategoryService;
import com.basiliskSB.service.abstraction.CrudService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.*;
import com.basiliskSB.dao.*;
import com.basiliskSB.entity.Category;

@Scope("singleton")
@Service("categoryService")
public class CategoryServiceImplementation implements CrudService, CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id").descending());
		var name = MapperHelper.getStringField(filter, "name");
		return categoryRepository.findAll(name, pagination);
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = categoryRepository.findById((Long)id);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var entity = new Category(
				MapperHelper.getLongField(dto, "id"),
				MapperHelper.getStringField(dto, "name"),
				MapperHelper.getStringField(dto, "description")
		);
		return categoryRepository.save(entity);
	}

	@Override
	public void delete(Object id) {
		categoryRepository.deleteById((Long)id);
	}

	@Override
	public Long dependentProducts(Long id) {
		var totalDependentProducts = productRepository.countByCategoryId(id);
		return totalDependentProducts;
	}
	
	@Override
	public Boolean checkExistingCategoryName(Long id, String name) {
		id = (id == null) ? 0l : id;
		var totalData = categoryRepository.count(id, name);
		return (totalData > 0);
	}
}
