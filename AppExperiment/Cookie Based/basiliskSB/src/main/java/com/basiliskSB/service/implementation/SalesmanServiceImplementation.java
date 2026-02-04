package com.basiliskSB.service.implementation;
import java.util.*;

import com.basiliskSB.entity.Region;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dao.RegionRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.entity.Salesman;
import org.springframework.transaction.annotation.Transactional;

@Scope("singleton")
@Service("salesmanService")
public class SalesmanServiceImplementation implements CrudService, SalesmanService {

	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var employeeNumber = MapperHelper.getStringField(filter, "employeeNumber");
		var name = MapperHelper.getStringField(filter, "name");
		var employeeLevel = MapperHelper.getStringField(filter, "employeeLevel");
		var superiorName = MapperHelper.getStringField(filter,"superiorName");
		return salesmanRepository.findAll(employeeNumber, name, employeeLevel, superiorName, pagination);
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = salesmanRepository.findById(id.toString());
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var employeeNumber = MapperHelper.getStringField(dto, "employeeNumber");
		var superiorEmployeeNumber = MapperHelper.getStringField(dto, "superiorEmployeeNumber");
		List<Region> regions = null;
		if(employeeNumber != null){
			var employeeOptions = salesmanRepository.findById(employeeNumber);
			if(employeeOptions.isPresent()){
				regions = employeeOptions.get().getRegions();
			}
		}
		var entity = new Salesman(
			MapperHelper.getStringField(dto, "employeeNumber"),
			MapperHelper.getStringField(dto, "firstName"),
			MapperHelper.getStringField(dto, "lastName"),
			MapperHelper.getStringField(dto, "level"),
			MapperHelper.getLocalDateField(dto, "birthDate"),
			MapperHelper.getLocalDateField(dto, "hiredDate"),
			MapperHelper.getStringField(dto, "address"),
			MapperHelper.getStringField(dto, "city"),
			MapperHelper.getStringField(dto, "phone"),
			superiorEmployeeNumber,
			regions
		);
		return salesmanRepository.save(entity);
	}

	@Override
	public void delete(Object id) {
		salesmanRepository.deleteById(id.toString());
	}

	@Override
	public Long dependentOrders(String employeeNumber) {
		return orderRepository.countByEmployeeNumber(employeeNumber);
	}
	
	@Override
	public Long dependentSubordinates(String superiorEmployeeNumber) {
		return salesmanRepository.countBySuperiorEmployeeNumber(superiorEmployeeNumber);
	}
	
	@Override
	public Object getSalesmanHeader(String employeeNumber) {
		var entity = salesmanRepository.findById(employeeNumber);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Page<Object> getRegionGridBySalesman(String employeeNumber, Integer page, String city) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		return regionRepository.findAll(employeeNumber, city, pagination);
	}

	@Override
	public Boolean checkExistingSalesman(String employeeNumber) {	
		var totalExistingSalesman = salesmanRepository.count(employeeNumber);
		return (totalExistingSalesman > 0);
	}

	@Override
	public Object assignRegion(Object dto) {
		var salesmanEmployeeNumber = MapperHelper.getStringField(dto, "salesmanEmployeeNumber");
		var regionId = MapperHelper.getLongField(dto, "regionId");
		var salesman = salesmanRepository.findById(salesmanEmployeeNumber);
		var region = regionRepository.findById(regionId);
		if(salesman.isPresent() && region.isPresent()){
			salesman.get().getRegions().add(region.get());
			return salesmanRepository.save(salesman.get());
		}
		return null;
	}

	@Override
	public List<Object> getSuperiorDropdown(String employeeNumber) {
		return salesmanRepository.findAllSuperior(employeeNumber);
	}

	@Override
	public List<Object> getRegionDropdown() {
		return regionRepository.findAllOrderByCity();
	}

	@Override
	public List<Object> getRegionDropdown(String employeeNumber) {
		return regionRepository.findAllOrderByCity(employeeNumber);
	}

	@Override
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
		var totalExistingRegionSalesman = salesmanRepository.count(regionId, employeeNumber);
		return (totalExistingRegionSalesman > 0);
	}

	@Transactional
	@Override
	public void detachRegionSalesman(Long regionId, String employeeNumber) {
		var nullableSalesman = salesmanRepository.findById(employeeNumber);
		var salesman = nullableSalesman.get();
		var nullableRegion = regionRepository.findById(regionId);
		var region = nullableRegion.get();
		region.getSalesmen().remove(salesman);
		regionRepository.save(region);
	}
}
