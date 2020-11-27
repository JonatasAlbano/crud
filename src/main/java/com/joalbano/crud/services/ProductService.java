package com.joalbano.crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joalbano.crud.dto.ProductDTO;
import com.joalbano.crud.entity.Product;
import com.joalbano.crud.exception.ResourceNotFoundException;
import com.joalbano.crud.message.ProductSendMessage;
import com.joalbano.crud.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductSendMessage productSendMessage;

	public ProductDTO create(ProductDTO productDTO) {
		ProductDTO productDTOCreated = ProductDTO.toProductDTO(productRepository.save(Product.toProduct(productDTO)));
		productSendMessage.sendMessage(productDTOCreated);
		return productDTOCreated;
	}
	
	public Page<ProductDTO> findAll(Pageable pageable) {
		var page = productRepository.findAll(pageable);
		return page.map(this::convertToProductDTO);
	}

	private ProductDTO convertToProductDTO(Product product) {
		return ProductDTO.toProductDTO(product);
	}
	
	public ProductDTO findById(Long id) {
		var entity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found"));
		return ProductDTO.toProductDTO(entity);
	}
	
	public ProductDTO update(ProductDTO productDTO) {
		final Optional<Product> optionalProduct = productRepository.findById(productDTO.getId());
	
		if(!optionalProduct.isPresent()) {
			new ResourceNotFoundException("No records found");
		}
		
		return ProductDTO.toProductDTO(productRepository.save(Product.toProduct(productDTO)));
	}
	
	public void delete(Long id) {
		var entity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found"));
		productRepository.delete(entity);
	}
}
