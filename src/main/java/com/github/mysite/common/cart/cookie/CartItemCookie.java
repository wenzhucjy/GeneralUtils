package com.github.mysite.common.cart.cookie;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:55
 */
public class CartItemCookie {

    /**
     * 保存购物项集合的Cookie名称
     */
    public static final String CART_ITEM_LIST_COOKIE_NAME = "cartItemCookie";
    /**
     * 商品的最大购买量
     */
    public static final Integer CART_GOODS_MAX_NUM = 100;
    /**
     * 商品ID
     */
    private String i;
    /**
     * 商品购买数量
     */
    private Integer q;


    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Integer getQ() {
        return q;
    }

    public void setQ(Integer q) {
        this.q = q;
    }


}
