package org.soldier.platform.dal_set;

public interface IVariableProvider {
	public String getVariable(String variableName) throws VariableException; 
}
