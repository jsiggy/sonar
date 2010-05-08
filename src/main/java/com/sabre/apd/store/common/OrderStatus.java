/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 22, 2003
 * Time: 11:39:34 AM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
package com.sabre.apd.store.common;

public class OrderStatus {
   public static final OrderStatus NEW = new OrderStatus("New");
   public static final OrderStatus SHIPPED = new OrderStatus("Shipped");
   public static final OrderStatus ON_ORDER = new OrderStatus("On Order");

   private final String myName; // for debug only

   private OrderStatus(String name) {
      myName = name;
   }

   public String toString() {
      return myName;
   }

   public static Object getInstanceFor(String statusString) {
      if (statusString.equalsIgnoreCase(NEW.toString()))
         return NEW;
      if (statusString.equalsIgnoreCase(ON_ORDER.toString()))
         return ON_ORDER;
      if (statusString.equalsIgnoreCase(SHIPPED.toString()))
         return SHIPPED;
      throw new IllegalArgumentException(statusString + " is not a valid status");
   }
}
