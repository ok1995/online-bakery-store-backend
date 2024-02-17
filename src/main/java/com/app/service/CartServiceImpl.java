package com.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CartRepository;
import com.app.dao.ProductRepository;
import com.app.dao.StockRepository;
import com.app.dao.UserRepository;
import com.app.pojos.Cart;
import com.app.pojos.Product;
import com.app.pojos.Stock;
import com.app.pojos.User;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private ProductRepository prodRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private StockRepository stockRepo;
	
	@Override
	public String addItemToCart(Integer productId, Integer quantity, Integer userId) {
		User customer = userRepo.findById(userId).get();
		Product product = prodRepo.findById(productId).get();
		Stock stock = stockRepo.findById(productId).get();
		if( stock.getQuantity() < quantity) {
			return "We only have "+stock.getQuantity()+" "+stock.getUnit()+"(s) of "+product.getName()+" .";
		}
		cartRepo.save(new Cart(quantity, product, customer));
		return quantity+" "+stock.getUnit()+"(s) of "+product.getName()+" added to cart";
	}

	@Override
	public List<Cart> getAllCartContents(Integer userId) {		
		return cartRepo.findAllItemsByUser(userId);
	}

	@Override
	public String updateQuantity(Integer cartId, Integer quantity) {
		Cart cartItem = cartRepo.findById(cartId).get();
		int stockId = cartItem.getSelectedProduct().getId();
		Stock stock = stockRepo.findById(stockId).get();
		if(stock.getQuantity() < quantity)
			return "We only have "+stock.getQuantity()+" "+stock.getUnit()+"(s) left!.";
		cartItem.setQuantity(quantity);
		return "success";
	}

	@Override
	public void deleteFromCart(Integer cartId) {
		cartRepo.deleteById(cartId);
	}

	@Override
	public void deleteAllFromCart(Integer userId) {
		cartRepo.deleteAll(cartRepo.findAllItemsByUser(userId));
		return;
	}

	@Override
	public Optional<Cart> findById(Integer cartId) {
		return cartRepo.findById(cartId);
	}

}
