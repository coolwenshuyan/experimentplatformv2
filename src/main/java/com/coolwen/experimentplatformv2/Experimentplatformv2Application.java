package com.coolwen.experimentplatformv2;

import com.coolwen.experimentplatformv2.config.ShiroConfig;
import com.coolwen.experimentplatformv2.dao.basedao.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.coolwen.experimentplatformv2"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
//@Import(ShiroCasConfiguration.class)//使用单点登陆配置，请打开
@Import(ShiroConfig.class)//不使用单点登陆配置，请打开
@EnableCaching
public class Experimentplatformv2Application {

    public static void main(String[] args) {
        SpringApplication.run(Experimentplatformv2Application.class, args);
    }

}
