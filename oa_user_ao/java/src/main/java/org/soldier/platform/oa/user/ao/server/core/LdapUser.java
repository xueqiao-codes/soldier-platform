package org.soldier.platform.oa.user.ao.server.core;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.codec.binary.Base64;
import org.soldier.base.logger.AppLog;
import org.springframework.ldap.core.AttributesMapper;

import com.buck.common.codec.Codec;

public class LdapUser {
    private String cn;
    private String userPasswdMd5 = "";
    private Set<String> memberOf = new HashSet<>();
    
    public String getCn() {
        return cn;
    }
    
    public void setCn(String cn) {
        this.cn = cn;
    }
    
    public String getUserPasswdMd5() {
        return userPasswdMd5;
    }

    public void setUserPasswdMd5(String userPasswdMd5) {
        this.userPasswdMd5 = userPasswdMd5;
    }
    
    public static final String[] NEED_MEMBEROF_ATTRIBUTES = new String[] {
            "cn", "memberOf"
    };
    
    public static final String[] NEED_PASSWD_ATTRIBUTES = new String[] {
            "cn", "userPassword"
    };
     
    public void addMemberOf(String group) {
        memberOf.add(group);
    }
    
    public boolean isMemberOf(String group) {
        return memberOf.contains(group);
    }
    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("LdapUser{cn=").append(cn).append(", memberOf=[");
        Iterator<String> it = memberOf.iterator();
        while(it.hasNext()) {
            builder.append(" ").append(it.next());
        }
        builder.append("], userPasswdMd5=").append(userPasswdMd5);
        builder.append("}");
        return builder.toString();
    }
    
    public static interface MemberOfFilter {
        public boolean accept(String group);
    }
    
    public static class LdapUserAttributeMapper implements AttributesMapper<LdapUser> {
        private static final String MD5_PREFIX = "{MD5}";
        
        private MemberOfFilter filter;
        public LdapUserAttributeMapper() {
        }
        
        public LdapUserAttributeMapper(MemberOfFilter filter) {
            this.filter = filter;
        }
        
        @Override
        public LdapUser mapFromAttributes(Attributes attributes) throws NamingException {
            LdapUser ldapUser = new LdapUser();
            ldapUser.setCn(attributes.get("cn").get().toString());
            
            Attribute memberOfAttr = attributes.get("memberOf");
            if (memberOfAttr != null) {
                NamingEnumeration<?> it = memberOfAttr.getAll();
                while(it.hasMore()) {
                    String group = it.next().toString();
                    if (filter != null) {
                        if (filter.accept(group)) {
                            ldapUser.addMemberOf(group);
                        }
                    } else {
                        ldapUser.addMemberOf(group);
                    }
                }
            }
            
            Attribute passwdAttr = attributes.get("userPassword");
            if (passwdAttr != null) {
                String passwd = new String((byte[])passwdAttr.get());
                if (!passwd.startsWith(MD5_PREFIX)) {
                    AppLog.w("password for " + ldapUser.getCn() + " is not md5, should modify this to md5");
                    ldapUser.setUserPasswdMd5("");
                } else {
                    try {
                        ldapUser.setUserPasswdMd5(new String(
                            Codec.forName("Base16").newEncoder().encode(Base64.decodeBase64(passwd.substring(MD5_PREFIX.length())))
                                    , "US-ASCII"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } 
            
            return ldapUser;
        }
        
    }

   
}
