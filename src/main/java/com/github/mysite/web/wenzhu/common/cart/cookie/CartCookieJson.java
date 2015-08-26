package com.github.mysite.web.wenzhu.common.cart.cookie;

import com.alibaba.fastjson.JSONArray;
import com.github.mysite.web.wenzhu.common.cart.Goods;
import com.github.mysite.web.wenzhu.common.common.CookieHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * description:操作cookie与购物项的转换
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/13 - 15:47
 */
public class CartCookieJson {

    public List<CartItemCookie> readCartItemCookie(HttpServletRequest request) {

        List<CartItemCookie> cookieList = null;
        Map<String, Cookie> cookieMap = CookieHelper.readCookieMap(request);
        if (!cookieMap.isEmpty()) {
            for (Map.Entry<String, Cookie> entry : cookieMap.entrySet()) {
                Cookie cookie = entry.getValue();
                if (StringUtils.equals(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
                    if (StringUtils.isNotEmpty(cookie.getValue())) {
                        cookieList = JSONArray.parseArray(cookie.getValue(), CartItemCookie.class);
                        // JsonConfig jsonConfig = new JsonConfig();
                        // jsonConfig.setRootClass(CartItemCookie.class);
                        // JSONArray jsonArray = JSONArray.fromObject(cookie.getValue());
                        // cookieList = (List<CartItemCookie>) JSONSerializer.toJava(jsonArray, jsonConfig);
                    }
                }
            }
        }
        return cookieList;
    }

    public String addCartItemCookie(HttpServletRequest request, HttpServletResponse response, String gId, int quantity) {

        int nQuantity = 0;
        int tQuantity = 0;
        BigDecimal tPrice = new BigDecimal(0);
        List<CartItemCookie> newCookieList = newArrayList();
        List<CartItemCookie> cartItemList = readCartItemCookie(request);
        if (cartItemList != null) {
            for (CartItemCookie ctc : cartItemList) {
                Goods goods = new Goods(); // 这里换成具体的业务,通过ctc.getI()获取具体的商品
                if (StringUtils.equals(gId, goods.getId())) {
                    nQuantity = quantity + ctc.getQ();
                    ctc.setQ(nQuantity);
                }
                newCookieList.add(ctc);
                tQuantity += ctc.getQ();
                tPrice = tPrice.add(goods.getPrice().multiply(new BigDecimal(ctc.getQ())));
            }

        } else {
            Goods goods = new Goods();// 根据具体业务来变,通过gId获取Goods
            CartItemCookie nCookie = new CartItemCookie();
            nCookie.setQ(quantity);
            nCookie.setI(gId);
            newCookieList.add(nCookie);
            tQuantity += quantity;
            tPrice = tPrice.add(goods.getPrice().multiply(new BigDecimal(quantity)));
        }
        CookieHelper.addCookie(
                response, CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JSONArray.toJSONString(newCookieList), 60 * 60 * 24, null, null
        );
        return tQuantity + "-" + tPrice;
    }

    public void clearCartItemCookie(HttpServletResponse response) {
        CookieHelper.removeCookie(
                response, CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null, null
        );
    }

    public String removeCartItemCookie(HttpServletRequest request, HttpServletResponse response, String gId) {
        int tQuantity = 0;
        BigDecimal tPrice = new BigDecimal(0);
        List<CartItemCookie> cartItemList = readCartItemCookie(request);
        if (cartItemList != null) {
            Iterator<CartItemCookie> iterator = cartItemList.iterator();
            while (iterator.hasNext()) {
                CartItemCookie ctc = iterator.next();
                if (StringUtils.equals(ctc.getI(), gId)) {
                    iterator.remove();
                }
                Goods goods = new Goods(); // 这里换成具体的业务,通过ctc.getI()获取具体的商品
                tQuantity += ctc.getQ();
                tPrice = tPrice.add(goods.getPrice().multiply(new BigDecimal(ctc.getQ())));
            }
            CookieHelper.addCookie(
                    response, CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JSONArray.toJSONString(cartItemList), 60 * 60 * 24, null, null
            );
        }
        return tQuantity + "-" + tPrice;
    }

    public String changeQuantityCartItemCookie(HttpServletRequest request, HttpServletResponse response, String gId, int quantity) {

        int tQuantity = 0;
        BigDecimal tPrice = new BigDecimal(0);
        List<CartItemCookie> newCookieList = newArrayList();
        List<CartItemCookie> cartItemList = readCartItemCookie(request);
        if (cartItemList != null) {
            for (CartItemCookie ctc : cartItemList) {
                Goods goods = new Goods(); // 这里换成具体的业务,通过ctc.getI()获取具体的商品
                if (StringUtils.equals(gId, goods.getId())) {
                    ctc.setQ(quantity);
                }
                newCookieList.add(ctc);
                tQuantity += ctc.getQ();
                tPrice = tPrice.add(goods.getPrice().multiply(new BigDecimal(ctc.getQ())));
            }

        }
        CookieHelper.addCookie(
                response, CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, JSONArray.toJSONString(newCookieList), 60 * 60 * 24, null, null
        );
        return tQuantity + "-" + tPrice;
    }
}
