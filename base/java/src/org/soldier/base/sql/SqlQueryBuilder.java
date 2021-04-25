package org.soldier.base.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.soldier.base.Assert;

/**
 * 辅助构建SQL
 * 
 * @author Xairy
 *
 */
public class SqlQueryBuilder {
	public static enum ConditionType {
		AND, OR
	}

	public static enum OrderType {
		DESC, ASC
	}

	protected static class ConditionPair {
		private ConditionType type;
		private String condition;

		public ConditionPair(final ConditionType type, final String condition) {
			this.type = type;
			this.condition = condition;
		}

		public ConditionType getType() {
			return type;
		}

		public String getCondition() {
			return condition;
		}
	}

	protected List<String> queryFields = new ArrayList<String>(10);
	protected List<String> queryTables = new ArrayList<String>(10);
	private List<ConditionPair> conditionFields = new ArrayList<ConditionPair>(3);
	protected int pageIndex = -1;
	protected int pageSize = 0;
	protected OrderType orderType;
	protected String orderKeyString;
	protected List<Object> parametersList;
	protected String groupByString;

	public SqlQueryBuilder() {
		parametersList = new ArrayList<Object>(5);
	}

	public void addFields(String... fields) {
		for (String field : fields) {
			Assert.True(field != null && field.length() > 0);
			queryFields.add(field);
		}
	}

	public void addTables(String... tables) {
		for (String table : tables) {
			Assert.True(table != null && table.length() > 0);
			queryTables.add(table);
		}
	}

	/**
	 * this is not safe for SQL Injection, Please Use addFieldCondition
	 * 
	 * @param type
	 * @param condition
	 */
	@Deprecated
	public void addCondition(ConditionType type, String condition) {
		Assert.True(type != null);
		Assert.True(condition != null && condition.length() > 0);
		conditionFields.add(new ConditionPair(type, condition));
	}

	public void addFieldCondition(ConditionType type, String preparedFieldCondition, Object... values) {
		Assert.True(type != null);
		Assert.True(preparedFieldCondition != null && preparedFieldCondition.length() > 0);
		conditionFields.add(new ConditionPair(type, preparedFieldCondition));
		for (Object value : values) {
			parametersList.add(value);
		}
	}

	public void setPage(final int pageIndex, final int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public void setOrder(final OrderType type, final String keyString) {
		this.orderType = type;
		this.orderKeyString = keyString;
	}

	public void setOrderChinese(final OrderType type, final String keyString) {
		this.orderType = type;
		this.orderKeyString = "convert(" + keyString + " using gbk)";
	}

	public void setGroup(final String keyString) {
		this.groupByString = keyString;
	}

	private String buildSelectString(boolean isSelectCount) {
		int index = 0;
		if (!queryFields.isEmpty()) {
			StringBuffer buffer = new StringBuffer(128);
			buffer.append("SELECT ");
			if (isSelectCount) {
				buffer.append("count(*)");
				return buffer.toString();
			}
			for (String field : queryFields) {
				if (0 == index) {
					buffer.append(" ");
				} else {
					buffer.append(",");
				}
				buffer.append(field);
				++index;
			}
			return buffer.toString();
		}
		return "";
	}

	protected String buildFromString() {
		int index = 0;
		if (!queryTables.isEmpty()) {
			StringBuffer buffer = new StringBuffer(128);
			buffer.append(" FROM ");
			for (String table : queryTables) {
				if (0 == index) {
					buffer.append(" ");
				} else {
					buffer.append(",");
				}
				buffer.append(table);
				++index;
			}
			return buffer.toString();
		}
		return "";
	}

	protected String buildConditionString() {
		int index = 0;
		if (!conditionFields.isEmpty()) {
			StringBuffer buffer = new StringBuffer(128);
			buffer.append(" WHERE");
			for (ConditionPair condition : conditionFields) {
				if (0 == index) {
					buffer.append(" ");
				} else {
					if (condition.getType() == ConditionType.AND) {
						buffer.append(" AND ");
					} else {
						buffer.append(" OR ");
					}
				}
				buffer.append("(");
				buffer.append(condition.getCondition());
				buffer.append(")");
				++index;
			}
			return buffer.toString();

		}
		return "";
	}

	protected String buildPageString() {
		if (pageIndex >= 0 && pageSize > 0) {
			StringBuffer buffer = new StringBuffer(32);
			buffer.append(" LIMIT ");
			buffer.append(pageIndex * pageSize);
			buffer.append(",");
			buffer.append(pageSize);
			return buffer.toString();
		}
		return "";
	}

	private String buildOrderString() {
		if (orderKeyString != null && orderKeyString.length() > 0 && orderType != null) {
			StringBuffer buffer = new StringBuffer(32);
			buffer.append(" ORDER BY ");
			buffer.append(orderKeyString);
			buffer.append(" ");
			buffer.append(orderType.name());
			return buffer.toString();
		}
		return "";
	}

	private String buildGroupString() {
		if (groupByString != null && groupByString.length() > 0) {
			StringBuffer buffer = new StringBuffer(32);
			buffer.append(" GROUP BY ");
			buffer.append(groupByString);
			return buffer.toString();
		}
		return "";
	}
	
	@Deprecated
	public void addMutilIdsFieldContion(ConditionType type, String field, List<Integer> ids) {
		StringBuffer condition = new StringBuffer(128);
		condition.append(field + " IN (");
		Iterator<Integer> iterator = ids.iterator();
		while (iterator.hasNext()) {
			condition.append("?");
			iterator.next();
			if (iterator.hasNext()) {
				condition.append(",");
			}
		}
		condition.append(")");
		addFieldCondition(type, condition.toString(), ids.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public void addInFieldCondition(ConditionType type, String field, Set inSets) {
		StringBuffer condition = new StringBuffer(128);
		condition.append(field + " IN (");
		Iterator it = inSets.iterator();
		while(it.hasNext()) {
			condition.append("?");
			parametersList.add(it.next());
			if (it.hasNext()) {
				condition.append(",");
			}
		}
		condition.append(")");
		addFieldCondition(type, condition.toString());
	}
	
	@SuppressWarnings("rawtypes")
	public void addNotInFieldCondition(ConditionType type, String field, Set notInSets) {
		StringBuffer condition = new StringBuffer(128);
		condition.append(field + " NOT IN (");
		Iterator it = notInSets.iterator();
		while(it.hasNext()) {
			condition.append("?");
			parametersList.add(it.next());
			if (it.hasNext()) {
				condition.append(",");
			}
		}
		condition.append(")");
		addFieldCondition(type, condition.toString());
	}

	public String getTotalCountSql() {
		StringBuffer buffer = new StringBuffer(128);
		buffer.append(buildSelectString(true));
		buffer.append(buildFromString());
		buffer.append(buildConditionString());
		return buffer.toString();
	}

	public String getItemsSql() {
		StringBuffer buffer = new StringBuffer(128);
		buffer.append(buildSelectString(false));
		buffer.append(buildFromString());
		buffer.append(buildConditionString());
		buffer.append(buildGroupString());
		buffer.append(buildOrderString());
		buffer.append(buildPageString());
		return buffer.toString();
	}

	public Object[] getParameterList() {
		if (0 == parametersList.size()) {
			return null;
		}
		Object[] resultList = new Object[parametersList.size()];
		for (int index = 0; index < parametersList.size(); ++index) {
			resultList[index] = parametersList.get(index);
		}
		return resultList;
	}
	
}
