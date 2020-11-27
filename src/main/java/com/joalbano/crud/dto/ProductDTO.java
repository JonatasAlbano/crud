package com.joalbano.crud.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.joalbano.crud.entity.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends RepresentationModel<ProductDTO> implements Serializable {
	
	private static final long serialVersionUID = 1006122387584186400L;
	
	private Long id;
	private String name;
	private Integer stock;
	private Double price;
	
	public static ProductDTO toProductDTO(Product product) {
		return new ModelMapper().map(product, ProductDTO.class);
	}
}
