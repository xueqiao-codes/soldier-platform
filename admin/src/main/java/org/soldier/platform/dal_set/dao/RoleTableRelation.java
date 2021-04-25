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

public class RoleTableRelation implements org.apache.thrift.TBase<RoleTableRelation, RoleTableRelation._Fields>, java.io.Serializable, Cloneable, Comparable<RoleTableRelation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RoleTableRelation");

  private static final org.apache.thrift.protocol.TField ROLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("roleName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TABLE_PREFIX_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tablePrefixName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RoleTableRelationStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RoleTableRelationTupleSchemeFactory());
  }

  public String roleName; // required
  public String tablePrefixName; // required
  public int createTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROLE_NAME((short)1, "roleName"),
    TABLE_PREFIX_NAME((short)2, "tablePrefixName"),
    CREATE_TIMESTAMP((short)3, "createTimestamp");

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
        case 3: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
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
  private static final int __CREATETIMESTAMP_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.CREATE_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ROLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("roleName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_PREFIX_NAME, new org.apache.thrift.meta_data.FieldMetaData("tablePrefixName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RoleTableRelation.class, metaDataMap);
  }

  public RoleTableRelation() {
  }

  public RoleTableRelation(
    String roleName,
    String tablePrefixName)
  {
    this();
    this.roleName = roleName;
    this.tablePrefixName = tablePrefixName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RoleTableRelation(RoleTableRelation other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetRoleName()) {
      this.roleName = other.roleName;
    }
    if (other.isSetTablePrefixName()) {
      this.tablePrefixName = other.tablePrefixName;
    }
    this.createTimestamp = other.createTimestamp;
  }

  public RoleTableRelation deepCopy() {
    return new RoleTableRelation(this);
  }

  @Override
  public void clear() {
    this.roleName = null;
    this.tablePrefixName = null;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
  }

  public String getRoleName() {
    return this.roleName;
  }

  public RoleTableRelation setRoleName(String roleName) {
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

  public RoleTableRelation setTablePrefixName(String tablePrefixName) {
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

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public RoleTableRelation setCreateTimestamp(int createTimestamp) {
    this.createTimestamp = createTimestamp;
    setCreateTimestampIsSet(true);
    return this;
  }

  public void unsetCreateTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID);
  }

  /** Returns true if field createTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID);
  }

  public void setCreateTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIMESTAMP_ISSET_ID, value);
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

    case CREATE_TIMESTAMP:
      if (value == null) {
        unsetCreateTimestamp();
      } else {
        setCreateTimestamp((Integer)value);
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

    case CREATE_TIMESTAMP:
      return Integer.valueOf(getCreateTimestamp());

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
    case CREATE_TIMESTAMP:
      return isSetCreateTimestamp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RoleTableRelation)
      return this.equals((RoleTableRelation)that);
    return false;
  }

  public boolean equals(RoleTableRelation that) {
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

    boolean this_present_createTimestamp = true && this.isSetCreateTimestamp();
    boolean that_present_createTimestamp = true && that.isSetCreateTimestamp();
    if (this_present_createTimestamp || that_present_createTimestamp) {
      if (!(this_present_createTimestamp && that_present_createTimestamp))
        return false;
      if (this.createTimestamp != that.createTimestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(RoleTableRelation other) {
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
    lastComparison = Boolean.valueOf(isSetCreateTimestamp()).compareTo(other.isSetCreateTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTimestamp, other.createTimestamp);
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
    StringBuilder sb = new StringBuilder("RoleTableRelation(");
    boolean first = true;

    sb.append("roleName:");
    if (this.roleName == null) {
      sb.append("null");
    } else {
      sb.append(this.roleName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tablePrefixName:");
    if (this.tablePrefixName == null) {
      sb.append("null");
    } else {
      sb.append(this.tablePrefixName);
    }
    first = false;
    if (isSetCreateTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("createTimestamp:");
      sb.append(this.createTimestamp);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (roleName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'roleName' was not present! Struct: " + toString());
    }
    if (tablePrefixName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'tablePrefixName' was not present! Struct: " + toString());
    }
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RoleTableRelationStandardSchemeFactory implements SchemeFactory {
    public RoleTableRelationStandardScheme getScheme() {
      return new RoleTableRelationStandardScheme();
    }
  }

  private static class RoleTableRelationStandardScheme extends StandardScheme<RoleTableRelation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RoleTableRelation struct) throws org.apache.thrift.TException {
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
          case 3: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RoleTableRelation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.roleName != null) {
        oprot.writeFieldBegin(ROLE_NAME_FIELD_DESC);
        oprot.writeString(struct.roleName);
        oprot.writeFieldEnd();
      }
      if (struct.tablePrefixName != null) {
        oprot.writeFieldBegin(TABLE_PREFIX_NAME_FIELD_DESC);
        oprot.writeString(struct.tablePrefixName);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.createTimestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RoleTableRelationTupleSchemeFactory implements SchemeFactory {
    public RoleTableRelationTupleScheme getScheme() {
      return new RoleTableRelationTupleScheme();
    }
  }

  private static class RoleTableRelationTupleScheme extends TupleScheme<RoleTableRelation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RoleTableRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.roleName);
      oprot.writeString(struct.tablePrefixName);
      BitSet optionals = new BitSet();
      if (struct.isSetCreateTimestamp()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RoleTableRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.roleName = iprot.readString();
      struct.setRoleNameIsSet(true);
      struct.tablePrefixName = iprot.readString();
      struct.setTablePrefixNameIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
    }
  }

}
