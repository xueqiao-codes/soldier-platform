package org.soldier.base.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.soldier.base.Assert;



public class PreparedFields {
	private StringBuffer buffer;
	private boolean needDot;
	private int size;
	private List<Object> parameters;
	
	public PreparedFields() {
		buffer = new StringBuffer(128);
		needDot = false;
		parameters = new ArrayList<Object>(5);
	}
	
	public void addInt(String field, int value) {
		appendField(field);
		parameters.add(value);
	}
	
	public void addLong(String field, long value) {
		appendField(field);
		parameters.add(value);
	}
	
	public void addString(String field, String value) {
		appendField(field);
		parameters.add(value);
	}
	
	public void addShort(String field, short value) {
		appendField(field);
		parameters.add(value);
	}
	
	public void addByte(String field, byte value) {
	    appendField(field);
	    parameters.add(value);
	}
	
	public void addArray(String field, Array array) {
		appendField(field);
		parameters.add(array);
	}
	
	public void addDate(String field, Date date) {
		appendField(field);
		parameters.add(date);
	}
	
	public void addTimestamp(String field, Timestamp timestamp) {
		appendField(field);
		parameters.add(timestamp);
	}
	
	public void addDouble(String field, double value){
		appendField(field);
		parameters.add(value);
	}
	
	public void addFieldExpression(String fieldExpression) {
	    if (needDot) {
	        buffer.append(",");
	    } else {
	        needDot = true;
	    }
	    buffer.append(fieldExpression);
	}

	public void addBlob(String field, Blob blob){
		appendField(field);
		parameters.add(blob);
	}
	
	public int getSize() {
		return parameters.size();
	}
	
	public Object[] getParameters() {
		Assert.True(size == parameters.size());
		if (0 == size) {
			return null;
		}
		Object[] result = new Object[size];
		for (int index = 0; index < size; ++index) {
			result[index] = parameters.get(index);
		}
		return result;
	}
	
	public String getPreparedSql() {
		return buffer.toString();
	}
	
	private void appendField(String field) {
		if (needDot) {
			buffer.append(",");
		} else {
			needDot = true;
		}
		buffer.append(field);
		buffer.append("=?");
		++size;
	}
}
