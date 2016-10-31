package cz.etn.etnshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.etn.etnshop.dao.Product;
import cz.etn.etnshop.dao.ProductDao;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	@Override
	public boolean changeProduct(Product product) {
		return productDao.editProduct(product);
	}

	@Override
	public boolean deleteProduct(Product product) {
		return productDao.deleteProduct(product);
	}

	@Override
	public int addProduct(Product product) {
		return productDao.addProduct(product);
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		return productDao.searchProducts(keyword);
	}

}
