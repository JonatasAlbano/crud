package com.joalbano.crud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.joalbano.crud.dto.ProductDTO;

import lombok.Data;

@Entity
@Table(name="product")
@Data
public class Product implements Serializable {
	
	private static final long serialVersionUID = -6416781024804996456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(nullable = false, length = 10)
	private Integer stock;
	
	@Column(nullable = false, length = 10)
	private Double price;
	
	public static Product toProduct(ProductDTO productDTO) {
		return new ModelMapper().map(productDTO, Product.class);
	}
}
