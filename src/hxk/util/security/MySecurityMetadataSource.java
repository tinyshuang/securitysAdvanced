package hxk.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Logger logger = Logger.getLogger(MySecurityMetadataSource.class);
    
    //声明url与角色的关系
    private static Map<String, Collection<ConfigAttribute>> map = null;  


    public MySecurityMetadataSource() {
	//在spring注入的时候..调用以下方法
	loadResourceDefine();
    }
    
    /**
     * @description 加载路径与资源的对应
     * 	这一部分第二版会改成从数据库加载url以及对应的权限关系..
     *  url<==>role  得在数据库添加一个url对应角色的表格 一对多的关系
     * 	现在先写死在这里..	
     *2015年8月25日  下午4:19:16
     *返回类型:void
     */
    private void loadResourceDefine() {
	if (map == null) {
	    map = new HashMap<String, Collection<ConfigAttribute>>();
	    //定义一个只有admin权限才能访问的属性
	    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();   
	    ConfigAttribute caadmin = new SecurityConfig("ROLE_ADMIN");  
	    atts.add(caadmin);   
	    map.put("admin.jsp", atts);  
	    
	    //定义一个admin和普通用户都可以访问的属性
	    Collection<ConfigAttribute> attsca = new ArrayList<ConfigAttribute>();   
	    ConfigAttribute ca = new SecurityConfig("ROLE_USER");  
	    attsca.add(ca);   
	    attsca.add(caadmin);
	    map.put("index.jsp", attsca);    
	}
    }

    
    

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
	return null;
    }
    
    
    @Override
    /**
     * @description 获取用户访问的地址所需的权限
     *2015年8月25日  下午4:19:16
     *返回类型:void
     */
    public Collection<ConfigAttribute> getAttributes(Object arg0) throws IllegalArgumentException {
	FilterInvocation invation = (FilterInvocation) arg0;
	String requestUrl = invation.getRequestUrl();
	logger.info("=============> uurl : " + requestUrl);
	requestUrl=requestUrl.substring(1,requestUrl.length());
	int flag =requestUrl.indexOf("?");
	if(flag>0){
		requestUrl=requestUrl.substring(0,flag);
	}
	logger.info("=============> url :" + requestUrl);
	if(map == null) {
		try {
			//执行加载权限方法
			loadResourceDefine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	logger.info("==============> role : " + map.get(requestUrl));
	return map.get(requestUrl);
    }

    @Override
    public boolean supports(Class<?> arg0) {
	//这里返回true就可以解决无法注入的问题
	//特么这里一定要返回true
	return true;
    }
	


}
