package com.coolwen.experimentplatformv2;


import com.coolwen.experimentplatformv2.model.Docker;
import com.coolwen.experimentplatformv2.model.Resource;
import com.coolwen.experimentplatformv2.model.Role;
import com.coolwen.experimentplatformv2.model.User;
import com.coolwen.experimentplatformv2.service.DockerService;
import com.coolwen.experimentplatformv2.service.ResourceService;
import com.coolwen.experimentplatformv2.service.RoleService;
import com.coolwen.experimentplatformv2.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddDockerTest {

    @Autowired
    private DockerService dockerService;

    @Test
    public void addDocker1() {
        for (int i = 2000; i <= 2150; i++) {
            Docker docker = new Docker();
            docker.setDc_url("http://172.17.55.56:" + i + "/lab");
            dockerService.addDocker(docker);
        }
    }

    @Test
    public void addDocker2() {
        for (int i = 2000; i <= 2150; i++) {
            Docker docker = new Docker();
            docker.setDc_url("http://172.17.55.119:" + i + "/lab");
            dockerService.addDocker(docker);
        }
    }

    @Test
    public void addDocker3() {
        for (int i = 2000; i <= 2150; i++) {
            Docker docker = new Docker();
            docker.setDc_url("http://172.17.55.160:" + i + "/lab");
            dockerService.addDocker(docker);
        }
    }


}