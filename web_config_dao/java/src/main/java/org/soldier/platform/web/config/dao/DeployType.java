/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.web.config.dao;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum DeployType implements org.apache.thrift.TEnum {
  Mitty(1),
  Apache(2),
  Jetty(4),
  Nginx(8);

  private final int value;

  private DeployType(int value) {
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
  public static DeployType findByValue(int value) { 
    switch (value) {
      case 1:
        return Mitty;
      case 2:
        return Apache;
      case 4:
        return Jetty;
      case 8:
        return Nginx;
      default:
        return null;
    }
  }
}
