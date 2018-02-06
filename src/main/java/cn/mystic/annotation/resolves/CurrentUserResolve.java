package cn.mystic.annotation.resolves;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.mystic.annotation.CurrentUser;
import cn.mystic.dao.SysUserRepository;
import cn.mystic.domain.SysUser;

@Component
public class CurrentUserResolve implements HandlerMethodArgumentResolver {
	
    @Autowired
    private SysUserRepository sysUserRepository; // 注入sysUserRepository

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		 HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		 SysUser user = null;
		 Cookie[] cookie = request.getCookies();
	        if (cookie != null) {
	            for (int i = 0; i < cookie.length; i++) {
	                if ("userId".equals(cookie[i].getName())){
	                    user = sysUserRepository.findOne(Integer.parseInt(cookie[i].getValue()));
	                    break;
	                }
	            }
	        }
		return user;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().isAssignableFrom(SysUser.class) && // 如果参数类型是SysUser并且有CurrentUser注解则支持
				parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}

}
