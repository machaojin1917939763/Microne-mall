
package com.microne.mall.interceptor;

import com.microne.mall.controller.vo.MicroneMallUserVO;
import com.microne.mall.common.Constants;
import com.microne.mall.dao.MicroneMallShoppingCartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Microne-mall购物车数量处理
 *
 * @author machaojin
 * 
 * @email 1917939763@qq.com
 * 
 */
@Component
public class MicroneMallCartNumberInterceptor implements HandlerInterceptor {

    @Autowired
    private MicroneMallShoppingCartItemMapper MicroneMallShoppingCartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //购物车中的数量会更改，但是在这些接口中并没有对session中的数据做修改，这里统一处理一下
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            //如果当前为登陆状态，就查询数据库并设置购物车中的数量值
            MicroneMallUserVO MicroneMallUserVO = (MicroneMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
            //设置购物车中的数量
            MicroneMallUserVO.setShopCartItemCount(MicroneMallShoppingCartItemMapper.selectCountByUserId(MicroneMallUserVO.getUserId()));
            request.getSession().setAttribute(Constants.MALL_USER_SESSION_KEY, MicroneMallUserVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
