package com.basiliskSB.dao;
import com.basiliskSB.entity.OrderDetailId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.OrderDetail;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

	@Query("""
			SELECT ordet.invoiceNumber, ordet.productId, prod.name, ordet.unitPrice, ordet.quantity, ordet.discount 
			FROM OrderDetail AS ordet
				INNER JOIN ordet.product AS prod
			WHERE ordet.invoiceNumber = :invoiceNumber""")
	public Page<Object> findAll(@Param("invoiceNumber") String invoiceNumber, Pageable pageable);
	
	@Query("""
			SELECT COUNT(*) 
			FROM OrderDetail AS ordet
			WHERE ordet.productId = :productId""")
	public Long countByProductId(@Param("productId") Long productId);

	@Query("""
			SELECT ordet.invoiceNumber, ordet.productId, prod.name, ordet.unitPrice, ordet.quantity, ordet.discount 
			FROM OrderDetail AS ordet
				INNER JOIN ordet.product AS prod
			WHERE ordet.invoiceNumber = :invoiceNumber""")
	public List<Object> getInvoiceDetail(@Param("invoiceNumber") String invoiceNumber);
}
