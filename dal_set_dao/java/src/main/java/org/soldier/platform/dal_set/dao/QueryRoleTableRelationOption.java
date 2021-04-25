/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.dal_set.dao;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryRoleTableRelationOption implements org.apache.thrift.TBase<QueryRoleTableRelationOption, QueryRoleTableRelationOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryRoleTableRelationOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryRoleTableRelationOption");

  private static final org.apache.thrift.protocol.TField ROLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("roleName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TABLE_PREFIX_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tablePrefixName", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryRoleTableRelationOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryRoleTableRelationOptionTupleSchemeFactory());
  }

  public String roleName; // optional
  public String tablePrefixName; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROLE_NAME((short)1, "roleName"),
    TABLE_PREFIX_NAME((short)2, "tablePrefixName");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ROLE_NAME
          return ROLE_NAME;
        case 2: // TABLE_PREFIX_NAME
          return TABLE_PREFIX_NAME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private _Fields optionals[] = {_Fields.ROLE_NAME,_Fields.TABLE_PREFIX_NAME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ROLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("roleName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_PREFIX_NAME, new org.apache.thrift.meta_data.FieldMetaData("tablePrefixName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryRoleTableRelationOption.class, metaDataMap);
  }

  public QueryRoleTableRelationOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryRoleTableRelationOption(QueryRoleTableRelationOption other) {
    if (other.isSetRoleName()) {
      this.roleName = other.roleName;
    }
    if (other.isSetTablePrefixName()) {
      this.tablePrefixName = other.tablePrefixName;
    }
  }

  public QueryRoleTableRelationOption deepCopy() {
    return new QueryRoleTableRelationOption(this);
  }

  @Override
  public void clear() {
    this.roleName = null;
    this.tablePrefixName = null;
  }

  public String getRoleName() {
    return this.roleName;
  }

  public QueryRoleTableRelationOption setRoleName(String roleName) {
    this.roleName = roleName;
    return this;
  }

  public void unsetRoleName() {
    this.roleName = null;
  }

  /** Returns true if field roleName is set (has been assigned a value) and false otherwise */
  public boolean isSetRoleName() {
    return this.roleName != null;
  }

  public void setRoleNameIsSet(boolean value) {
    if (!value) {
      this.roleName = null;
    }
  }

  public String getTablePrefixName() {
    return this.tablePrefixName;
  }

  public QueryRoleTableRelationOption setTablePrefixName(String tablePrefixName) {
    this.tablePrefixName = tablePrefixName;
    return this;
  }

  public void unsetTablePrefixName() {
    this.tablePrefixName = null;
  }

  /** Returns true if field tablePrefixName is set (has been assigned a value) and false otherwise */
  public boolean isSetTablePrefixName() {
    return this.tablePrefixName != null;
  }

  public void setTablePrefixNameIsSet(boolean value) {
    if (!value) {
      this.tablePrefixName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ROLE_NAME:
      if (value == null) {
        unsetRoleName();
      } else {
        setRoleName((String)value);
      }
      break;

    case TABLE_PREFIX_NAME:
      if (value == null) {
        unsetTablePrefixName();
      } else {
        setTablePrefixName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ROLE_NAME:
      return getRoleName();

    case TABLE_PREFIX_NAME:
      return getTablePrefixName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ROLE_NAME:
      return isSetRoleName();
    case TABLE_PREFIX_NAME:
      return isSetTablePrefixName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryRoleTableRelationOption)
      return this.equals((QueryRoleTableRelationOption)that);
    return false;
  }

  public boolean equals(QueryRoleTableRelationOption that) {
    if (that == null)
      return false;

    boolean this_present_roleName = true && this.isSetRoleName();
    boolean that_present_roleName = true && that.isSetRoleName();
    if (this_present_roleName || that_present_roleName) {
      if (!(this_present_roleName && that_present_roleName))
        return false;
      if (!this.roleName.equals(that.roleName))
        return false;
    }

    boolean this_present_tablePrefixName = true && this.isSetTablePrefixName();
    boolean that_present_tablePrefixName = true && that.isSetTablePrefixName();
    if (this_present_tablePrefixName || that_present_tablePrefixName) {
      if (!(this_present_tablePrefixName && that_present_tablePrefixName))
        return false;
      if (!this.tablePrefixName.equals(that.tablePrefixName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryRoleTableRelationOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRoleName()).compareTo(other.isSetRoleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roleName, other.roleName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTablePrefixName()).compareTo(other.isSetTablePrefixName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTablePrefixName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tablePrefixName, other.tablePrefixName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("QueryRoleTableRelationOption(");
    boolean first = true;

    if (isSetRoleName()) {
      sb.append("roleName:");
      if (this.roleName == null) {
        sb.append("null");
      } else {
        sb.append(this.roleName);
      }
      first = false;
    }
    if (isSetTablePrefixName()) {
      if (!first) sb.append(", ");
      sb.append("tablePrefixName:");
      if (this.tablePrefixName == null) {
        sb.append("null");
      } else {
        sb.append(this.tablePrefixName);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class QueryRoleTableRelationOptionStandardSchemeFactory implements SchemeFactory {
    public QueryRoleTableRelationOptionStandardScheme getScheme() {
      return new QueryRoleTableRelationOptionStandardScheme();
    }
  }

  private static class QueryRoleTableRelationOptionStandardScheme extends StandardScheme<QueryRoleTableRelationOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryRoleTableRelationOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ROLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.roleName = iprot.readString();
              struct.setRoleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TABLE_PREFIX_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tablePrefixName = iprot.readString();
              struct.setTablePrefixNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryRoleTableRelationOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.roleName != null) {
        if (struct.isSetRoleName()) {
          oprot.writeFieldBegin(ROLE_NAME_FIELD_DESC);
          oprot.writeString(struct.roleName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.tablePrefixName != null) {
        if (struct.isSetTablePrefixName()) {
          oprot.writeFieldBegin(TABLE_PREFIX_NAME_FIELD_DESC);
          oprot.writeString(struct.tablePrefixName);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryRoleTableRelationOptionTupleSchemeFactory implements SchemeFactory {
    public QueryRoleTableRelationOptionTupleScheme getScheme() {
      return new QueryRoleTableRelationOptionTupleScheme();
    }
  }

  private static class QueryRoleTableRelationOptionTupleScheme extends TupleScheme<QueryRoleTableRelationOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryRoleTableRelationOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRoleName()) {
        optionals.set(0);
      }
      if (struct.isSetTablePrefixName()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRoleName()) {
        oprot.writeString(struct.roleName);
      }
      if (struct.isSetTablePrefixName()) {
        oprot.writeString(struct.tablePrefixName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryRoleTableRelationOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.roleName = iprot.readString();
        struct.setRoleNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.tablePrefixName = iprot.readString();
        struct.setTablePrefixNameIsSet(true);
      }
    }
  }

}

