package com.coolwen.experimentplatformv2.realm;

import com.coolwen.experimentplatformv2.model.*;
import com.coolwen.experimentplatformv2.service.AdminService;
import com.coolwen.experimentplatformv2.service.ClassService;
import com.coolwen.experimentplatformv2.service.StudentService;
import com.coolwen.experimentplatformv2.service.UserService;
import com.coolwen.experimentplatformv2.utils.CasUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
            AdminService adminService;

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
        int uid = 0;
        if (comsys_role.contains("ROLE_STUDENT")) {
            Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute("student");
            logger.debug("授权学生信息:" + student);
            uid = student.getId();
            logger.debug("授权用户:" + uid + "," + student.getStuName());
        } else if (comsys_role.contains("ROLE_TEACHER")) {
            String gonghao = (String) map.get("comsys_teaching_number");
            logger.debug("授权老师工号信息:" + "," + gonghao);
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("teacher");
            //把老师工号存入系统
            user.setGonghao(gonghao);
            userService.add(user);
            uid = user.getId();
            logger.debug("授权老师信息:" + uid + "," + user.getUsername());
            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
        }
        List<String> roles = userService.listRoleSnByUser(uid);
        logger.debug("授权角色:" + roles);
        List<Resource> reses = userService.listAllResource(uid);
        List<String> permissions = new ArrayList<String>();
        logger.debug("授权资源:" + reses);
        for (Resource r : reses) {
            permissions.add(r.getUrl());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(new HashSet<String>(roles));
        info.setStringPermissions(new HashSet<String>(permissions));
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
//        Map<Object, Object> map = CasUtils.getUserInfo(SecurityUtils.getSubject().getSession());
//        logger.debug("登陆map信息:" + map);
////        String comsys_role = (String) map.get("comsys_role");
        if (xuehao.length() == 10) {
            Student student = studentService.findStudentByXueHao(xuehao);
            logger.debug("登陆学生信息:" + student);
            //成功则放入session
            SecurityUtils.getSubject().getSession().setAttribute("student", student);
        }
        if (xuehao.length() == 18) {
            //查询本地老师信息
            User user = userService.findByUsername(xuehao);
//            Admin admin = adminService.findByUname(xuehao);
            logger.debug("登陆老师信息:" + user);
            //成功则放入session
            SecurityUtils.getSubject().getSession().setAttribute("teacher", user);
        }
        return authc;

    }
}
