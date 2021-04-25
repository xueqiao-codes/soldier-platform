/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;

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

public class QueryHostingUserOption implements org.apache.thrift.TBase<QueryHostingUserOption, QueryHostingUserOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryHostingUserOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryHostingUserOption");

  private static final org.apache.thrift.protocol.TField SUB_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subUserId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField LOGIN_NAME_PARTICAL_FIELD_DESC = new org.apache.thrift.protocol.TField("loginNamePartical", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField NICK_NAME_PARTICAL_FIELD_DESC = new org.apache.thrift.protocol.TField("nickNamePartical", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ORDER_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("orderType", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryHostingUserOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryHostingUserOptionTupleSchemeFactory());
  }

  public int subUserId; // optional
  public String loginNamePartical; // optional
  public String nickNamePartical; // optional
  /**
   * 
   * @see HostingUserOrderType
   */
  public HostingUserOrderType orderType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_USER_ID((short)2, "subUserId"),
    LOGIN_NAME_PARTICAL((short)3, "loginNamePartical"),
    NICK_NAME_PARTICAL((short)4, "nickNamePartical"),
    /**
     * 
     * @see HostingUserOrderType
     */
    ORDER_TYPE((short)6, "orderType");

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
        case 2: // SUB_USER_ID
          return SUB_USER_ID;
        case 3: // LOGIN_NAME_PARTICAL
          return LOGIN_NAME_PARTICAL;
        case 4: // NICK_NAME_PARTICAL
          return NICK_NAME_PARTICAL;
        case 6: // ORDER_TYPE
          return ORDER_TYPE;
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
  private static final int __SUBUSERID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_USER_ID,_Fields.LOGIN_NAME_PARTICAL,_Fields.NICK_NAME_PARTICAL,_Fields.ORDER_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("subUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOGIN_NAME_PARTICAL, new org.apache.thrift.meta_data.FieldMetaData("loginNamePartical", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NICK_NAME_PARTICAL, new org.apache.thrift.meta_data.FieldMetaData("nickNamePartical", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_TYPE, new org.apache.thrift.meta_data.FieldMetaData("orderType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, HostingUserOrderType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryHostingUserOption.class, metaDataMap);
  }

  public QueryHostingUserOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryHostingUserOption(QueryHostingUserOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subUserId = other.subUserId;
    if (other.isSetLoginNamePartical()) {
      this.loginNamePartical = other.loginNamePartical;
    }
    if (other.isSetNickNamePartical()) {
      this.nickNamePartical = other.nickNamePartical;
    }
    if (other.isSetOrderType()) {
      this.orderType = other.orderType;
    }
  }

  public QueryHostingUserOption deepCopy() {
    return new QueryHostingUserOption(this);
  }

  @Override
  public void clear() {
    setSubUserIdIsSet(false);
    this.subUserId = 0;
    this.loginNamePartical = null;
    this.nickNamePartical = null;
    this.orderType = null;
  }

  public int getSubUserId() {
    return this.subUserId;
  }

  public QueryHostingUserOption setSubUserId(int subUserId) {
    this.subUserId = subUserId;
    setSubUserIdIsSet(true);
    return this;
  }

  public void unsetSubUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUBUSERID_ISSET_ID);
  }

  /** Returns true if field subUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetSubUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __SUBUSERID_ISSET_ID);
  }

  public void setSubUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUBUSERID_ISSET_ID, value);
  }

  public String getLoginNamePartical() {
    return this.loginNamePartical;
  }

  public QueryHostingUserOption setLoginNamePartical(String loginNamePartical) {
    this.loginNamePartical = loginNamePartical;
    return this;
  }

  public void unsetLoginNamePartical() {
    this.loginNamePartical = null;
  }

  /** Returns true if field loginNamePartical is set (has been assigned a value) and false otherwise */
  public boolean isSetLoginNamePartical() {
    return this.loginNamePartical != null;
  }

  public void setLoginNameParticalIsSet(boolean value) {
    if (!value) {
      this.loginNamePartical = null;
    }
  }

  public String getNickNamePartical() {
    return this.nickNamePartical;
  }

  public QueryHostingUserOption setNickNamePartical(String nickNamePartical) {
    this.nickNamePartical = nickNamePartical;
    return this;
  }

  public void unsetNickNamePartical() {
    this.nickNamePartical = null;
  }

  /** Returns true if field nickNamePartical is set (has been assigned a value) and false otherwise */
  public boolean isSetNickNamePartical() {
    return this.nickNamePartical != null;
  }

  public void setNickNameParticalIsSet(boolean value) {
    if (!value) {
      this.nickNamePartical = null;
    }
  }

  /**
   * 
   * @see HostingUserOrderType
   */
  public HostingUserOrderType getOrderType() {
    return this.orderType;
  }

  /**
   * 
   * @see HostingUserOrderType
   */
  public QueryHostingUserOption setOrderType(HostingUserOrderType orderType) {
    this.orderType = orderType;
    return this;
  }

  public void unsetOrderType() {
    this.orderType = null;
  }

  /** Returns true if field orderType is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderType() {
    return this.orderType != null;
  }

  public void setOrderTypeIsSet(boolean value) {
    if (!value) {
      this.orderType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB_USER_ID:
      if (value == null) {
        unsetSubUserId();
      } else {
        setSubUserId((Integer)value);
      }
      break;

    case LOGIN_NAME_PARTICAL:
      if (value == null) {
        unsetLoginNamePartical();
      } else {
        setLoginNamePartical((String)value);
      }
      break;

    case NICK_NAME_PARTICAL:
      if (value == null) {
        unsetNickNamePartical();
      } else {
        setNickNamePartical((String)value);
      }
      break;

    case ORDER_TYPE:
      if (value == null) {
        unsetOrderType();
      } else {
        setOrderType((HostingUserOrderType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_USER_ID:
      return Integer.valueOf(getSubUserId());

    case LOGIN_NAME_PARTICAL:
      return getLoginNamePartical();

    case NICK_NAME_PARTICAL:
      return getNickNamePartical();

    case ORDER_TYPE:
      return getOrderType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB_USER_ID:
      return isSetSubUserId();
    case LOGIN_NAME_PARTICAL:
      return isSetLoginNamePartical();
    case NICK_NAME_PARTICAL:
      return isSetNickNamePartical();
    case ORDER_TYPE:
      return isSetOrderType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryHostingUserOption)
      return this.equals((QueryHostingUserOption)that);
    return false;
  }

  public boolean equals(QueryHostingUserOption that) {
    if (that == null)
      return false;

    boolean this_present_subUserId = true && this.isSetSubUserId();
    boolean that_present_subUserId = true && that.isSetSubUserId();
    if (this_present_subUserId || that_present_subUserId) {
      if (!(this_present_subUserId && that_present_subUserId))
        return false;
      if (this.subUserId != that.subUserId)
        return false;
    }

    boolean this_present_loginNamePartical = true && this.isSetLoginNamePartical();
    boolean that_present_loginNamePartical = true && that.isSetLoginNamePartical();
    if (this_present_loginNamePartical || that_present_loginNamePartical) {
      if (!(this_present_loginNamePartical && that_present_loginNamePartical))
        return false;
      if (!this.loginNamePartical.equals(that.loginNamePartical))
        return false;
    }

    boolean this_present_nickNamePartical = true && this.isSetNickNamePartical();
    boolean that_present_nickNamePartical = true && that.isSetNickNamePartical();
    if (this_present_nickNamePartical || that_present_nickNamePartical) {
      if (!(this_present_nickNamePartical && that_present_nickNamePartical))
        return false;
      if (!this.nickNamePartical.equals(that.nickNamePartical))
        return false;
    }

    boolean this_present_orderType = true && this.isSetOrderType();
    boolean that_present_orderType = true && that.isSetOrderType();
    if (this_present_orderType || that_present_orderType) {
      if (!(this_present_orderType && that_present_orderType))
        return false;
      if (!this.orderType.equals(that.orderType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryHostingUserOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSubUserId()).compareTo(other.isSetSubUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subUserId, other.subUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLoginNamePartical()).compareTo(other.isSetLoginNamePartical());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginNamePartical()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loginNamePartical, other.loginNamePartical);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNickNamePartical()).compareTo(other.isSetNickNamePartical());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNickNamePartical()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nickNamePartical, other.nickNamePartical);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderType()).compareTo(other.isSetOrderType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderType, other.orderType);
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
    StringBuilder sb = new StringBuilder("QueryHostingUserOption(");
    boolean first = true;

    if (isSetSubUserId()) {
      sb.append("subUserId:");
      sb.append(this.subUserId);
      first = false;
    }
    if (isSetLoginNamePartical()) {
      if (!first) sb.append(", ");
      sb.append("loginNamePartical:");
      if (this.loginNamePartical == null) {
        sb.append("null");
      } else {
        sb.append(this.loginNamePartical);
      }
      first = false;
    }
    if (isSetNickNamePartical()) {
      if (!first) sb.append(", ");
      sb.append("nickNamePartical:");
      if (this.nickNamePartical == null) {
        sb.append("null");
      } else {
        sb.append(this.nickNamePartical);
      }
      first = false;
    }
    if (isSetOrderType()) {
      if (!first) sb.append(", ");
      sb.append("orderType:");
      if (this.orderType == null) {
        sb.append("null");
      } else {
        sb.append(this.orderType);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class QueryHostingUserOptionStandardSchemeFactory implements SchemeFactory {
    public QueryHostingUserOptionStandardScheme getScheme() {
      return new QueryHostingUserOptionStandardScheme();
    }
  }

  private static class QueryHostingUserOptionStandardScheme extends StandardScheme<QueryHostingUserOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryHostingUserOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 2: // SUB_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.subUserId = iprot.readI32();
              struct.setSubUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LOGIN_NAME_PARTICAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.loginNamePartical = iprot.readString();
              struct.setLoginNameParticalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NICK_NAME_PARTICAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.nickNamePartical = iprot.readString();
              struct.setNickNameParticalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ORDER_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.orderType = HostingUserOrderType.findByValue(iprot.readI32());
              struct.setOrderTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryHostingUserOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubUserId()) {
        oprot.writeFieldBegin(SUB_USER_ID_FIELD_DESC);
        oprot.writeI32(struct.subUserId);
        oprot.writeFieldEnd();
      }
      if (struct.loginNamePartical != null) {
        if (struct.isSetLoginNamePartical()) {
          oprot.writeFieldBegin(LOGIN_NAME_PARTICAL_FIELD_DESC);
          oprot.writeString(struct.loginNamePartical);
          oprot.writeFieldEnd();
        }
      }
      if (struct.nickNamePartical != null) {
        if (struct.isSetNickNamePartical()) {
          oprot.writeFieldBegin(NICK_NAME_PARTICAL_FIELD_DESC);
          oprot.writeString(struct.nickNamePartical);
          oprot.writeFieldEnd();
        }
      }
      if (struct.orderType != null) {
        if (struct.isSetOrderType()) {
          oprot.writeFieldBegin(ORDER_TYPE_FIELD_DESC);
          oprot.writeI32(struct.orderType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryHostingUserOptionTupleSchemeFactory implements SchemeFactory {
    public QueryHostingUserOptionTupleScheme getScheme() {
      return new QueryHostingUserOptionTupleScheme();
    }
  }

  private static class QueryHostingUserOptionTupleScheme extends TupleScheme<QueryHostingUserOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryHostingUserOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubUserId()) {
        optionals.set(0);
      }
      if (struct.isSetLoginNamePartical()) {
        optionals.set(1);
      }
      if (struct.isSetNickNamePartical()) {
        optionals.set(2);
      }
      if (struct.isSetOrderType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSubUserId()) {
        oprot.writeI32(struct.subUserId);
      }
      if (struct.isSetLoginNamePartical()) {
        oprot.writeString(struct.loginNamePartical);
      }
      if (struct.isSetNickNamePartical()) {
        oprot.writeString(struct.nickNamePartical);
      }
      if (struct.isSetOrderType()) {
        oprot.writeI32(struct.orderType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryHostingUserOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.subUserId = iprot.readI32();
        struct.setSubUserIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.loginNamePartical = iprot.readString();
        struct.setLoginNameParticalIsSet(true);
      }
      if (incoming.get(2)) {
        struct.nickNamePartical = iprot.readString();
        struct.setNickNameParticalIsSet(true);
      }
      if (incoming.get(3)) {
        struct.orderType = HostingUserOrderType.findByValue(iprot.readI32());
        struct.setOrderTypeIsSet(true);
      }
    }
  }

}

