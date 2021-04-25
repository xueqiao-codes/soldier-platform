package org.soldier.framework.message_bus.api;

public interface IGuardPolicy {
	public static enum GuardPolicyType {
		POLICY_TIMEOUT
	}
	
	public GuardPolicyType getPolicyType();
}
