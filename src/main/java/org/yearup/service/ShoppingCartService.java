package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        ShoppingCart shoppingCart = new ShoppingCart();

        for (CartItem cartItem : cartItems) {

            Product product = productService.getById(cartItem.getProductId());
            if (product != null) {
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                shoppingCartItem.setProduct(product);
                shoppingCartItem.setQuantity(cartItem.getQuantity());

                shoppingCart.add(shoppingCartItem);
            }
        }
        return shoppingCart;
    }

    public ShoppingCart addProduct(int userId, int productId) {
        CartItem currentItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (currentItem == null) {
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            shoppingCartRepository.save(newItem);
        } else {
            currentItem.setQuantity(currentItem.getQuantity() + 1);
            shoppingCartRepository.save(currentItem);
        }
        return getByUserId(userId);
    }

    public ShoppingCart updateQuantity(int userId, int productId, int quantity) {
        CartItem currentItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (currentItem != null) {
            currentItem.setQuantity(quantity);
            shoppingCartRepository.save(currentItem);
        }
        return getByUserId(userId);
    }

    public ShoppingCart clearCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
        return getByUserId(userId);
    }
}
