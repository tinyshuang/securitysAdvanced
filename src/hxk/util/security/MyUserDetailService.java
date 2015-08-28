package hxk.util.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import hxk.dao.UserDao;  


/**
 * 
 * @author hxk
 * @description 用来在用户登录的时候获取这个用户的相关权限
 * 	
 *2015年8月25日  下午4:01:51
 */
public class MyUserDetailService implements UserDetailsService {   

    //登陆验证时，通过username获取用户的所有权限信息，  
    //并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {     
        Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();   
        hxk.model.User myuser = userDao.find(username);
        //下面传权限相关的字符串给SimpleGrantedAuthority
        //这个简单的例子我们使用的只是
        //	用户<==>角色
        //	角色<==>是否有权限访问页面
        //比如"ROLE_ADMIN"代表有权限访问admin.jsp页面
        GrantedAuthority auth=new SimpleGrantedAuthority(myuser.getRole());
        auths.add(auth);
        //包装返回security的User..
        User user = new User(myuser.getName(), myuser.getPwd(), true, true, true, true, auths);   
        return user;    
   }   
    
    
    //注入dao
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}  
