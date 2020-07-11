package com.coolwen.experimentplatformv2.realm;

import com.coolwen.experimentplatformv2.model.Resource;
import com.coolwen.experimentplatformv2.model.Student;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 2016/6/4.
 * <p>本地测试使用，没有加入单点登陆
 * 权限登录与验证
 */


public class MyShiroRealm extends AuthorizingRealm {

    protected static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    @Lazy//解决shiro和事务管理不生效问题
    private UserService userService;

    @Autowired
//    @Lazy//解决shiro和事务管理不生效问题
    private StudentService studentService;

    //    用来判断授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principals.getPrimaryPrincipal() instanceof User) {
            logger.debug("授权-------------->>>>>>>>>>>>");
            User user = (User) principals.getPrimaryPrincipal();
            int uid = user.getId();
            logger.debug("授权用户:" + user.getId() + "," + user.getUsername());
            List<String> roles = userService.listRoleSnByUser(uid);
            logger.debug("授权角色:" + roles);
            List<Resource> reses = userService.listAllResource(uid);
            List<String> permissions = new ArrayList<String>();
            logger.debug("授权资源:" + reses);
            for (Resource r : reses) {
                permissions.add(r.getUrl());
            }
            info.setRoles(new HashSet<String>(roles));
            info.setStringPermissions(new HashSet<String>(permissions));
        }
        return info;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        logger.debug("登录用户名username:" + username);
        String password = new String((char[]) token.getCredentials());
        SimpleAuthenticationInfo info;
        logger.debug("登录password:" + password);
        if (username.length() == 10) {
            Student student = studentService.findStudentByXueHao(username);
            logger.debug("登陆学生信息:" + student);
            //成功则放入session
            SecurityUtils.getSubject().getSession().setAttribute("student", student);
            SecurityUtils.getSubject().getSession().getAttribute("student");
            SecurityUtils.getSubject().getSession().getAttribute("teacher");
            info = new SimpleAuthenticationInfo(student, student.getStuPassword(), this.getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(student.getStuXuehao()));
        } else {
            User user = userService.findByUsername(username);
//            Admin admin = adminService.findByUname(xuehao);
            logger.debug("登陆老师信息:" + user);
            //成功则放入session
            info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
        }
        return info;
    }


    //清除认证
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        User u = (User) principals.getPrimaryPrincipal();
        SimplePrincipalCollection sp = new SimplePrincipalCollection(u.getUsername(), getName());
        Cache c = this.getAuthenticationCache();
        Set<Object> keys = c.keys();
        for (Object o : keys) {
            logger.debug("授权缓存:", o);
            logger.debug("授权缓存:", c.get(o));
        }
        super.clearCachedAuthenticationInfo(sp);
        //   super.clearCachedAuthenticationInfo(principals);
    }


    //清除权限
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {

        Cache c = this.getAuthorizationCache();
        Set<Object> keys = c.keys();
        for (Object o : keys) {
            logger.debug("认证缓存: {0}.", o);
            logger.debug("认证缓存: {0}.", c.get(o));
        }
//        User user = ((User) principals.getPrimaryPrincipal());
//        SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUsername(), getName());
        super.clearCachedAuthorizationInfo(principals);
    }

}
