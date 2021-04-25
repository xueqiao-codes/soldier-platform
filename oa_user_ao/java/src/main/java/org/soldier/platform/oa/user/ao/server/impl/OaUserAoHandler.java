package org.soldier.platform.oa.user.ao.server.impl;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.falcon.api.data.resp.LoginResp;
import org.soldier.platform.falcon.api.method.LoginMethod;
import org.soldier.platform.oa.session.dao.TSession;
import org.soldier.platform.oa.session.dao.client.OaSessionDaoAsyncStub;
import org.soldier.platform.oa.session.dao.client.OaSessionDaoStub;
import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.OaUserPage;
import org.soldier.platform.oa.user.QueryUserOption;
import org.soldier.platform.oa.user.oa_userConstants;
import org.soldier.platform.oa.user.ao.ECheckResult;
import org.soldier.platform.oa.user.ao.LoginResult;
import org.soldier.platform.oa.user.ao.OaUserErrorCode;
import org.soldier.platform.oa.user.ao.server.OaUserAoAdaptor;
import org.soldier.platform.oa.user.ao.server.core.AoSession;
import org.soldier.platform.oa.user.ao.server.core.IOaUserChecker;
import org.soldier.platform.oa.user.ao.server.core.OaUserCheckerByDao;
import org.soldier.platform.oa.user.ao.server.core.OaUserCheckerByLdap;
import org.soldier.platform.oa.user.ao.server.core.OpenFalconUser;
import org.soldier.platform.oa.user.dao.client.OaUserDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.antiy.error_code.ErrorCodeInner;

public class OaUserAoHandler extends OaUserAoAdaptor {
	private ThreadPoolExecutor asyncTaskPool = new ThreadPoolExecutor(4, 16, 60
			, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(60)
			, new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread taskThread = new Thread(r);
                    taskThread.setDaemon(true);
                    taskThread.setName("AsyncTask-" + taskThread.getId());
                    return taskThread;
                }
            }, new ThreadPoolExecutor.AbortPolicy());
	
	@Override
	public int InitApp(Properties props) {
		return 0;
	}
	
	@Override
    public void destroy() {
    }

	
	public boolean isLdapEnabled() {
	    return "true".equalsIgnoreCase(System.getenv("ENABLE_LDAP"));
	}
	
	public boolean isOpenFalconDisabled() {
	    return "true".equalsIgnoreCase(System.getenv("DISABLE_OPENFALCON"));
	}
	
	private IOaUserChecker createChecker() {
        if (isLdapEnabled()) {
            return new OaUserCheckerByLdap();
        } 
        return new OaUserCheckerByDao();
	}
	
	@Override
	protected LoginResult login(TServiceCntl oCntl, String userName, String passwordMd5)
			throws ErrorInfo, TException {
	    IOaUserChecker checker = createChecker();
	            
	    OaUser user = checker.checkUser(userName, passwordMd5);
		AoSession aoSession = new AoSession(userName);
		
		OaSessionDaoStub sessionStub = new OaSessionDaoStub();
		TSession daoSession  = new TSession();
		daoSession.setUserId(user.getUserId());
		daoSession.setSessionKey(aoSession.getSessionKey());
		daoSession.setUserName(userName);
		sessionStub.updateSession(user.getUserId(), 1500, daoSession);
		
		LoginResult result = new LoginResult();
		result.setUserId(user.getUserId());
		result.setUserName(userName);
		result.setSecretKey(aoSession.getSecretKey());
		
		if (!isLdapEnabled() && !isOpenFalconDisabled()) {
	         // 尝试登陆OpenFalcon，登陆不成功则无视
		    LoginMethod loginMethod = new LoginMethod();
		    loginMethod.getReq().setName(userName);
		    loginMethod.getReq().setPassword(userName);
		    loginMethod.setTimeout(1000);
		    
		    LoginResp loginResp = null;
		    try {
		        loginResp = loginMethod.call(LoginResp.class);
		        if (loginResp.getHttpStatusCode() == 200) {
		            aoSession.setFalconSig(loginResp.getSig());
		        } else if (loginResp.getHttpStatusCode() >= 400 && loginResp.getHttpStatusCode() < 500) {
		            asyncTaskPool.execute(new Runnable() {
		                @Override
		                public void run() {
		                    try {
		                        OpenFalconUser.repairUser(user);
		                    } catch (Throwable e) {
		                        AppLog.e(e.getMessage(), e);
		                    }
		                }
		            });
		        }
		    } catch (Throwable e) {
		        AppLog.e(e.getMessage(), e);
		    }
	            
		    if (loginResp != null) {
		        result.setFalconSig(loginResp.getSig());
		    }
		}
		
		return result;
	}
	
	private void checkUserName(OaUser user) throws ErrorInfo {
		if (!user.isSetUserName() || user.getUserName().trim().isEmpty()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userName should be set and not be empty!");
		}
		user.setUserName(user.getUserName().trim());
		if (user.getUserName().length() > oa_userConstants.MAX_USER_NAME_LENGTH) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userName length should not > " + oa_userConstants.MAX_USER_NAME_LENGTH);
		}
	}
	
	private void checkUserPassword(OaUser user) throws ErrorInfo {
		if (!user.isSetUserPassword()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userPassword should be set!");
		} else {
			user.setUserPassword(user.getUserPassword().trim());
			if (user.getUserPassword().isEmpty()) {
				throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
						"userPassworld should not be empty!");
			}
			if (user.getUserPassword().length() > oa_userConstants.MAX_PASSWORD_LENGTH) {
				throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
						"userPassword length should not > " + oa_userConstants.MAX_PASSWORD_LENGTH);
			}
		}
	}
	
	private void checkUserNickName(OaUser user) throws ErrorInfo {
		if (user.isSetUserNickName()) {
			user.setUserNickName(user.getUserNickName().trim());
			
			if (user.getUserNickName().length() > oa_userConstants.MAX_NICK_NAME_LENGTH) {
				throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
						"userNickName length should not > " + oa_userConstants.MAX_NICK_NAME_LENGTH);
			}
		}
	}

	@Override
	protected int registerUser(TServiceCntl oCntl, OaUser user, String operationUserName)
			throws ErrorInfo, TException {
		checkUserName(user);
		checkUserPassword(user);
		checkUserNickName(user);
		
		OaUserDaoStub userDaoStub = new OaUserDaoStub();
		QueryUserOption option = new QueryUserOption();
		option.setUserName(user.getUserName());
		
		List<OaUser> userList = userDaoStub.queryUser(user.getUserName().hashCode(), 1500, option);
		if (!userList.isEmpty()) {
			throw new ErrorInfo(OaUserErrorCode.UserNameAlreadyExist.getValue(),
					"user already exist!");
		}
		
		return userDaoStub.addUser(user.getUserName().hashCode(), 1500, user);
	}

	@Override
	protected boolean checkSession(TServiceCntl oCntl, int userId, String userName, String secretKey)
			throws ErrorInfo, TException {
		OaSessionDaoStub sessionStub = new OaSessionDaoStub();
		
		List<TSession> sessionList = sessionStub.getSession(userId, 1000, userId, userName);
		if (sessionList.isEmpty()) {
			return false;
		}
		final AoSession aoSession = AoSession.fromSessionKey(sessionList.get(0).getSessionKey());
		if (aoSession == null) {
			return false;
		}
		
		boolean result = aoSession.getSecretKey().equals(secretKey) && aoSession.getUserName().equals(userName);
		if (result) {
			new OaSessionDaoAsyncStub().send_updateSession(RandomUtils.nextInt(), 1000, 
					new TSession().setUserId(userId).setSessionKey(aoSession.getSessionKey()));
			
		}
		return result;
	}

	@Override
	protected void updateUser(TServiceCntl oCntl, OaUser user) throws ErrorInfo, TException {
		if (!user.isSetUserId() || user.getUserId() <= 0) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userId should set and not <= 0");
		}
		if (user.isSetUserPassword()) {
			checkUserPassword(user);
		}
		if (user.isSetUserNickName()) {
			checkUserNickName(user);
		}
		
		try {
			new OaUserDaoStub().updateUser(user.getUserId(), 1500, user);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				throw new ErrorInfo(OaUserErrorCode.UserNotExist.getValue(),
						"user is not exist!");
			}
			throw err;
		}
	}

	@Override
	protected void deleteUser(TServiceCntl oCntl, int userId, String operationUserName) throws ErrorInfo, TException {
		new OaUserDaoStub().deleteUser(userId, 1500, userId);
	}

	@Override
	protected void logout(TServiceCntl oCntl, int userId, String userName)
			throws ErrorInfo, TException {
		new OaSessionDaoStub().deleteSession(RandomUtils.nextInt(), 1500, userId, userName);
	}

	@Override
	protected List<OaUser> queryUser(TServiceCntl oCntl, QueryUserOption option)
			throws ErrorInfo, TException {
		if (!option.isSetUserId() && !option.isSetUserName()) {
			throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(),
					"userId or userName must be set one for option");
		}
		
		return new OaUserDaoStub().queryUser(RandomUtils.nextInt(), 1500, option);
	}

	@Override
	protected OaUserPage queryUserByPage(TServiceCntl oCntl,
			QueryUserOption option, int pageIndex, int pageSize)
			throws ErrorInfo, TException {
		return new OaUserDaoStub().queryUserByPage(
				RandomUtils.nextInt(), 3000, option, pageIndex, pageSize);
	}
	
	
    @Override
    protected ECheckResult checkSessionAndGroups(TServiceCntl oCntl
            , int userId, String userName, String secretKey,
            Set<String> groups) throws ErrorInfo, TException {
        if (!checkSession(oCntl, userId, userName, secretKey)) {
            return ECheckResult.SESSION_INVALID;
        }
        
        if (groups == null || groups.isEmpty()) {
            return ECheckResult.OK;
        }
        
        IOaUserChecker checker = createChecker();
        if (!checker.checkInGroups(userId, userName, groups)) {
            return ECheckResult.GROUP_INVALID;
        }
        return ECheckResult.OK;
        
    }
}
