package com.github.wenzhu.cart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.math.BigDecimal;

/**
 * description:购物项
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/13 - 15:22
 */
public class CartItem {
    private Goods goods;
    private BigDecimal price = new BigDecimal(0);
    private Integer quantity = 1;

    public CartItem() {

    }

    public CartItem(Goods goods, BigDecimal price, Integer quantity) {
        this.goods = goods;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        if (null != goods) {
            this.price = this.goods.getPrice().multiply(new BigDecimal(quantity));
        }
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Goods getGoods() {
        return goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equal(goods, cartItem.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(goods);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("goods", goods)
                .add("price", price)
                .add("quantity", quantity)
                .toString();
    }
}

