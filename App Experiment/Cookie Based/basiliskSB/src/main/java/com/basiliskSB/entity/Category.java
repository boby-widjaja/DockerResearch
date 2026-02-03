package com.basiliskSB.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name="Categories")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;

	@OneToMany(mappedBy="category", fetch = FetchType.LAZY)
	private List<Product> products;

	public Category(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
