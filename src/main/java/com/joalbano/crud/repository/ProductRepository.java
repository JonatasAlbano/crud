package com.joalbano.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joalbano.crud.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
