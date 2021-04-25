package org.soldier.framework.message_bus.api;

public class TimeoutGuardPolicy implements IGuardPolicy {
	private int timeoutSeconds = 3;
	
	@Override
	public GuardPolicyType getPolicyType() {
		return GuardPolicyType.POLICY_TIMEOUT;
	}

	public int getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public TimeoutGuardPolicy setTimeoutSeconds(int timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
		return this;
	}
	
	@Override
	public String toString() {
		return "TimeoutGuardPolicy(" + timeoutSeconds + ")";
	}

}
