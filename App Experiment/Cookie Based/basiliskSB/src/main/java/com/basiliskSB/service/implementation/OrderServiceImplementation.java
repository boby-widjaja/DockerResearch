package com.basiliskSB.service.implementation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.basiliskSB.dto.order.InvoiceDetailDTO;
import com.basiliskSB.entity.Order;
import com.basiliskSB.entity.OrderDetail;
import com.basiliskSB.entity.OrderDetailId;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.*;

@Scope("singleton")
@Service("orderService")
public class OrderServiceImplementation implements CrudService, CrudDetailService, OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by(Sort.Direction.DESC,"orderDate", "invoiceNumber"));
		var invoiceNumber = MapperHelper.getStringField(filter, "invoiceNumber");
		var customerId = MapperHelper.getLongField(filter, "customerId");
		var employeeNumber = MapperHelper.getStringField(filter, "employeeNumber");
		var deliveryId = MapperHelper.getLongField(filter, "deliveryId");
		var orderDate = MapperHelper.getLocalDateField(filter, "orderDate");
		return orderRepository.findAll(invoiceNumber, customerId, employeeNumber, deliveryId, orderDate, pagination);
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = orderRepository.findById(id.toString());
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var deliveryId = MapperHelper.getLongField(dto, "deliveryId");
		var delivery = deliveryRepository.findById(deliveryId).get();
		var deliveryCost = delivery.getCost();
		var invoiceNumber = MapperHelper.getStringField(dto, "invoiceNumber");
		var orderDate = MapperHelper.getLocalDateField(dto, "orderDate");
		var isInvoiceNumberExist = (orderRepository.countByInvoiceNumber(invoiceNumber) > 0 ) ? true : false;
		invoiceNumber = (!isInvoiceNumberExist) ? getInvoiceNumber(orderDate) : invoiceNumber;
		var entity = new Order(
				invoiceNumber,
				MapperHelper.getLongField(dto, "customerId"),
				MapperHelper.getStringField(dto, "salesEmployeeNumber"),
				orderDate,
				MapperHelper.getLocalDateField(dto, "shippedDate"),
				MapperHelper.getLocalDateField(dto, "dueDate"),
				MapperHelper.getLongField(dto, "deliveryId"),
				deliveryCost,
				MapperHelper.getStringField(dto, "destinationAddress"),
				MapperHelper.getStringField(dto, "destinationCity"),
				MapperHelper.getStringField(dto, "destinationPostalCode")
			);
		return orderRepository.save(entity);
	}

	private String getInvoiceNumber(LocalDate orderDate){
		var formatter = DateTimeFormatter.ofPattern("MM-yy");
		var segment = formatter.format(orderDate);
		var nextRollingNumber = orderRepository.countInvoiceNumberByPeriod(segment) + 1;
		return String.format("%s-%04d", segment, nextRollingNumber);
	}

	@Override
	public void delete(Object id) {
		orderRepository.deleteById(id.toString());
	}

	@Override
	public <T> Page<Object> getGridDetail(Object headerId, Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("productId"));
		return orderDetailRepository.findAll(headerId.toString(), pagination);
	}

	@Override
	public Object getUpdateDetail(Object id) {
		var invoiceNumber = MapperHelper.getStringField(id, "invoiceNumber");
		var productId = MapperHelper.getLongField(id, "productId");
		var compositeId = new OrderDetailId(invoiceNumber, productId);
		var entity = orderDetailRepository.findById(compositeId);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public <T> Object saveDetail(Object dto) {
		var productId = MapperHelper.getLongField(dto, "productId");
		var productEntity = productRepository.findById(productId).get();
		var unitPrice = productEntity.getPrice();
		var orderDetailEntity = new OrderDetail(
			MapperHelper.getStringField(dto, "invoiceNumber"),
			MapperHelper.getLongField(dto, "productId"),
			unitPrice,
			MapperHelper.getIntegerField(dto, "quantity"),
			MapperHelper.getDoubleField(dto, "discount")
		);
		return orderDetailRepository.save(orderDetailEntity);
	}

	@Override
	public void deleteDetail(Object id) {
		var invoiceNumber = MapperHelper.getStringField(id, "invoiceNumber");
		var productId = MapperHelper.getLongField(id, "productId");
		var compositeId = new OrderDetailId(invoiceNumber, productId);
		orderDetailRepository.deleteById(compositeId);
	}

	@Override
	public List<Object> getCustomerDropdown() {
		return customerRepository.findAllOrderByCompanyName();
	}

	@Override
	public List<Object> getSalesmanDropdown() {
		return salesmanRepository.findAllOrderByFirstName();
	}

	@Override
	public List<Object> getDeliveryDropdown() {
		return deliveryRepository.findAllOrderByCompanyName();
	}

	@Override
	public List<Object> getProductDropdown(String invoiceNumber) {
		return productRepository.findAllOrderByName(invoiceNumber);
	}

	@Override
	public Object getCustomer(Long customerId) {
		return customerRepository.findById(customerId).get();
	}

	@Override
	public Object getInvoice(String invoiceNumber) {
		@Getter @Setter @NoArgsConstructor @AllArgsConstructor
		class InvoiceDetail {
			private String productName;
			private Integer quantity;
			private Double unitPrice;
			private Double discount;
			private Double totalDiscount;
			private Double subTotal;
		}
		@Getter @Setter @AllArgsConstructor @NoArgsConstructor
		class Invoice {
			private String invoiceNumber;
			private LocalDate orderDate;
			private String customerCompany;
			private String deliveryCompany;
			private String deliveryCity;
			private String deliveryAddress;
			private String deliveryPostalCode;
			private String receivedBy;
			private Double deliveryCost;
			private Double totalCost;
			private List<InvoiceDetail> details;
		}

		var detailResults = orderDetailRepository.getInvoiceDetail(invoiceNumber);
		var invoiceDetails = new ArrayList<InvoiceDetail>();
		var totalCost = 0.0;
		for(var detailResult : detailResults){
			var quantity = MapperHelper.getIntegerField(detailResult, 4);
			var unitPrice = MapperHelper.getDoubleField(detailResult, 3);
			var discount = MapperHelper.getDoubleField(detailResult, 5);
			var totalDiscount = (quantity * unitPrice) * (discount / 100);
			var subTotal = (quantity * unitPrice) - totalDiscount;
			totalCost += subTotal;
			invoiceDetails.add(new InvoiceDetail(
				MapperHelper.getStringField(detailResult, 2), quantity, unitPrice, discount, totalDiscount, subTotal
			));
		}

		var orderEntity = orderRepository.findById(invoiceNumber).get();
		var customerId = MapperHelper.getLongField(orderEntity, "customerId");
		var customerEntity = customerRepository.findById(customerId).get();
		var deliveryId = MapperHelper.getLongField(orderEntity, "deliveryId");
		var deliveryEntity = deliveryRepository.findById(deliveryId).get();
		totalCost += deliveryEntity.getCost();

		return new Invoice(
			MapperHelper.getStringField(orderEntity, "invoiceNumber"),
			MapperHelper.getLocalDateField(orderEntity, "orderDate"),
			customerEntity.getCompanyName(),
			deliveryEntity.getCompanyName(),
			MapperHelper.getStringField(orderEntity, "destinationCity"),
			MapperHelper.getStringField(orderEntity, "destinationAddress"),
			MapperHelper.getStringField(orderEntity, "destinationPostalCode"),
			customerEntity.getContactPerson(),
			deliveryEntity.getCost(),
			totalCost,
			invoiceDetails
		);
	}

	@Override
	public String getProductName(Long productId) {
		return productRepository.findById(productId).get().getName();
	}

	@Override
	public Object getHeader(Object headerId) {
		var order = orderRepository.findById(headerId.toString()).get();
		var customerId = order.getCustomerId();
		var customer = customerRepository.findById(customerId).get();
		var salesmanEmployeeNumber = order.getSalesEmployeeNumber();
		var salesman = salesmanRepository.findById(salesmanEmployeeNumber).get();

		@AllArgsConstructor @Getter @Setter
		class Header{
			private String invoiceNumber;
			private String customer;
			private String salesman;
			private LocalDate orderDate;
		};

		return new Header(
			order.getInvoiceNumber(),
			customer.getCompanyName(),
			String.format("%s %s", salesman.getFirstName(), salesman.getLastName()),
			order.getOrderDate()
		);
	}

	@Override
	public Boolean checkExistingOrder(String invoiceNumber) {
		Long totalExistingOrder = orderRepository.countByInvoiceNumber(invoiceNumber);
		return (totalExistingOrder > 0);
	}

}
