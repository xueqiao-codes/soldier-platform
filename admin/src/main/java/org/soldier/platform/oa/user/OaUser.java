/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.oa.user;

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

public class OaUser implements org.apache.thrift.TBase<OaUser, OaUser._Fields>, java.io.Serializable, Cloneable, Comparable<OaUser> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OaUser");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("userName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField USER_PASSWORD_FIELD_DESC = new org.apache.thrift.protocol.TField("userPassword", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField USER_NICK_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("userNickName", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField BIND_TOY_EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("bindToyEmail", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField BIND_TOY_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("bindToyUserId", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)20);
  private static final org.apache.thrift.protocol.TField LASTMOFIY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmofiyTimestamp", org.apache.thrift.protocol.TType.I32, (short)21);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OaUserStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OaUserTupleSchemeFactory());
  }

  public int userId; // optional
  public String userName; // optional
  public String userPassword; // optional
  public String userNickName; // optional
  public String bindToyEmail; // optional
  public int bindToyUserId; // optional
  public int createTimestamp; // optional
  public int lastmofiyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "userId"),
    USER_NAME((short)2, "userName"),
    USER_PASSWORD((short)3, "userPassword"),
    USER_NICK_NAME((short)4, "userNickName"),
    BIND_TOY_EMAIL((short)5, "bindToyEmail"),
    BIND_TOY_USER_ID((short)6, "bindToyUserId"),
    CREATE_TIMESTAMP((short)20, "createTimestamp"),
    LASTMOFIY_TIMESTAMP((short)21, "lastmofiyTimestamp");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // USER_NAME
          return USER_NAME;
        case 3: // USER_PASSWORD
          return USER_PASSWORD;
        case 4: // USER_NICK_NAME
          return USER_NICK_NAME;
        case 5: // BIND_TOY_EMAIL
          return BIND_TOY_EMAIL;
        case 6: // BIND_TOY_USER_ID
          return BIND_TOY_USER_ID;
        case 20: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 21: // LASTMOFIY_TIMESTAMP
          return LASTMOFIY_TIMESTAMP;
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
  private static final int __USERID_ISSET_ID = 0;
  private static final int __BINDTOYUSERID_ISSET_ID = 1;
  private static final int __CREATETIMESTAMP_ISSET_ID = 2;
  private static final int __LASTMOFIYTIMESTAMP_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.USER_ID,_Fields.USER_NAME,_Fields.USER_PASSWORD,_Fields.USER_NICK_NAME,_Fields.BIND_TOY_EMAIL,_Fields.BIND_TOY_USER_ID,_Fields.CREATE_TIMESTAMP,_Fields.LASTMOFIY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("userName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_PASSWORD, new org.apache.thrift.meta_data.FieldMetaData("userPassword", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_NICK_NAME, new org.apache.thrift.meta_data.FieldMetaData("userNickName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BIND_TOY_EMAIL, new org.apache.thrift.meta_data.FieldMetaData("bindToyEmail", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BIND_TOY_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("bindToyUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMOFIY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmofiyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OaUser.class, metaDataMap);
  }

  public OaUser() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OaUser(OaUser other) {
    __isset_bitfield = other.__isset_bitfield;
    this.userId = other.userId;
    if (other.isSetUserName()) {
      this.userName = other.userName;
    }
    if (other.isSetUserPassword()) {
      this.userPassword = other.userPassword;
    }
    if (other.isSetUserNickName()) {
      this.userNickName = other.userNickName;
    }
    if (other.isSetBindToyEmail()) {
      this.bindToyEmail = other.bindToyEmail;
    }
    this.bindToyUserId = other.bindToyUserId;
    this.createTimestamp = other.createTimestamp;
    this.lastmofiyTimestamp = other.lastmofiyTimestamp;
  }

  public OaUser deepCopy() {
    return new OaUser(this);
  }

  @Override
  public void clear() {
    setUserIdIsSet(false);
    this.userId = 0;
    this.userName = null;
    this.userPassword = null;
    this.userNickName = null;
    this.bindToyEmail = null;
    setBindToyUserIdIsSet(false);
    this.bindToyUserId = 0;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmofiyTimestampIsSet(false);
    this.lastmofiyTimestamp = 0;
  }

  public int getUserId() {
    return this.userId;
  }

  public OaUser setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);
    return this;
  }

  public void unsetUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
  }

  public String getUserName() {
    return this.userName;
  }

  public OaUser setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public void unsetUserName() {
    this.userName = null;
  }

  /** Returns true if field userName is set (has been assigned a value) and false otherwise */
  public boolean isSetUserName() {
    return this.userName != null;
  }

  public void setUserNameIsSet(boolean value) {
    if (!value) {
      this.userName = null;
    }
  }

  public String getUserPassword() {
    return this.userPassword;
  }

  public OaUser setUserPassword(String userPassword) {
    this.userPassword = userPassword;
    return this;
  }

  public void unsetUserPassword() {
    this.userPassword = null;
  }

  /** Returns true if field userPassword is set (has been assigned a value) and false otherwise */
  public boolean isSetUserPassword() {
    return this.userPassword != null;
  }

  public void setUserPasswordIsSet(boolean value) {
    if (!value) {
      this.userPassword = null;
    }
  }

  public String getUserNickName() {
    return this.userNickName;
  }

  public OaUser setUserNickName(String userNickName) {
    this.userNickName = userNickName;
    return this;
  }

  public void unsetUserNickName() {
    this.userNickName = null;
  }

  /** Returns true if field userNickName is set (has been assigned a value) and false otherwise */
  public boolean isSetUserNickName() {
    return this.userNickName != null;
  }

  public void setUserNickNameIsSet(boolean value) {
    if (!value) {
      this.userNickName = null;
    }
  }

  public String getBindToyEmail() {
    return this.bindToyEmail;
  }

  public OaUser setBindToyEmail(String bindToyEmail) {
    this.bindToyEmail = bindToyEmail;
    return this;
  }

  public void unsetBindToyEmail() {
    this.bindToyEmail = null;
  }

  /** Returns true if field bindToyEmail is set (has been assigned a value) and false otherwise */
  public boolean isSetBindToyEmail() {
    return this.bindToyEmail != null;
  }

  public void setBindToyEmailIsSet(boolean value) {
    if (!value) {
      this.bindToyEmail = null;
    }
  }

  public int getBindToyUserId() {
    return this.bindToyUserId;
  }

  public OaUser setBindToyUserId(int bindToyUserId) {
    this.bindToyUserId = bindToyUserId;
    setBindToyUserIdIsSet(true);
    return this;
  }

  public void unsetBindToyUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BINDTOYUSERID_ISSET_ID);
  }

  /** Returns true if field bindToyUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetBindToyUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __BINDTOYUSERID_ISSET_ID);
  }

  public void setBindToyUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BINDTOYUSERID_ISSET_ID, value);
  }

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public OaUser setCreateTimestamp(int createTimestamp) {
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

  public int getLastmofiyTimestamp() {
    return this.lastmofiyTimestamp;
  }

  public OaUser setLastmofiyTimestamp(int lastmofiyTimestamp) {
    this.lastmofiyTimestamp = lastmofiyTimestamp;
    setLastmofiyTimestampIsSet(true);
    return this;
  }

  public void unsetLastmofiyTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMOFIYTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field lastmofiyTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetLastmofiyTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMOFIYTIMESTAMP_ISSET_ID);
  }

  public void setLastmofiyTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMOFIYTIMESTAMP_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;

    case USER_NAME:
      if (value == null) {
        unsetUserName();
      } else {
        setUserName((String)value);
      }
      break;

    case USER_PASSWORD:
      if (value == null) {
        unsetUserPassword();
      } else {
        setUserPassword((String)value);
      }
      break;

    case USER_NICK_NAME:
      if (value == null) {
        unsetUserNickName();
      } else {
        setUserNickName((String)value);
      }
      break;

    case BIND_TOY_EMAIL:
      if (value == null) {
        unsetBindToyEmail();
      } else {
        setBindToyEmail((String)value);
      }
      break;

    case BIND_TOY_USER_ID:
      if (value == null) {
        unsetBindToyUserId();
      } else {
        setBindToyUserId((Integer)value);
      }
      break;

    case CREATE_TIMESTAMP:
      if (value == null) {
        unsetCreateTimestamp();
      } else {
        setCreateTimestamp((Integer)value);
      }
      break;

    case LASTMOFIY_TIMESTAMP:
      if (value == null) {
        unsetLastmofiyTimestamp();
      } else {
        setLastmofiyTimestamp((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return Integer.valueOf(getUserId());

    case USER_NAME:
      return getUserName();

    case USER_PASSWORD:
      return getUserPassword();

    case USER_NICK_NAME:
      return getUserNickName();

    case BIND_TOY_EMAIL:
      return getBindToyEmail();

    case BIND_TOY_USER_ID:
      return Integer.valueOf(getBindToyUserId());

    case CREATE_TIMESTAMP:
      return Integer.valueOf(getCreateTimestamp());

    case LASTMOFIY_TIMESTAMP:
      return Integer.valueOf(getLastmofiyTimestamp());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserId();
    case USER_NAME:
      return isSetUserName();
    case USER_PASSWORD:
      return isSetUserPassword();
    case USER_NICK_NAME:
      return isSetUserNickName();
    case BIND_TOY_EMAIL:
      return isSetBindToyEmail();
    case BIND_TOY_USER_ID:
      return isSetBindToyUserId();
    case CREATE_TIMESTAMP:
      return isSetCreateTimestamp();
    case LASTMOFIY_TIMESTAMP:
      return isSetLastmofiyTimestamp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OaUser)
      return this.equals((OaUser)that);
    return false;
  }

  public boolean equals(OaUser that) {
    if (that == null)
      return false;

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    boolean this_present_userName = true && this.isSetUserName();
    boolean that_present_userName = true && that.isSetUserName();
    if (this_present_userName || that_present_userName) {
      if (!(this_present_userName && that_present_userName))
        return false;
      if (!this.userName.equals(that.userName))
        return false;
    }

    boolean this_present_userPassword = true && this.isSetUserPassword();
    boolean that_present_userPassword = true && that.isSetUserPassword();
    if (this_present_userPassword || that_present_userPassword) {
      if (!(this_present_userPassword && that_present_userPassword))
        return false;
      if (!this.userPassword.equals(that.userPassword))
        return false;
    }

    boolean this_present_userNickName = true && this.isSetUserNickName();
    boolean that_present_userNickName = true && that.isSetUserNickName();
    if (this_present_userNickName || that_present_userNickName) {
      if (!(this_present_userNickName && that_present_userNickName))
        return false;
      if (!this.userNickName.equals(that.userNickName))
        return false;
    }

    boolean this_present_bindToyEmail = true && this.isSetBindToyEmail();
    boolean that_present_bindToyEmail = true && that.isSetBindToyEmail();
    if (this_present_bindToyEmail || that_present_bindToyEmail) {
      if (!(this_present_bindToyEmail && that_present_bindToyEmail))
        return false;
      if (!this.bindToyEmail.equals(that.bindToyEmail))
        return false;
    }

    boolean this_present_bindToyUserId = true && this.isSetBindToyUserId();
    boolean that_present_bindToyUserId = true && that.isSetBindToyUserId();
    if (this_present_bindToyUserId || that_present_bindToyUserId) {
      if (!(this_present_bindToyUserId && that_present_bindToyUserId))
        return false;
      if (this.bindToyUserId != that.bindToyUserId)
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

    boolean this_present_lastmofiyTimestamp = true && this.isSetLastmofiyTimestamp();
    boolean that_present_lastmofiyTimestamp = true && that.isSetLastmofiyTimestamp();
    if (this_present_lastmofiyTimestamp || that_present_lastmofiyTimestamp) {
      if (!(this_present_lastmofiyTimestamp && that_present_lastmofiyTimestamp))
        return false;
      if (this.lastmofiyTimestamp != that.lastmofiyTimestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(OaUser other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserName()).compareTo(other.isSetUserName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userName, other.userName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserPassword()).compareTo(other.isSetUserPassword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserPassword()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userPassword, other.userPassword);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserNickName()).compareTo(other.isSetUserNickName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserNickName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userNickName, other.userNickName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBindToyEmail()).compareTo(other.isSetBindToyEmail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBindToyEmail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bindToyEmail, other.bindToyEmail);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBindToyUserId()).compareTo(other.isSetBindToyUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBindToyUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bindToyUserId, other.bindToyUserId);
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
    lastComparison = Boolean.valueOf(isSetLastmofiyTimestamp()).compareTo(other.isSetLastmofiyTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastmofiyTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastmofiyTimestamp, other.lastmofiyTimestamp);
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
    StringBuilder sb = new StringBuilder("OaUser(");
    boolean first = true;

    if (isSetUserId()) {
      sb.append("userId:");
      sb.append(this.userId);
      first = false;
    }
    if (isSetUserName()) {
      if (!first) sb.append(", ");
      sb.append("userName:");
      if (this.userName == null) {
        sb.append("null");
      } else {
        sb.append(this.userName);
      }
      first = false;
    }
    if (isSetUserPassword()) {
      if (!first) sb.append(", ");
      sb.append("userPassword:");
      if (this.userPassword == null) {
        sb.append("null");
      } else {
        sb.append(this.userPassword);
      }
      first = false;
    }
    if (isSetUserNickName()) {
      if (!first) sb.append(", ");
      sb.append("userNickName:");
      if (this.userNickName == null) {
        sb.append("null");
      } else {
        sb.append(this.userNickName);
      }
      first = false;
    }
    if (isSetBindToyEmail()) {
      if (!first) sb.append(", ");
      sb.append("bindToyEmail:");
      if (this.bindToyEmail == null) {
        sb.append("null");
      } else {
        sb.append(this.bindToyEmail);
      }
      first = false;
    }
    if (isSetBindToyUserId()) {
      if (!first) sb.append(", ");
      sb.append("bindToyUserId:");
      sb.append(this.bindToyUserId);
      first = false;
    }
    if (isSetCreateTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("createTimestamp:");
      sb.append(this.createTimestamp);
      first = false;
    }
    if (isSetLastmofiyTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("lastmofiyTimestamp:");
      sb.append(this.lastmofiyTimestamp);
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

  private static class OaUserStandardSchemeFactory implements SchemeFactory {
    public OaUserStandardScheme getScheme() {
      return new OaUserStandardScheme();
    }
  }

  private static class OaUserStandardScheme extends StandardScheme<OaUser> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OaUser struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userName = iprot.readString();
              struct.setUserNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_PASSWORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userPassword = iprot.readString();
              struct.setUserPasswordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // USER_NICK_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userNickName = iprot.readString();
              struct.setUserNickNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // BIND_TOY_EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bindToyEmail = iprot.readString();
              struct.setBindToyEmailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // BIND_TOY_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.bindToyUserId = iprot.readI32();
              struct.setBindToyUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 20: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 21: // LASTMOFIY_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lastmofiyTimestamp = iprot.readI32();
              struct.setLastmofiyTimestampIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OaUser struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetUserId()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.userName != null) {
        if (struct.isSetUserName()) {
          oprot.writeFieldBegin(USER_NAME_FIELD_DESC);
          oprot.writeString(struct.userName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.userPassword != null) {
        if (struct.isSetUserPassword()) {
          oprot.writeFieldBegin(USER_PASSWORD_FIELD_DESC);
          oprot.writeString(struct.userPassword);
          oprot.writeFieldEnd();
        }
      }
      if (struct.userNickName != null) {
        if (struct.isSetUserNickName()) {
          oprot.writeFieldBegin(USER_NICK_NAME_FIELD_DESC);
          oprot.writeString(struct.userNickName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.bindToyEmail != null) {
        if (struct.isSetBindToyEmail()) {
          oprot.writeFieldBegin(BIND_TOY_EMAIL_FIELD_DESC);
          oprot.writeString(struct.bindToyEmail);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetBindToyUserId()) {
        oprot.writeFieldBegin(BIND_TOY_USER_ID_FIELD_DESC);
        oprot.writeI32(struct.bindToyUserId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.createTimestamp);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastmofiyTimestamp()) {
        oprot.writeFieldBegin(LASTMOFIY_TIMESTAMP_FIELD_DESC);
        oprot.writeI32(struct.lastmofiyTimestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OaUserTupleSchemeFactory implements SchemeFactory {
    public OaUserTupleScheme getScheme() {
      return new OaUserTupleScheme();
    }
  }

  private static class OaUserTupleScheme extends TupleScheme<OaUser> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OaUser struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUserId()) {
        optionals.set(0);
      }
      if (struct.isSetUserName()) {
        optionals.set(1);
      }
      if (struct.isSetUserPassword()) {
        optionals.set(2);
      }
      if (struct.isSetUserNickName()) {
        optionals.set(3);
      }
      if (struct.isSetBindToyEmail()) {
        optionals.set(4);
      }
      if (struct.isSetBindToyUserId()) {
        optionals.set(5);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(6);
      }
      if (struct.isSetLastmofiyTimestamp()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
      if (struct.isSetUserName()) {
        oprot.writeString(struct.userName);
      }
      if (struct.isSetUserPassword()) {
        oprot.writeString(struct.userPassword);
      }
      if (struct.isSetUserNickName()) {
        oprot.writeString(struct.userNickName);
      }
      if (struct.isSetBindToyEmail()) {
        oprot.writeString(struct.bindToyEmail);
      }
      if (struct.isSetBindToyUserId()) {
        oprot.writeI32(struct.bindToyUserId);
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmofiyTimestamp()) {
        oprot.writeI32(struct.lastmofiyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OaUser struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userName = iprot.readString();
        struct.setUserNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userPassword = iprot.readString();
        struct.setUserPasswordIsSet(true);
      }
      if (incoming.get(3)) {
        struct.userNickName = iprot.readString();
        struct.setUserNickNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.bindToyEmail = iprot.readString();
        struct.setBindToyEmailIsSet(true);
      }
      if (incoming.get(5)) {
        struct.bindToyUserId = iprot.readI32();
        struct.setBindToyUserIdIsSet(true);
      }
      if (incoming.get(6)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(7)) {
        struct.lastmofiyTimestamp = iprot.readI32();
        struct.setLastmofiyTimestampIsSet(true);
      }
    }
  }

}
