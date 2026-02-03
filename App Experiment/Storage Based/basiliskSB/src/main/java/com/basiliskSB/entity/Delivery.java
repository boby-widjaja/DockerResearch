package com.basiliskSB.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Deliveries")
public class Delivery {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="CompanyName")
	private String companyName;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Cost")
	private Double cost;

	@OneToMany(mappedBy="delivery", fetch = FetchType.LAZY)
	private List<Order> orders;

	public Delivery(Long id, String companyName, String phone, Double cost) {
		this.id = id;
		this.companyName = companyName;
		this.phone = phone;
		this.cost = cost;
	}
}
