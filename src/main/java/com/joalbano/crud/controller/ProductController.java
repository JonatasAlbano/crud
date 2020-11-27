package com.joalbano.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joalbano.crud.dto.ProductDTO;
import com.joalbano.crud.services.ProductService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private PagedResourcesAssembler<ProductDTO> assembler;
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public ProductDTO findById(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.findById(id);
		productDTO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
		return productDTO;
	}
	
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
				
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page,  limit, Sort.by(sortDirection, "name"));
		Page<ProductDTO> listProducts = productService.findAll(pageable);
		listProducts.stream()
				    .forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));
	
		PagedModel<EntityModel<ProductDTO>> pagedModel = assembler.toModel(listProducts);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}	
	
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
				 consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ProductDTO create(@RequestBody ProductDTO productDTO) {
		ProductDTO createProduct = productService.create(productDTO);
		createProduct.add(linkTo(methodOn(ProductController.class).findById(productDTO.getId())).withSelfRel());
		return createProduct;
	}
	
	@PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
			 consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ProductDTO update(@RequestBody ProductDTO productDTO) {
		ProductDTO createProduct = productService.update(productDTO);
		createProduct.add(linkTo(methodOn(ProductController.class).findById(productDTO.getId())).withSelfRel());
		return createProduct;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		productService.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
