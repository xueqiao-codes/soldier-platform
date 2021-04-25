package org.soldier.platform.dal_set;

public class VariableFactory {
	private static VariableFactory sInstance;
	
	public static VariableFactory getInstance() {
		if (sInstance == null) {
			synchronized(VariableFactory.class) {
				if (sInstance == null) {
					sInstance = new VariableFactory();
				}
			}
		}
		return sInstance;
	}
	
	private static class ThrowVariableProvider implements IVariableProvider {
		@Override
		public String getVariable(String variableName) throws VariableException {
			throw new VariableException("IVariableProvider is not valid!");
		}
		
	}
	
	private IVariableProvider provider = new ThrowVariableProvider();
	
	public IVariableProvider getProvider() {
		return provider;
	}
	
	public void setProvider(IVariableProvider provider) {
		this.provider = provider;
	}
}
