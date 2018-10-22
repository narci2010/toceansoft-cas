package com.toceansoft.cas.shiro.client.demo.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import io.buji.pac4j.realm.Pac4jRealm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroPac4jRealm extends Pac4jRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		Object user = super.getAvailablePrincipal(principals);
		LOGGER.debug("登录用户：{}", user);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		List<String> permissions = new ArrayList<String>();
		permissions.add("user:info");
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}

}