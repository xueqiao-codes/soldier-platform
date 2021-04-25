package org.soldier.platform.dal_set;

import org.soldier.base.Assert;

/**
 *  表信息描述
 * @author Xairy
 */
public class TableInfo {
	private String namePrefix;  // 表名前缀
	private int sliceNum;        // 分表数
	private boolean fillZero; // 是否补丁， 例如1024个分表，补齐0，表1则后缀为0001
	
	public TableInfo(){
		this.namePrefix = "";
		this.sliceNum = 1;
		this.fillZero = false;
	}
	
	public final String getNamePrefix(){
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		Assert.True(namePrefix != null && !namePrefix.isEmpty());
		this.namePrefix = namePrefix;
	}

	public int getSliceNum() {
		return sliceNum;
	}

	public void setSliceNum(int sliceNum) {
		Assert.True(sliceNum >= 0);
		this.sliceNum = sliceNum;
	}

	public boolean isFillZero() {
		return fillZero;
	}

	public void setFillZero(boolean fillZero) {
		this.fillZero = fillZero;
	}
	
	public final String GetTableName(final long key){
		if(this.sliceNum == 0){
			return this.namePrefix;
		}
		
		StringBuffer buffer = new StringBuffer(namePrefix.length() + 12);
		buffer.append(this.namePrefix);
		int tableIndex = (int)(key % sliceNum);
		int zeroNum = 0;
		if(fillZero){
			// 对比
			int _sliceNum = this.sliceNum;
			int _tableIndex = tableIndex;
			if(_tableIndex == 0){
				--zeroNum;
			}
			while(_tableIndex > 0){
				_sliceNum /= 10;
				_tableIndex /= 10;
			}
			// 算多出0的个数
			while(_sliceNum > 0){
				_sliceNum /= 10;
				++zeroNum;
			}
		}
		for(int index = 0; index < zeroNum; ++index){
			buffer.append('0');
		}
		buffer.append(tableIndex);
		return buffer.toString();
	}
}
