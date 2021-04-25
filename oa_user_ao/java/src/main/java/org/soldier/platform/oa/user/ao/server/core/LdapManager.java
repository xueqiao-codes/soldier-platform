package org.soldier.platform.oa.user.ao.server.core;

import java.util.HashMap;
import java.util.Map;

import org.soldier.base.logger.AppLog;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.StringUtils;

public class LdapManager {
    private static LdapManager sInstance;
    public static LdapManager Global() {
        if (sInstance == null) {
            synchronized(LdapManager.class) {
                if (sInstance == null) {
                    sInstance = new LdapManager();
                }
            }
        }
        return sInstance;
    }
    
    private ThreadLocal<LdapTemplate> ldapTemplate = new ThreadLocal<LdapTemplate>();
    
    private String host;
    private String port;
    private boolean isSecure;
    private String baseDc;
    
    private String userDn;
    private String userPasswd;
    private String groupDn;
    
    private String userNameAttribute;
    private String groupNameAttribute;
    
    private StringBuffer ldapUrl = new StringBuffer(64);
    
    private String getEnvDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
    
    private LdapManager() {
        host = getEnvDefault("LDAP_HOST", "ldap.soldier-tools.svc");
        port = getEnvDefault("LDAP_PORT", "389");
        isSecure = "true".equals(System.getenv("LDAP_SECURE"));
        baseDc = getEnvDefault("LDAP_BASEDC", "ou=People,dc=xueqiao,dc=com");
        userNameAttribute = getEnvDefault("LDAP_USERNAME_ATTRIBUTE", "cn");
        userDn = getEnvDefault("LDAP_USERDN", "cn=reader,dc=xueqiao,dc=com");
        userPasswd = getEnvDefault("LDAP_USERPASSWD", "123456");
        groupDn = getEnvDefault("LDAP_GROUPDN", "ou=oa,ou=People,dc=xueqiao,dc=com");
        groupNameAttribute = getEnvDefault("LDAP_GROUPNAME_ATTRIBUTE", "cn");
        
        if (this.isSecure) {
            ldapUrl.append("ldaps://");
        } else {
            ldapUrl.append("ldap://");
        }
        ldapUrl.append(host).append(":").append(port);
        
        AppLog.d("ldapUrl=" + ldapUrl.toString() + ", baseDc=" + baseDc
                + ", userDn=" + userDn 
                + ", userNameAttribute=" + userNameAttribute);
    }
    
    public String getUserNameAttribute() {
        return userNameAttribute;
    }
    
    public String getBaseDc() {
        return baseDc;
    }
    
    public String getGroupDn() {
        return groupDn;
    }
    
    public String getGroupNameAttribute() {
        return groupNameAttribute;
    }
    
    private LdapContextSource createContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap<>();

        contextSource.setUrl(ldapUrl.toString());
        contextSource.setBase(baseDc);
        contextSource.setUserDn(userDn);
        contextSource.setPassword(userPasswd);
        contextSource.afterPropertiesSet();

        //  解决 乱码 的关键一句
        config.put("java.naming.ldap.attributes.binary", "objectGUID");

        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(config);
        
        return contextSource;
    }
    
    public LdapTemplate getTemplate() {
        if (ldapTemplate.get() == null) {
            ldapTemplate.set(new LdapTemplate(createContextSource()));
        }
        
        return ldapTemplate.get();
    }
    
    public LdapUser getUser(String userName, String[] needAttributes, LdapUser.MemberOfFilter filter) {
        try {
            return getTemplate().lookup(userNameAttribute + "=" + userName
                , needAttributes
                , new LdapUser.LdapUserAttributeMapper(filter));
        } catch (NameNotFoundException e) {
            AppLog.i(e.getMessage());
            return null;
        }
    }
    
   public LdapGroup getGroup(String groupName) {
       StringBuilder groupFilterBuilder = new StringBuilder();
       groupFilterBuilder.append(groupNameAttribute).append("=").append(groupName)
                  .append(",").append(groupDn);
       
       String groupFilter = groupFilterBuilder.toString().substring(0
               , groupFilterBuilder.length() - baseDc.length() - 1);
       
       try {
           return getTemplate().lookup(groupFilter
               , LdapGroup.NEED_ATTRIBUTES
               , new LdapGroup.LdapGroupAttributeMapper());
       } catch (NameNotFoundException e) {
           AppLog.i(e.getMessage());
           return null;
       }
   }
}
