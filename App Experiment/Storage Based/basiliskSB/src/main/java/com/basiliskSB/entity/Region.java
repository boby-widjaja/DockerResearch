package com.basiliskSB.entity;
import java.util.List;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Regions")
public class Region {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="City")
	private String city;
	
	@Column(name="Remark")
	private String remark;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="SalesmenRegions", 
		joinColumns=@JoinColumn(name="RegionId", insertable = false, updatable = false),
		inverseJoinColumns=@JoinColumn(name="SalesmanEmployeeNumber", insertable = false, updatable = false))
	private List<Salesman> salesmen;

	public Region(Long id, String city, String remark) {
		this.id = id;
		this.city = city;
		this.remark = remark;
	}
}
