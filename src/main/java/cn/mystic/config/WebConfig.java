package cn.mystic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/templates/**", "/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/",ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
		super.addResourceHandlers(registry);
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login", "/user/signin");
        super.addInterceptors(registry);
    }

}
