package com.basiliskSB.service.abstraction;
import java.util.List;
import org.springframework.data.domain.Page;

public interface RegionService {
	public Object getRegionHeader(Object id);
	public Page<Object> getSalesmanGridByRegion(Long id, Integer page, String employeeNumber, String name, String employeeLevel, String superiorName);
	public Object assignSalesman(Object dto);
	public Boolean detachRegionSalesman(Long regionId, String employeeNumber);
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber);
	public List<Object> getSalesmanDropdown(Long regionId);
	public List<Object> getSalesmanDropdown();
}
