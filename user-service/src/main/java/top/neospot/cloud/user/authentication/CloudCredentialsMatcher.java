package top.neospot.cloud.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;
import top.neospot.cloud.user.entity.UserInfo;

import java.util.Date;

@Slf4j
public class CloudCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        PrincipalCollection principals = authenticationInfo.getPrincipals();
        CloudToken credentials = (CloudToken) authenticationInfo.getCredentials();

        UserInfo cloudToken = (UserInfo) principals.getPrimaryPrincipal();

        return credentials.getExpiredAt().compareTo(new Date()) > 0;
    }

}
