package cz.etn.etnshop.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cz.etn.etnshop.dao.Product;

public interface ProductService {

	@Transactional(readOnly = true)
	List<Product> getProducts();

	@Transactional
	boolean changeProduct(Product product);

	@Transactional
	boolean deleteProduct(Product product);

	@Transactional
	int addProduct(Product product);
	
	@Transactional(readOnly = true)
	List<Product> searchProducts(String keyword);

}
