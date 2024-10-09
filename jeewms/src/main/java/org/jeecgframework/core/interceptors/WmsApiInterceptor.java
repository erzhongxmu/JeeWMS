package org.jeecgframework.core.interceptors;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Package org.jeecgframework.core.interceptors
 * @date 2021/7/22 14:33
 * @description
 */
public class WmsApiInterceptor implements HandlerInterceptor {

    private List<String> containUrls;

    public List<String> getContainUrls() {
        return containUrls;
    }

    public WmsApiInterceptor setContainUrls(List<String> containUrls) {
        this.containUrls = containUrls;
        return this;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestPath = ResourceUtil.getRequestPath(request);
        if (containUrls.contains(requestPath)) {
            //检验token
            String token  = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                throw new BusinessException("need a token");
            }
            String account =  JwtUtils.vaildToken(token);
            if ("wmsAccount".equals(account)) {
                return true;
            }else {
               throw new BusinessException("invalid token" );
            }
        }else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }
}
