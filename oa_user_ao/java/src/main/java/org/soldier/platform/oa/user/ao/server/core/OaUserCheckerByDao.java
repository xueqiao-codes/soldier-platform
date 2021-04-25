package org.soldier.platform.oa.user.ao.server.core;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.Md5;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.QueryUserOption;
import org.soldier.platform.oa.user.ao.OaUserErrorCode;
import org.soldier.platform.oa.user.dao.client.OaUserDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeInner;

public class OaUserCheckerByDao implements IOaUserChecker {

    @Override
    public OaUser checkUser(String userName, String passwordMd5) throws ErrorInfo  {
        QueryUserOption option = new QueryUserOption();
        option.setUserName(userName);

        List<OaUser> userList;
        try {
            userList = new OaUserDaoStub().queryUser(RandomUtils.nextInt(), 1500, option);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
                    , ErrorCodeInner.SERVER_INNER_ERROR.getErrorMsg());
        }
        
        if (userList == null || userList.isEmpty()) {
            throw new ErrorInfo(OaUserErrorCode.UserNameOrPasswordError.getValue(),
                    "user is not exist");
        }
        OaUser user = userList.get(0);
        if (!Md5.toMD5(user.getUserPassword()).equals(passwordMd5)) {
            throw new ErrorInfo(OaUserErrorCode.UserNameOrPasswordError.getValue(),
                    "user password is error");
        }     
        return user;
    }

    @Override
    public boolean checkInGroups(int userId, String userName, Set<String> groups) throws ErrorInfo {
        // 传统的用户dao暂时不支持oa用户组的配置
        return true;
    }
    
}
