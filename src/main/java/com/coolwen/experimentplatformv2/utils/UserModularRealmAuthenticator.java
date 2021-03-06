package com.coolwen.experimentplatformv2.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: experimentplatform
 * @Package: com.coolwen.experimentplatformv2.utils
 * @ClassName: UserModularRealmAuthenticator
 * @Author: Txc
 * @Description: 处理多个realm
 * @Date: 2020/5/15 0015 13:29
 * @Version: 1.0
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
    protected static final Logger logger = LoggerFactory.getLogger(UserModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.debug("UserModularRealmAuthenticator:method doAuthenticate() execute ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        LoginToken loginToken = (LoginToken) authenticationToken;
        // 登录类型
        String loginType = loginToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().toLowerCase().contains(loginType)) {
                typeRealms.add(realm);
            }
        }
        logger.debug(String.valueOf(typeRealms.size()));
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1) {
            logger.debug("doSingleRealmAuthentication() execute ");
            return doSingleRealmAuthentication(typeRealms.get(0), loginToken);
        } else {
            logger.debug("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, loginToken);
        }
    }


}
