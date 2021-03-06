/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.file_storage_info.dao;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum AccessAttribute implements org.apache.thrift.TEnum {
  PublicRead(0),
  PrivateRead(1);

  private final int value;

  private AccessAttribute(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static AccessAttribute findByValue(int value) { 
    switch (value) {
      case 0:
        return PublicRead;
      case 1:
        return PrivateRead;
      default:
        return null;
    }
  }
}
