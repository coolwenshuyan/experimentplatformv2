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

        res = new Resource();
        res.setName("课程设置");
        res.setUrl("/courseinfo/list");
        res.setPermission("courseinfo:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("课程安排");
        res.setUrl("/arrangeclass/list");
        res.setPermission("arrangeclass:*");
        resourceService.add(res);

        res = new Resource();
        res.setName("考核模块设置");
        res.setUrl("/kaohemodel/checkModule");
        res.setPermission("kaohemodel:*");
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
        roleService.addRoleResource(1, 1);
    }

    @Test
    public void testAddUserRole() {
        roleService.addUserRole(1, 1);
        roleService.addUserRole(2, 1);
    }
}