package com.github.wenzhu.cart;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * description:商品用例
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:53
 */
public class Goods {


    private String id;
    private String name;
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("price", price)
                .toString();
    }
}
