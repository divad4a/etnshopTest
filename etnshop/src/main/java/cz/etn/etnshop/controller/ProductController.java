package cz.etn.etnshop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.etn.etnshop.dao.Product;
import cz.etn.etnshop.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("product/list");
		modelAndView.addObject("products", productService.getProducts());
		modelAndView.addObject("searchKeyword", "");
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView getEditList(@RequestParam(required = false) String searchKeyword) {
		logger.debug("get search keyword = " + searchKeyword);
		List<Product> list;
		if (searchKeyword == null || searchKeyword.equals("")) {
			list = productService.getProducts();
		} else {
			list = productService.searchProducts(searchKeyword);
		}

		ModelAndView modelAndView = new ModelAndView("product/edit");
		modelAndView.addObject("products", list);
		modelAndView.addObject("searchKeyword", searchKeyword);
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<?> changeInEditList(@RequestBody Product input) {

		logger.debug("change product, id=" + input.getId() + ", name = " + input.getName() + ", serialNo=" + input.getSerialNo());
		boolean result = productService.changeProduct(input);

		return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFromEditList(@RequestBody Product input) {

		logger.debug("delete product, id=" + input.getId() + ", name = " + input.getName() + ", serialNo=" + input.getSerialNo());
		boolean result = productService.deleteProduct(input);

		return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<?> addToEditList(@RequestBody Product input) {

		logger.debug("add product, id=" + input.getId() + ", name = " + input.getName() + ", serialNo=" + input.getSerialNo());
		int result = productService.addProduct(input);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/edit/editTableRow", method = RequestMethod.GET)
	public ModelAndView getEditTestRow() {
		ModelAndView modelAndView = new ModelAndView("product/editTableRow");
		return modelAndView;
	}
}
