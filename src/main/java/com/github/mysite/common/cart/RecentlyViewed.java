package com.github.mysite.common.cart;

import com.github.mysite.common.common.CookieHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * description: Cookie实现最近浏览记录
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-09 14:40
 */
public class RecentlyViewed {

    /**
     * 添加最近浏览记录
     * 
     * @param request HttpServletRequest
     * @param currentProductId 当前的记录编号
     * @param cookieName 浏览历史保存的 Cookie 名称
     * @param maxCount 记录数
     * @return 浏览记录
     */
    public static String buildViewHistory(HttpServletRequest request, String currentProductId, String cookieName, int maxCount) {
        // 23-2-6-5
        // 1.如果当前浏览的id已经在浏览历史里了,我们要把移到最前面
        // 2.如果浏览历史里已经达到了10个产品了,我们需要把最选进入的元素删除
        String cookieValue = CookieHelper.getCookieByName(request, cookieName);
        LinkedList<String> productIds = Lists.newLinkedList();
        if (StringUtils.isNotBlank(cookieValue)) {
            String[] ids = cookieValue.split("-");
            for (String id : ids) {
                // 添加指定的元素作为这个列表的尾部(最后一个元素)
                productIds.offer(id.trim());
            }
            if (productIds.contains(currentProductId)) {
                productIds.remove(currentProductId);
            }
            if (productIds.size() >= maxCount) {
                // 获取和删除这个列表的头(第一个元素)
                productIds.poll();
            }
        }
        productIds.offer(currentProductId);
        StringBuilder sb = new StringBuilder();
        for (String id : productIds) {
            sb.append(id).append('-');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
