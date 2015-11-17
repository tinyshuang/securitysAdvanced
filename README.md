# securitysAdvanced

###项目说明 :   

	这是一个spring-security的Demo..第二版...实现自定义登录验证了..  
	
	
security里面的权限用户是读取数据库表格的....  


###本项目的权限设计是 :  

user表里面有一个角色字段..  

这个角色字段对应相应可以访问页面的权限..  

以此达到最简单的权限设计...  


###项目里面以及配套了sql语句.  
	admin用户可以访问全部页面,one用户只可以访问index页面..
	login没设置拦截...accessDeny是权限不足返回的页面..
	这里本来想统筹spring mvc...但是为了体现security,就直接使用jsp来替代了...

###主要的代码在于 :  

util.security包..以及spring-security.xml配置文件

###本项目是参考此博文的 :  

http://blog.csdn.net/u012367513/article/details/38866465
