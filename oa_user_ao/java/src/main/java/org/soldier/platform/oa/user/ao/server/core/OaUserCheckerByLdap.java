package org.soldier.platform.oa.user.ao.server.core;

import java.util.Set;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.ao.OaUserErrorCode;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public class OaUserCheckerByLdap implements IOaUserChecker {
    @Override
    public OaUser checkUser(String userName, String passwordMd5) throws ErrorInfo {
        LdapUser ldapUser = LdapManager.Global().getUser(userName, LdapUser.NEED_PASSWD_ATTRIBUTES, null);
        if (ldapUser == null) {
            throw new ErrorInfo(OaUserErrorCode.UserNameOrPasswordError.getValue(),
                    "user is not exist");
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("checkUser ldapUser=" + ldapUser);
        }
        if (!passwordMd5.equalsIgnoreCase(ldapUser.getUserPasswdMd5())) {
            throw new ErrorInfo(OaUserErrorCode.UserNameOrPasswordError.getValue(),
                    "user password is error");
        }     
        
        OaUser resultUser = new OaUser();
        resultUser.setUserId(1);
        resultUser.setUserName(userName);
        return resultUser;
    }

    @Override
    public boolean checkInGroups(int userId, String userName, Set<String> groupNames) throws ErrorInfo {
        LdapUser ldapUser = LdapManager.Global().getUser(userName
                , LdapUser.NEED_MEMBEROF_ATTRIBUTES
                , new LdapUser.MemberOfFilter() {
                    
                    @Override
                    public boolean accept(String group) {
                        return group.endsWith(LdapManager.Global().getGroupDn()) 
                                && group.startsWith(LdapManager.Global().getGroupNameAttribute());
                    }
                });
        if (ldapUser == null) {
            return false;
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("checkInGroups ldapUser=" + ldapUser);
        }
        
        for (String groupName : groupNames) {
            StringBuilder groupDnBuilder = new StringBuilder(64);
            groupDnBuilder.append(LdapManager.Global().getGroupNameAttribute()).append("=").append(groupName)
                          .append(",").append(LdapManager.Global().getGroupDn());
            
            if (ldapUser.isMemberOf(groupDnBuilder.toString())){
                return true;
            }
        }
        
        return false;
    }
    
}
