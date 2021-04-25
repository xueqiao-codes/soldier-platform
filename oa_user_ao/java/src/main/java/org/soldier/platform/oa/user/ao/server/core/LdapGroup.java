package org.soldier.platform.oa.user.ao.server.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class LdapGroup {
    private String cn;
    private Set<String> members = new HashSet<>();
    
    public String getCn() {
        return cn;
    }
    public void setCn(String cn) {
        this.cn = cn;
    }
    
    public void addMember(String member) {
        this.members.add(member);
    }
    
    public boolean hasMember(String member) {
        return this.members.contains(member);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("LdapGroup(cn=").append(cn).append(", members=[");
        Iterator<String> it = members.iterator();
        while(it.hasNext()) {
            builder.append(" ").append(it.next());
        }
        builder.append("])");
        return builder.toString();
    }
    
    public static String[] NEED_ATTRIBUTES = new String[] {
            "cn", "member"
    };
    
    public static class LdapGroupAttributeMapper implements AttributesMapper<LdapGroup> {

        @Override
        public LdapGroup mapFromAttributes(Attributes attributes) throws NamingException {
            LdapGroup group = new LdapGroup();
            group.setCn(attributes.get("cn").get().toString());
            
            Attribute memberAttr = attributes.get("member");
            if (memberAttr != null) {
                NamingEnumeration<?> it = memberAttr.getAll();
                while(it.hasMore()) {
                    group.addMember(it.next().toString());
                }
            }
            
            return group;
        }
        
    }
}
