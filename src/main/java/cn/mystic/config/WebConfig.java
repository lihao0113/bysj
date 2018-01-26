package cn.mystic.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	  
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/templates/**", "/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/",ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
		super.addResourceHandlers(registry);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
/*	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了login，其他都拦截判断
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("*//**").excludePathPatterns("/rwkh/home");
        super.addInterceptors(registry);
    }*/
}
