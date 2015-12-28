package com.github.mysite.common.cart.session;

import com.github.mysite.common.cart.CartItem;

import java.math.BigDecimal;
import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * description:session域购物车
 *
 * @author :    jy.chen
 *  @version  :  1.0
 * @since  : 2015/8/14 - 10:00
 */
public class SessionCart {

    private Integer totalQuantity = 0;
    private BigDecimal totalPrice = new BigDecimal(0);
    private Map<String, CartItem> cartMap = newLinkedHashMap();


    public Integer getQuantity() {
        int totalQuantity = 0;
        for (Map.Entry<String, CartItem> entry : cartMap.entrySet()) {
            // 拿到购物车的每一个购物项
            CartItem item = entry.getValue();
            totalQuantity += item.getQuantity();
        }
        this.totalQuantity = totalQuantity;
        return totalQuantity;

    }

    /**
     * 得到购物车中的总商品的价格
     *
     * @return
     */
    public BigDecimal getPrice() {
        totalPrice = new BigDecimal("0");
        BigDecimal itemPrice = new BigDecimal("0");
        for (Map.Entry<String, CartItem> entry : cartMap.entrySet()) {
            // 拿到购物车的每一个购物项
            CartItem item = entry.getValue();
            this.totalPrice = itemPrice.add(item.getPrice()).add(totalPrice);
        }
        return totalPrice;
    }

    /**
     * 向购物车中增加购物项，要先检查购物车中是否已经存在对应的购物项
     *
     * @param item
     */
    public void addCartItem(CartItem item) {
        if (cartMap.containsKey(item.getGoods().getId())) {
            for (Map.Entry<String, CartItem> items : cartMap.entrySet()) {
                CartItem cItem = items.getValue();
                if (cItem.equals(item)) {
                    cItem.setQuantity(cItem.getQuantity() + item.getQuantity());
                    break;
                }
            }
        } else {
            cartMap.put(item.getGoods().getId(), item);
        }
    }

    /**
     * 通过商品sn获得购物项的数量
     *
     * @param goodsSn
     * @return
     */
    public int getQuantityBySn(Long goodsSn) {
        int count = 0;
        if (cartMap.containsKey(goodsSn)) {
            count = cartMap.get(goodsSn).getQuantity();
        }
        return count;
    }


    public Map<String, CartItem> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<String, CartItem> cartMap) {
        this.cartMap = cartMap;
    }

}
