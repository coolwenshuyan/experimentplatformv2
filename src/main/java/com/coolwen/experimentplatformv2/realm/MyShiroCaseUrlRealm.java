package com.coolwen.experimentplatformv2.realm;

import com.coolwen.experimentplatformv2.kit.ShiroKit;
import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.AdminService;
import com.coolwen.experimentplatformv2.service.ClassService;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.service.UserService;
import com.coolwen.experimentplatformv2.utils.CasUtils;
import com.coolwen.experimentplatformv2.utils.SpringBeanFactoryUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @ProjectName: experimentplatform
 * @Package: com.coolwen.experimentplatformv2.realm
 * @ClassName: StudentRealm
 * @Author: Txc
 * @Description: 单点登陆使用的Realm
 * @Date: 2020/5/15 0015 13:18
 * @Version: 1.0
 */
public class MyShiroCaseUrlRealm extends CasRealm {
    protected static final Logger logger = LoggerFactory.getLogger(MyShiroCaseUrlRealm.class);
    @Autowired
//    @Lazy//解决shiro和事务管理不生效问题
    private StudentService studentService;

    @Autowired
//    @Lazy
    private ClassService classService;

    @Autowired
//    @Lazy//解决shiro和事务管理不生效问题
    private UserService userService;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("授权开始------------------------>>>>>>>>>>>>>>>>>>>>>>>>");
        Map<Object, Object> map = CasUtils.getUserInfo(SecurityUtils.getSubject().getSession());
        String comsys_role = (String) map.get("comsys_role");
        String comsys_name = (String) map.get("comsys_name");
        logger.debug("授权角色信息:" + comsys_role);
        logger.debug("授权账号:" + principals.getPrimaryPrincipal() + "授权用户名:" + comsys_name);
        String xuehao = (String) SecurityUtils.getSubject().getSession().getAttribute("account");
//        int uid = 0;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (comsys_role.contains("ROLE_STUDENT")) {
            Student student = studentService.findStudentByXueHao(xuehao);
            SecurityUtils.getSubject().getSession().setAttribute("student", student);
            logger.debug("授权学生信息:" + student);
        } else if (comsys_role.contains("ROLE_TEACHER")) {
            String gonghao = (String) map.get("comsys_teaching_number");
            logger.debug("授权老师工号信息:" + "," + gonghao);
//            userService.findByUsername(xuehao);
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
            logger.debug("授权老师Session信息:" + "," + user);
            //把老师工号存入系统
            user.setGonghao(gonghao);
            userService.update(user);
            logger.debug("授权老师信息:" + user);
            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
            List<String> roles = userService.listRoleSnByUser(user.getId());
            logger.debug("授权角色:" + roles);
            List<Resource> reses = userService.listAllResource(user.getId());
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

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("StudentCaseRealm登陆验证开始");
// 调用父类中的认证方法，CasRealm已经为我们实现了单点认证。
        AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
        // 获取登录的账号，cas认证成功后，会将账号存起来
        String xuehao = (String) authc.getPrincipals().getPrimaryPrincipal();
        logger.debug("登陆账号:" + xuehao);
        // 将用户信息存入session中,方便程序获取,此处可以将根据登录账号查询出的用户信息放到session中
        SecurityUtils.getSubject().getSession().setAttribute("account", xuehao);
//        if (xuehao.length() == 10) {
//            logger.debug("登陆账号:" + xuehao);
//
//            if (ShiroKit.isEmpty(studentService)) {
//                logger.debug("studentService信息:" + studentService);
//                studentService = SpringBeanFactoryUtils.getBean(StudentService.class);
//            }
//            Student student = studentService.findStudentByXueHao(xuehao);
//            logger.info("登陆学生信息:" + student);
//            //成功则放入session
//            SecurityUtils.getSubject().getSession().setAttribute("student", student);
//        }
//        if (xuehao.length() == 18) {
//            //查询本地老师信息
//
//            if (ShiroKit.isEmpty(userService)) {
//                logger.debug("userService信息:" + userService);
//                userService = SpringBeanFactoryUtils.getBean(UserService.class);
//            }
//            User user = userService.findByUsername(xuehao);
//            logger.info("登陆老师信息:" + user);
//            //成功则放入session
//            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
//        }
        return authc;
    }

    //清除认证
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        logger.debug("授权缓存清除:");
////        User u = (User) principals.getPrimaryPrincipal();
//        String xuehao = (String) SecurityUtils.getSubject().getSession().getAttribute("account");
//        SimplePrincipalCollection sp = new SimplePrincipalCollection(xuehao, getName());
//        Cache c = this.getAuthenticationCache();
//        Set<Object> keys = c.keys();
//        for (Object o : keys) {
//            logger.debug("授权缓存:", o);
//            logger.debug("授权缓存:", c.get(o));
//        }
//        super.clearCachedAuthenticationInfo(sp);
        super.clearCachedAuthenticationInfo(principals);
    }


    //清除权限
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
//
//        Cache c = this.getAuthorizationCache();
//        Set<Object> keys = c.keys();
//        for (Object o : keys) {
//            logger.debug("认证缓存: {0}.", o);
//            logger.debug("认证缓存: {0}.", c.get(o));
//        }
//        User user = ((User) principals.getPrimaryPrincipal());
//        SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUsername(), getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
