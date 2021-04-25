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

public class RoleSetRelation implements org.apache.thrift.TBase<RoleSetRelation, RoleSetRelation._Fields>, java.io.Serializable, Cloneable, Comparable<RoleSetRelation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RoleSetRelation");

  private static final org.apache.thrift.protocol.TField HOST_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("hostName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SET_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("setIndex", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_IN_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("typeInSet", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField ROLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("roleName", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestamp", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RoleSetRelationStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RoleSetRelationTupleSchemeFactory());
  }

  public String hostName; // required
  public int setIndex; // required
  /**
   * 
   * @see TypeInSet
   */
  public TypeInSet typeInSet; // optional
  public int weight; // optional
  public String roleName; // required
  public int createTimestamp; // optional
  public int lastmodifyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HOST_NAME((short)1, "hostName"),
    SET_INDEX((short)2, "setIndex"),
    /**
     * 
     * @see TypeInSet
     */
    TYPE_IN_SET((short)3, "typeInSet"),
    WEIGHT((short)4, "weight"),
    ROLE_NAME((short)5, "roleName"),
    CREATE_TIMESTAMP((short)6, "createTimestamp"),
    LASTMODIFY_TIMESTAMP((short)7, "lastmodifyTimestamp");

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
        case 1: // HOST_NAME
          return HOST_NAME;
        case 2: // SET_INDEX
          return SET_INDEX;
        case 3: // TYPE_IN_SET
          return TYPE_IN_SET;
        case 4: // WEIGHT
          return WEIGHT;
        case 5: // ROLE_NAME
          return ROLE_NAME;
        case 6: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 7: // LASTMODIFY_TIMESTAMP
          return LASTMODIFY_TIMESTAMP;
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
  private static final int __SETINDEX_ISSET_ID = 0;
  private static final int __WEIGHT_ISSET_ID = 1;
  private static final int __CREATETIMESTAMP_ISSET_ID = 2;
  private static final int __LASTMODIFYTIMESTAMP_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TYPE_IN_SET,_Fields.WEIGHT,_Fields.CREATE_TIMESTAMP,_Fields.LASTMODIFY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HOST_NAME, new org.apache.thrift.meta_data.FieldMetaData("hostName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SET_INDEX, new org.apache.thrift.meta_data.FieldMetaData("setIndex", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TYPE_IN_SET, new org.apache.thrift.meta_data.FieldMetaData("typeInSet", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TypeInSet.class)));
    tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ROLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("roleName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RoleSetRelation.class, metaDataMap);
  }

  public RoleSetRelation() {
  }

  public RoleSetRelation(
    String hostName,
    int setIndex,
    String roleName)
  {
    this();
    this.hostName = hostName;
    this.setIndex = setIndex;
    setSetIndexIsSet(true);
    this.roleName = roleName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RoleSetRelation(RoleSetRelation other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetHostName()) {
      this.hostName = other.hostName;
    }
    this.setIndex = other.setIndex;
    if (other.isSetTypeInSet()) {
      this.typeInSet = other.typeInSet;
    }
    this.weight = other.weight;
    if (other.isSetRoleName()) {
      this.roleName = other.roleName;
    }
    this.createTimestamp = other.createTimestamp;
    this.lastmodifyTimestamp = other.lastmodifyTimestamp;
  }

  public RoleSetRelation deepCopy() {
    return new RoleSetRelation(this);
  }

  @Override
  public void clear() {
    this.hostName = null;
    setSetIndexIsSet(false);
    this.setIndex = 0;
    this.typeInSet = null;
    setWeightIsSet(false);
    this.weight = 0;
    this.roleName = null;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmodifyTimestampIsSet(false);
    this.lastmodifyTimestamp = 0;
  }

  public String getHostName() {
    return this.hostName;
  }

  public RoleSetRelation setHostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

  public void unsetHostName() {
    this.hostName = null;
  }

  /** Returns true if field hostName is set (has been assigned a value) and false otherwise */
  public boolean isSetHostName() {
    return this.hostName != null;
  }

  public void setHostNameIsSet(boolean value) {
    if (!value) {
      this.hostName = null;
    }
  }

  public int getSetIndex() {
    return this.setIndex;
  }

  public RoleSetRelation setSetIndex(int setIndex) {
    this.setIndex = setIndex;
    setSetIndexIsSet(true);
    return this;
  }

  public void unsetSetIndex() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SETINDEX_ISSET_ID);
  }

  /** Returns true if field setIndex is set (has been assigned a value) and false otherwise */
  public boolean isSetSetIndex() {
    return EncodingUtils.testBit(__isset_bitfield, __SETINDEX_ISSET_ID);
  }

  public void setSetIndexIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SETINDEX_ISSET_ID, value);
  }

  /**
   * 
   * @see TypeInSet
   */
  public TypeInSet getTypeInSet() {
    return this.typeInSet;
  }

  /**
   * 
   * @see TypeInSet
   */
  public RoleSetRelation setTypeInSet(TypeInSet typeInSet) {
    this.typeInSet = typeInSet;
    return this;
  }

  public void unsetTypeInSet() {
    this.typeInSet = null;
  }

  /** Returns true if field typeInSet is set (has been assigned a value) and false otherwise */
  public boolean isSetTypeInSet() {
    return this.typeInSet != null;
  }

  public void setTypeInSetIsSet(boolean value) {
    if (!value) {
      this.typeInSet = null;
    }
  }

  public int getWeight() {
    return this.weight;
  }

  public RoleSetRelation setWeight(int weight) {
    this.weight = weight;
    setWeightIsSet(true);
    return this;
  }

  public void unsetWeight() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  /** Returns true if field weight is set (has been assigned a value) and false otherwise */
  public boolean isSetWeight() {
    return EncodingUtils.testBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  public void setWeightIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __WEIGHT_ISSET_ID, value);
  }

  public String getRoleName() {
    return this.roleName;
  }

  public RoleSetRelation setRoleName(String roleName) {
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

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public RoleSetRelation setCreateTimestamp(int createTimestamp) {
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

  public int getLastmodifyTimestamp() {
    return this.lastmodifyTimestamp;
  }

  public RoleSetRelation setLastmodifyTimestamp(int lastmodifyTimestamp) {
    this.lastmodifyTimestamp = lastmodifyTimestamp;
    setLastmodifyTimestampIsSet(true);
    return this;
  }

  public void unsetLastmodifyTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field lastmodifyTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetLastmodifyTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID);
  }

  public void setLastmodifyTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMODIFYTIMESTAMP_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case HOST_NAME:
      if (value == null) {
        unsetHostName();
      } else {
        setHostName((String)value);
      }
      break;

    case SET_INDEX:
      if (value == null) {
        unsetSetIndex();
      } else {
        setSetIndex((Integer)value);
      }
      break;

    case TYPE_IN_SET:
      if (value == null) {
        unsetTypeInSet();
      } else {
        setTypeInSet((TypeInSet)value);
      }
      break;

    case WEIGHT:
      if (value == null) {
        unsetWeight();
      } else {
        setWeight((Integer)value);
      }
      break;

    case ROLE_NAME:
      if (value == null) {
        unsetRoleName();
      } else {
        setRoleName((String)value);
      }
      break;

    case CREATE_TIMESTAMP:
      if (value == null) {
        unsetCreateTimestamp();
      } else {
        setCreateTimestamp((Integer)value);
      }
      break;

    case LASTMODIFY_TIMESTAMP:
      if (value == null) {
        unsetLastmodifyTimestamp();
      } else {
        setLastmodifyTimestamp((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case HOST_NAME:
      return getHostName();

    case SET_INDEX:
      return Integer.valueOf(getSetIndex());

    case TYPE_IN_SET:
      return getTypeInSet();

    case WEIGHT:
      return Integer.valueOf(getWeight());

    case ROLE_NAME:
      return getRoleName();

    case CREATE_TIMESTAMP:
      return Integer.valueOf(getCreateTimestamp());

    case LASTMODIFY_TIMESTAMP:
      return Integer.valueOf(getLastmodifyTimestamp());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case HOST_NAME:
      return isSetHostName();
    case SET_INDEX:
      return isSetSetIndex();
    case TYPE_IN_SET:
      return isSetTypeInSet();
    case WEIGHT:
      return isSetWeight();
    case ROLE_NAME:
      return isSetRoleName();
    case CREATE_TIMESTAMP:
      return isSetCreateTimestamp();
    case LASTMODIFY_TIMESTAMP:
      return isSetLastmodifyTimestamp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RoleSetRelation)
      return this.equals((RoleSetRelation)that);
    return false;
  }

  public boolean equals(RoleSetRelation that) {
    if (that == null)
      return false;

    boolean this_present_hostName = true && this.isSetHostName();
    boolean that_present_hostName = true && that.isSetHostName();
    if (this_present_hostName || that_present_hostName) {
      if (!(this_present_hostName && that_present_hostName))
        return false;
      if (!this.hostName.equals(that.hostName))
        return false;
    }

    boolean this_present_setIndex = true;
    boolean that_present_setIndex = true;
    if (this_present_setIndex || that_present_setIndex) {
      if (!(this_present_setIndex && that_present_setIndex))
        return false;
      if (this.setIndex != that.setIndex)
        return false;
    }

    boolean this_present_typeInSet = true && this.isSetTypeInSet();
    boolean that_present_typeInSet = true && that.isSetTypeInSet();
    if (this_present_typeInSet || that_present_typeInSet) {
      if (!(this_present_typeInSet && that_present_typeInSet))
        return false;
      if (!this.typeInSet.equals(that.typeInSet))
        return false;
    }

    boolean this_present_weight = true && this.isSetWeight();
    boolean that_present_weight = true && that.isSetWeight();
    if (this_present_weight || that_present_weight) {
      if (!(this_present_weight && that_present_weight))
        return false;
      if (this.weight != that.weight)
        return false;
    }

    boolean this_present_roleName = true && this.isSetRoleName();
    boolean that_present_roleName = true && that.isSetRoleName();
    if (this_present_roleName || that_present_roleName) {
      if (!(this_present_roleName && that_present_roleName))
        return false;
      if (!this.roleName.equals(that.roleName))
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

    boolean this_present_lastmodifyTimestamp = true && this.isSetLastmodifyTimestamp();
    boolean that_present_lastmodifyTimestamp = true && that.isSetLastmodifyTimestamp();
    if (this_present_lastmodifyTimestamp || that_present_lastmodifyTimestamp) {
      if (!(this_present_lastmodifyTimestamp && that_present_lastmodifyTimestamp))
        return false;
      if (this.lastmodifyTimestamp != that.lastmodifyTimestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(RoleSetRelation other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetHostName()).compareTo(other.isSetHostName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHostName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hostName, other.hostName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSetIndex()).compareTo(other.isSetSetIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSetIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.setIndex, other.setIndex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTypeInSet()).compareTo(other.isSetTypeInSet());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTypeInSet()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.typeInSet, other.typeInSet);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetWeight()).compareTo(other.isSetWeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.weight, other.weight);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetLastmodifyTimestamp()).compareTo(other.isSetLastmodifyTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastmodifyTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastmodifyTimestamp, other.lastmodifyTimestamp);
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
    StringBuilder sb = new StringBuilder("RoleSetRelation(");
    boolean first = true;

    sb.append("hostName:");
    if (this.hostName == null) {
      sb.append("null");
    } else {
      sb.append(this.hostName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("setIndex:");
    sb.append(this.setIndex);
    first = false;
    if (isSetTypeInSet()) {
      if (!first) sb.append(", ");
      sb.append("typeInSet:");
      if (this.typeInSet == null) {
        sb.append("null");
      } else {
        sb.append(this.typeInSet);
      }
      first = false;
    }
    if (isSetWeight()) {
      if (!first) sb.append(", ");
      sb.append("weight:");
      sb.append(this.weight);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("roleName:");
    if (this.roleName == null) {
      sb.append("null");
    } else {
      sb.append(this.roleName);
    }
    first = false;
    if (isSetCreateTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("createTimestamp:");
      sb.append(this.createTimestamp);
      first = false;
    }
    if (isSetLastmodifyTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("lastmodifyTimestamp:");
      sb.append(this.lastmodifyTimestamp);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (hostName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'hostName' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'setIndex' because it's a primitive and you chose the non-beans generator.
    if (roleName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'roleName' was not present! Struct: " + toString());
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

  private static class RoleSetRelationStandardSchemeFactory implements SchemeFactory {
    public RoleSetRelationStandardScheme getScheme() {
      return new RoleSetRelationStandardScheme();
    }
  }

  private static class RoleSetRelationStandardScheme extends StandardScheme<RoleSetRelation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RoleSetRelation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HOST_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hostName = iprot.readString();
              struct.setHostNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SET_INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.setIndex = iprot.readI32();
              struct.setSetIndexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE_IN_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.typeInSet = TypeInSet.findByValue(iprot.readI32());
              struct.setTypeInSetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.weight = iprot.readI32();
              struct.setWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ROLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.roleName = iprot.readString();
              struct.setRoleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // LASTMODIFY_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lastmodifyTimestamp = iprot.readI32();
              struct.setLastmodifyTimestampIsSet(true);
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
      if (!struct.isSetSetIndex()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'setIndex' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RoleSetRelation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.hostName != null) {
        oprot.writeFieldBegin(HOST_NAME_FIELD_DESC);
        oprot.writeString(struct.hostName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SET_INDEX_FIELD_DESC);
      oprot.writeI32(struct.setIndex);
      oprot.writeFieldEnd();
      if (struct.typeInSet != null) {
        if (struct.isSetTypeInSet()) {
          oprot.writeFieldBegin(TYPE_IN_SET_FIELD_DESC);
          oprot.writeI32(struct.typeInSet.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetWeight()) {
        oprot.writeFieldBegin(WEIGHT_FIELD_DESC);
        oprot.writeI32(struct.weight);
        oprot.writeFieldEnd();
      }
      if (struct.roleName != null) {
        oprot.writeFieldBegin(ROLE_NAME_FIELD_DESC);
        oprot.writeString(struct.roleName);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.createTimestamp);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeFieldBegin(LASTMODIFY_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.lastmodifyTimestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RoleSetRelationTupleSchemeFactory implements SchemeFactory {
    public RoleSetRelationTupleScheme getScheme() {
      return new RoleSetRelationTupleScheme();
    }
  }

  private static class RoleSetRelationTupleScheme extends TupleScheme<RoleSetRelation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RoleSetRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.hostName);
      oprot.writeI32(struct.setIndex);
      oprot.writeString(struct.roleName);
      BitSet optionals = new BitSet();
      if (struct.isSetTypeInSet()) {
        optionals.set(0);
      }
      if (struct.isSetWeight()) {
        optionals.set(1);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(2);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTypeInSet()) {
        oprot.writeI32(struct.typeInSet.getValue());
      }
      if (struct.isSetWeight()) {
        oprot.writeI32(struct.weight);
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeI32(struct.lastmodifyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RoleSetRelation struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.hostName = iprot.readString();
      struct.setHostNameIsSet(true);
      struct.setIndex = iprot.readI32();
      struct.setSetIndexIsSet(true);
      struct.roleName = iprot.readString();
      struct.setRoleNameIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.typeInSet = TypeInSet.findByValue(iprot.readI32());
        struct.setTypeInSetIsSet(true);
      }
      if (incoming.get(1)) {
        struct.weight = iprot.readI32();
        struct.setWeightIsSet(true);
      }
      if (incoming.get(2)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lastmodifyTimestamp = iprot.readI32();
        struct.setLastmodifyTimestampIsSet(true);
      }
    }
  }

}

