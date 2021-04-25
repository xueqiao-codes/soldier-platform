package org.soldier.base.sql;

import java.util.ArrayList;
import java.util.List;

public class StackSqlQueryBuilder extends SqlQueryBuilder {
	private static abstract class ConditionNode {
		private ListConditionNode parent;
		private ConditionType conditionType;
		
		public ConditionNode(ListConditionNode parent, ConditionType conditionType) {
			this.parent = parent;
			this.conditionType = conditionType;
		}
		
		public abstract String toSql();
		public String getConditionType() {
			return conditionType.toString();
		}
		
		public ListConditionNode getParent() {
			return this.parent;
		}
	}
	
	private static class ListConditionNode extends ConditionNode {
		private List<ConditionNode> nodeList;
		
		public ListConditionNode(ListConditionNode parent, ConditionType conditionType) {
			super(parent, conditionType);
			nodeList = new ArrayList<ConditionNode>();
		}
		
		public void append(ConditionNode node) {
			nodeList.add(node);
		}

		@Override
		public String toSql() {
			StringBuffer buffer = new StringBuffer(128);
			int index = 0;
			for(ConditionNode node : nodeList) {
				if(index != 0) {
					buffer.append(" ");
					buffer.append(node.getConditionType());
					buffer.append(" ");
				}
				buffer.append("(");
				buffer.append(node.toSql());
				buffer.append(")");
				++index;
			}
			return buffer.toString();
		}
		
	}
	
	private static class LeafConditionNode extends ConditionNode {
		private String condition;
		
		public LeafConditionNode(ListConditionNode parent, ConditionPair pair) {
			super(parent, pair.getType());
			this.condition = pair.getCondition();
		}

		@Override
		public String toSql() {
			return condition;
		}
	}
	
	private ListConditionNode rootNode = new ListConditionNode(null, ConditionType.AND);
	private ListConditionNode currentNode = rootNode;
	
	@Override
	protected String buildConditionString() {
		String conditionSql = rootNode.toSql().trim();
		if (!conditionSql.isEmpty()) {
			StringBuffer conditionSqlBuffer = new StringBuffer(conditionSql.length() + 32);
			conditionSqlBuffer.append(" WHERE ");
			conditionSqlBuffer.append(conditionSql);
			return conditionSqlBuffer.toString();
		}
		return "";
	}
	
	@Override
	@Deprecated
	public void addCondition(ConditionType type, String condition) {
		currentNode.append(new LeafConditionNode(currentNode, new ConditionPair(type, condition)));
	}
	
	@Override
	public void addFieldCondition(ConditionType type,
			String preparedFieldCondition, Object... values) {
		currentNode.append(new LeafConditionNode(currentNode, new ConditionPair(type, preparedFieldCondition)));
		for (Object value : values) {
			parametersList.add(value);
		}
	}
	
	public void push(ConditionType type) {
		currentNode = new ListConditionNode(currentNode, type);
		currentNode.getParent().append(currentNode);
	}
	
	public void pop() {
		if (currentNode.getParent() != null) {
			currentNode = currentNode.getParent();
		}
	}
	
//	public static void main(String[] args) {
//		SqlQueryBuilder builder = new SqlQueryBuilder();
//		builder.addFields("Fname", "Fvalue");
//		builder.addTables("table_1");
//		builder.addCondition(ConditionType.AND, "Fname=1");
//		builder.addCondition(ConditionType.OR, "Fvalue=10");
//		builder.addCondition(ConditionType.OR, "Fvalue=10");
//		builder.setPage(0, 10);
//		builder.setOrder(OrderType.ASC, "Fname");
//		
//		System.out.println(builder.getItemsSql());
//		System.out.println(builder.getTotalCountSql());
		
//		builder.push(ConditionType.AND);
//		builder.addFieldCondition(ConditionType.AND, "Fname IN ?", new ArrayList<Long>());
//		
//		builder.push(ConditionType.AND);
//		builder.addFieldCondition(ConditionType.AND, "Fvalue IN ?", new ArrayList<Long>());
//		builder.addCondition(ConditionType.AND, "Fvalue=100");
//		builder.pop();
//		
//		builder.pop();
//		
//		builder.push(ConditionType.AND);
//		builder.addFieldCondition(ConditionType.OR, "Fvalue like ?", "hehe");
//		builder.addFieldCondition(ConditionType.OR, "Fvalue like ?", "hehe");
//		
//		System.out.println(builder.getItemsSql());
//	}
}
