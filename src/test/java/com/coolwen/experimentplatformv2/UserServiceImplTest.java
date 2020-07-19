package com.coolwen.experimentplatformv2;


import com.coolwen.experimentplatformv2.model.Resource;
import com.coolwen.experimentplatformv2.model.Role;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.ResourceService;
import com.coolwen.experimentplatformv2.service.RoleService;
import com.coolwen.experimentplatformv2.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Test
    public void addUser() {
        User user = new User();
        user.setNickname("温老师");
        user.setUsername("0");
        user.setPassword("123");
        user.setStatus(false);
        user.setGonghao("0000935");
        userService.add(user);
        user = new User();
        user.setNickname("朱老师");
        user.setUsername("1");
        user.setPassword("123");
        user.setGonghao("0010086");
        user.setStatus(false);
        userService.add(user);
    }

    @Test
    public void addRes() {
        Resource res = new Resource();
        res.setName("系统管理");
        res.setUrl("/**");
        res.setPermission("admin:*");
        resourceService.add(res);

//        res = new Resource();
//        res.setName("课程设置");
//        res.setUrl("/courseinfo/list");
//        res.setPermission("courseinfo:*");
//        resourceService.add(res);
//
//        res = new Resource();
//        res.setName("课程安排");
//        res.setUrl("/arrangeclass/list");
//        res.setPermission("arrangeclass:*");
//        resourceService.add(res);
//
//        res = new Resource();
//        res.setName("考核模块设置");
//        res.setUrl("/kaohemodel/checkModule");
//        res.setPermission("kaohemodel:*");
//        resourceService.add(res);

        res = new Resource();
        res.setName("后台首页");
        res.setUrl("/learning/kuangjia");
        res.setPermission("learning:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("权限和教师账号管理");
        res.setUrl("/admin/**");
        res.setPermission("admin:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("平台公告");
        res.setUrl("/newsinfoback/**");
        res.setPermission("newsinfoback:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("简介轮播");
        res.setUrl("/setinfoback/**");
        res.setPermission("setinfoback:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("课程设置");
        res.setUrl("/courseinfo/**");
        res.setPermission("courseinfo:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("课程安排");
        res.setUrl("/arrangeclass/**");
        res.setPermission("arrangeclass:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("学生账户管理");
        res.setUrl("/studentManage/**");
        res.setPermission("/studentManage:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("学习效果");
        res.setUrl("/learning/**");
        res.setPermission("learning:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("师资队伍后台");
        res.setUrl("/teachers/**");
        res.setPermission("teachers:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("学院版教师评分");
        res.setUrl("/collegereportmark/**");
        res.setPermission("collegereportmark:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("考核模块");
        res.setUrl("/kaohemodel/**");
        res.setPermission("kaohemodel:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("实验模块");
        res.setUrl("/shiyan/**");
        res.setPermission("shiyan:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("期末理论测试成绩管理");
        res.setUrl("/lastTestScoreManage/**");
        res.setPermission("lastTestScoreManage:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("学生模块报告成绩管理");
        res.setUrl("/reportScoreManage/**");
        res.setPermission("reportScoreManage:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("学生模块测试成绩管理");
        res.setUrl("/testScoreManage/**");
        res.setPermission("testScoreManage:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("总成绩管理（当期）");
        res.setUrl("/totalscore/**");
        res.setPermission("totalscore:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("总成绩管理（往期）");
        res.setUrl("/passTotalscore/**");
        res.setPermission("passTotalscore:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("自定义报告评分");
        res.setUrl("/TreportGrade/**");
        res.setPermission("TreportGrade:*");
        resourceService.add(res);
    }


    @Test
    public void addRole() {
        Role r = new Role();
        r.setName("管理员");
        r.setSn("admin");
        roleService.add(r);
        r = new Role();
        r.setName("课程负责人");
        r.setSn("courseleader");
        roleService.add(r);
        r = new Role();
        r.setName("授课老师");
        r.setSn("teacher");
        roleService.add(r);
    }

    @Test
    public void testAddRoleRes() {
        roleService.addRoleResource(1, 2);
        roleService.addRoleResource(1, 3);
        roleService.addRoleResource(1, 4);
        roleService.addRoleResource(1, 5);
        roleService.addRoleResource(1, 6);
        roleService.addRoleResource(1, 7);
        roleService.addRoleResource(1, 8);
        roleService.addRoleResource(2, 2);
        roleService.addRoleResource(2, 9);
        roleService.addRoleResource(2, 10);
        roleService.addRoleResource(3, 2);
        roleService.addRoleResource(3, 11);
        roleService.addRoleResource(3, 12);
        roleService.addRoleResource(3, 13);
        roleService.addRoleResource(3, 14);
        roleService.addRoleResource(3, 15);
        roleService.addRoleResource(3, 16);
        roleService.addRoleResource(3, 17);
        roleService.addRoleResource(3, 18);
        roleService.addRoleResource(3, 19);
    }

    @Test
    public void testAddUserRole() {
        roleService.addUserRole(1, 1);
        roleService.addUserRole(2, 1);
    }
}