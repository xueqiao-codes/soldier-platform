package org.soldier.platform.oa.user.ao.server.core;

import java.util.Set;

import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public interface IOaUserChecker {
    /**
     * @param userName
     * @param passwordMd5
     * @return 用户基础信息
     */
    OaUser checkUser(String userName, String passwordMd5) throws ErrorInfo;
    
    boolean checkInGroups(int userId, String userName, Set<String> groupNames) throws ErrorInfo;
}
