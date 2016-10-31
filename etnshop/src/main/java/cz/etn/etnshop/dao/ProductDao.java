package cz.etn.etnshop.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ProductDao {

	@Transactional(readOnly = true)
	List<Product> getProducts();

	@Transactional
	boolean editProduct(Product product);

	@Transactional
	boolean deleteProduct(Product product);

	@Transactional
	public int addProduct(Product product);

	@Transactional(readOnly = true)
	List<Product> searchProducts(String keyword);

}
