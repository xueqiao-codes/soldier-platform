/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.oa.menu.dao;

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

public class TSubMenu implements org.apache.thrift.TBase<TSubMenu, TSubMenu._Fields>, java.io.Serializable, Cloneable, Comparable<TSubMenu> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSubMenu");

  private static final org.apache.thrift.protocol.TField MENU_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("menuId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MENU_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("menuName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SYSTEM_MENU_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("systemMenuId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField MENU_SRC_FIELD_DESC = new org.apache.thrift.protocol.TField("menuSrc", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ORDER_WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("orderWeight", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestamp", org.apache.thrift.protocol.TType.I32, (short)10);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestamp", org.apache.thrift.protocol.TType.I32, (short)11);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSubMenuStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSubMenuTupleSchemeFactory());
  }

  public int menuId; // optional
  public String menuName; // optional
  public int systemMenuId; // optional
  public String menuSrc; // optional
  public int orderWeight; // optional
  public int createTimestamp; // optional
  public int lastmodifyTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MENU_ID((short)1, "menuId"),
    MENU_NAME((short)2, "menuName"),
    SYSTEM_MENU_ID((short)3, "systemMenuId"),
    MENU_SRC((short)4, "menuSrc"),
    ORDER_WEIGHT((short)5, "orderWeight"),
    CREATE_TIMESTAMP((short)10, "createTimestamp"),
    LASTMODIFY_TIMESTAMP((short)11, "lastmodifyTimestamp");

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
        case 1: // MENU_ID
          return MENU_ID;
        case 2: // MENU_NAME
          return MENU_NAME;
        case 3: // SYSTEM_MENU_ID
          return SYSTEM_MENU_ID;
        case 4: // MENU_SRC
          return MENU_SRC;
        case 5: // ORDER_WEIGHT
          return ORDER_WEIGHT;
        case 10: // CREATE_TIMESTAMP
          return CREATE_TIMESTAMP;
        case 11: // LASTMODIFY_TIMESTAMP
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
  private static final int __MENUID_ISSET_ID = 0;
  private static final int __SYSTEMMENUID_ISSET_ID = 1;
  private static final int __ORDERWEIGHT_ISSET_ID = 2;
  private static final int __CREATETIMESTAMP_ISSET_ID = 3;
  private static final int __LASTMODIFYTIMESTAMP_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.MENU_ID,_Fields.MENU_NAME,_Fields.SYSTEM_MENU_ID,_Fields.MENU_SRC,_Fields.ORDER_WEIGHT,_Fields.CREATE_TIMESTAMP,_Fields.LASTMODIFY_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MENU_ID, new org.apache.thrift.meta_data.FieldMetaData("menuId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MENU_NAME, new org.apache.thrift.meta_data.FieldMetaData("menuName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SYSTEM_MENU_ID, new org.apache.thrift.meta_data.FieldMetaData("systemMenuId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MENU_SRC, new org.apache.thrift.meta_data.FieldMetaData("menuSrc", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER_WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("orderWeight", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("createTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSubMenu.class, metaDataMap);
  }

  public TSubMenu() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSubMenu(TSubMenu other) {
    __isset_bitfield = other.__isset_bitfield;
    this.menuId = other.menuId;
    if (other.isSetMenuName()) {
      this.menuName = other.menuName;
    }
    this.systemMenuId = other.systemMenuId;
    if (other.isSetMenuSrc()) {
      this.menuSrc = other.menuSrc;
    }
    this.orderWeight = other.orderWeight;
    this.createTimestamp = other.createTimestamp;
    this.lastmodifyTimestamp = other.lastmodifyTimestamp;
  }

  public TSubMenu deepCopy() {
    return new TSubMenu(this);
  }

  @Override
  public void clear() {
    setMenuIdIsSet(false);
    this.menuId = 0;
    this.menuName = null;
    setSystemMenuIdIsSet(false);
    this.systemMenuId = 0;
    this.menuSrc = null;
    setOrderWeightIsSet(false);
    this.orderWeight = 0;
    setCreateTimestampIsSet(false);
    this.createTimestamp = 0;
    setLastmodifyTimestampIsSet(false);
    this.lastmodifyTimestamp = 0;
  }

  public int getMenuId() {
    return this.menuId;
  }

  public TSubMenu setMenuId(int menuId) {
    this.menuId = menuId;
    setMenuIdIsSet(true);
    return this;
  }

  public void unsetMenuId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MENUID_ISSET_ID);
  }

  /** Returns true if field menuId is set (has been assigned a value) and false otherwise */
  public boolean isSetMenuId() {
    return EncodingUtils.testBit(__isset_bitfield, __MENUID_ISSET_ID);
  }

  public void setMenuIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MENUID_ISSET_ID, value);
  }

  public String getMenuName() {
    return this.menuName;
  }

  public TSubMenu setMenuName(String menuName) {
    this.menuName = menuName;
    return this;
  }

  public void unsetMenuName() {
    this.menuName = null;
  }

  /** Returns true if field menuName is set (has been assigned a value) and false otherwise */
  public boolean isSetMenuName() {
    return this.menuName != null;
  }

  public void setMenuNameIsSet(boolean value) {
    if (!value) {
      this.menuName = null;
    }
  }

  public int getSystemMenuId() {
    return this.systemMenuId;
  }

  public TSubMenu setSystemMenuId(int systemMenuId) {
    this.systemMenuId = systemMenuId;
    setSystemMenuIdIsSet(true);
    return this;
  }

  public void unsetSystemMenuId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SYSTEMMENUID_ISSET_ID);
  }

  /** Returns true if field systemMenuId is set (has been assigned a value) and false otherwise */
  public boolean isSetSystemMenuId() {
    return EncodingUtils.testBit(__isset_bitfield, __SYSTEMMENUID_ISSET_ID);
  }

  public void setSystemMenuIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SYSTEMMENUID_ISSET_ID, value);
  }

  public String getMenuSrc() {
    return this.menuSrc;
  }

  public TSubMenu setMenuSrc(String menuSrc) {
    this.menuSrc = menuSrc;
    return this;
  }

  public void unsetMenuSrc() {
    this.menuSrc = null;
  }

  /** Returns true if field menuSrc is set (has been assigned a value) and false otherwise */
  public boolean isSetMenuSrc() {
    return this.menuSrc != null;
  }

  public void setMenuSrcIsSet(boolean value) {
    if (!value) {
      this.menuSrc = null;
    }
  }

  public int getOrderWeight() {
    return this.orderWeight;
  }

  public TSubMenu setOrderWeight(int orderWeight) {
    this.orderWeight = orderWeight;
    setOrderWeightIsSet(true);
    return this;
  }

  public void unsetOrderWeight() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORDERWEIGHT_ISSET_ID);
  }

  /** Returns true if field orderWeight is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderWeight() {
    return EncodingUtils.testBit(__isset_bitfield, __ORDERWEIGHT_ISSET_ID);
  }

  public void setOrderWeightIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORDERWEIGHT_ISSET_ID, value);
  }

  public int getCreateTimestamp() {
    return this.createTimestamp;
  }

  public TSubMenu setCreateTimestamp(int createTimestamp) {
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

  public TSubMenu setLastmodifyTimestamp(int lastmodifyTimestamp) {
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
    case MENU_ID:
      if (value == null) {
        unsetMenuId();
      } else {
        setMenuId((Integer)value);
      }
      break;

    case MENU_NAME:
      if (value == null) {
        unsetMenuName();
      } else {
        setMenuName((String)value);
      }
      break;

    case SYSTEM_MENU_ID:
      if (value == null) {
        unsetSystemMenuId();
      } else {
        setSystemMenuId((Integer)value);
      }
      break;

    case MENU_SRC:
      if (value == null) {
        unsetMenuSrc();
      } else {
        setMenuSrc((String)value);
      }
      break;

    case ORDER_WEIGHT:
      if (value == null) {
        unsetOrderWeight();
      } else {
        setOrderWeight((Integer)value);
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
    case MENU_ID:
      return Integer.valueOf(getMenuId());

    case MENU_NAME:
      return getMenuName();

    case SYSTEM_MENU_ID:
      return Integer.valueOf(getSystemMenuId());

    case MENU_SRC:
      return getMenuSrc();

    case ORDER_WEIGHT:
      return Integer.valueOf(getOrderWeight());

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
    case MENU_ID:
      return isSetMenuId();
    case MENU_NAME:
      return isSetMenuName();
    case SYSTEM_MENU_ID:
      return isSetSystemMenuId();
    case MENU_SRC:
      return isSetMenuSrc();
    case ORDER_WEIGHT:
      return isSetOrderWeight();
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
    if (that instanceof TSubMenu)
      return this.equals((TSubMenu)that);
    return false;
  }

  public boolean equals(TSubMenu that) {
    if (that == null)
      return false;

    boolean this_present_menuId = true && this.isSetMenuId();
    boolean that_present_menuId = true && that.isSetMenuId();
    if (this_present_menuId || that_present_menuId) {
      if (!(this_present_menuId && that_present_menuId))
        return false;
      if (this.menuId != that.menuId)
        return false;
    }

    boolean this_present_menuName = true && this.isSetMenuName();
    boolean that_present_menuName = true && that.isSetMenuName();
    if (this_present_menuName || that_present_menuName) {
      if (!(this_present_menuName && that_present_menuName))
        return false;
      if (!this.menuName.equals(that.menuName))
        return false;
    }

    boolean this_present_systemMenuId = true && this.isSetSystemMenuId();
    boolean that_present_systemMenuId = true && that.isSetSystemMenuId();
    if (this_present_systemMenuId || that_present_systemMenuId) {
      if (!(this_present_systemMenuId && that_present_systemMenuId))
        return false;
      if (this.systemMenuId != that.systemMenuId)
        return false;
    }

    boolean this_present_menuSrc = true && this.isSetMenuSrc();
    boolean that_present_menuSrc = true && that.isSetMenuSrc();
    if (this_present_menuSrc || that_present_menuSrc) {
      if (!(this_present_menuSrc && that_present_menuSrc))
        return false;
      if (!this.menuSrc.equals(that.menuSrc))
        return false;
    }

    boolean this_present_orderWeight = true && this.isSetOrderWeight();
    boolean that_present_orderWeight = true && that.isSetOrderWeight();
    if (this_present_orderWeight || that_present_orderWeight) {
      if (!(this_present_orderWeight && that_present_orderWeight))
        return false;
      if (this.orderWeight != that.orderWeight)
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
  public int compareTo(TSubMenu other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMenuId()).compareTo(other.isSetMenuId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMenuId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.menuId, other.menuId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMenuName()).compareTo(other.isSetMenuName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMenuName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.menuName, other.menuName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSystemMenuId()).compareTo(other.isSetSystemMenuId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSystemMenuId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.systemMenuId, other.systemMenuId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMenuSrc()).compareTo(other.isSetMenuSrc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMenuSrc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.menuSrc, other.menuSrc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrderWeight()).compareTo(other.isSetOrderWeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderWeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderWeight, other.orderWeight);
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
    StringBuilder sb = new StringBuilder("TSubMenu(");
    boolean first = true;

    if (isSetMenuId()) {
      sb.append("menuId:");
      sb.append(this.menuId);
      first = false;
    }
    if (isSetMenuName()) {
      if (!first) sb.append(", ");
      sb.append("menuName:");
      if (this.menuName == null) {
        sb.append("null");
      } else {
        sb.append(this.menuName);
      }
      first = false;
    }
    if (isSetSystemMenuId()) {
      if (!first) sb.append(", ");
      sb.append("systemMenuId:");
      sb.append(this.systemMenuId);
      first = false;
    }
    if (isSetMenuSrc()) {
      if (!first) sb.append(", ");
      sb.append("menuSrc:");
      if (this.menuSrc == null) {
        sb.append("null");
      } else {
        sb.append(this.menuSrc);
      }
      first = false;
    }
    if (isSetOrderWeight()) {
      if (!first) sb.append(", ");
      sb.append("orderWeight:");
      sb.append(this.orderWeight);
      first = false;
    }
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

  private static class TSubMenuStandardSchemeFactory implements SchemeFactory {
    public TSubMenuStandardScheme getScheme() {
      return new TSubMenuStandardScheme();
    }
  }

  private static class TSubMenuStandardScheme extends StandardScheme<TSubMenu> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSubMenu struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MENU_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.menuId = iprot.readI32();
              struct.setMenuIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MENU_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.menuName = iprot.readString();
              struct.setMenuNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SYSTEM_MENU_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.systemMenuId = iprot.readI32();
              struct.setSystemMenuIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MENU_SRC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.menuSrc = iprot.readString();
              struct.setMenuSrcIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ORDER_WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.orderWeight = iprot.readI32();
              struct.setOrderWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // CREATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTimestamp = iprot.readI32();
              struct.setCreateTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // LASTMODIFY_TIMESTAMP
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSubMenu struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetMenuId()) {
        oprot.writeFieldBegin(MENU_ID_FIELD_DESC);
        oprot.writeI32(struct.menuId);
        oprot.writeFieldEnd();
      }
      if (struct.menuName != null) {
        if (struct.isSetMenuName()) {
          oprot.writeFieldBegin(MENU_NAME_FIELD_DESC);
          oprot.writeString(struct.menuName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSystemMenuId()) {
        oprot.writeFieldBegin(SYSTEM_MENU_ID_FIELD_DESC);
        oprot.writeI32(struct.systemMenuId);
        oprot.writeFieldEnd();
      }
      if (struct.menuSrc != null) {
        if (struct.isSetMenuSrc()) {
          oprot.writeFieldBegin(MENU_SRC_FIELD_DESC);
          oprot.writeString(struct.menuSrc);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetOrderWeight()) {
        oprot.writeFieldBegin(ORDER_WEIGHT_FIELD_DESC);
        oprot.writeI32(struct.orderWeight);
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

  private static class TSubMenuTupleSchemeFactory implements SchemeFactory {
    public TSubMenuTupleScheme getScheme() {
      return new TSubMenuTupleScheme();
    }
  }

  private static class TSubMenuTupleScheme extends TupleScheme<TSubMenu> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSubMenu struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMenuId()) {
        optionals.set(0);
      }
      if (struct.isSetMenuName()) {
        optionals.set(1);
      }
      if (struct.isSetSystemMenuId()) {
        optionals.set(2);
      }
      if (struct.isSetMenuSrc()) {
        optionals.set(3);
      }
      if (struct.isSetOrderWeight()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTimestamp()) {
        optionals.set(5);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetMenuId()) {
        oprot.writeI32(struct.menuId);
      }
      if (struct.isSetMenuName()) {
        oprot.writeString(struct.menuName);
      }
      if (struct.isSetSystemMenuId()) {
        oprot.writeI32(struct.systemMenuId);
      }
      if (struct.isSetMenuSrc()) {
        oprot.writeString(struct.menuSrc);
      }
      if (struct.isSetOrderWeight()) {
        oprot.writeI32(struct.orderWeight);
      }
      if (struct.isSetCreateTimestamp()) {
        oprot.writeI32(struct.createTimestamp);
      }
      if (struct.isSetLastmodifyTimestamp()) {
        oprot.writeI32(struct.lastmodifyTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSubMenu struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.menuId = iprot.readI32();
        struct.setMenuIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.menuName = iprot.readString();
        struct.setMenuNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.systemMenuId = iprot.readI32();
        struct.setSystemMenuIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.menuSrc = iprot.readString();
        struct.setMenuSrcIsSet(true);
      }
      if (incoming.get(4)) {
        struct.orderWeight = iprot.readI32();
        struct.setOrderWeightIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTimestamp = iprot.readI32();
        struct.setCreateTimestampIsSet(true);
      }
      if (incoming.get(6)) {
        struct.lastmodifyTimestamp = iprot.readI32();
        struct.setLastmodifyTimestampIsSet(true);
      }
    }
  }

}

