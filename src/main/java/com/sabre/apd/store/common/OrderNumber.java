package com.sabre.apd.store.common;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 30, 2003
 * Time: 3:16:58 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderNumber {
   private int id;

   public OrderNumber() {
      this.id = OrderNumberGenerator.nextOrderNumber();
   }

   public OrderNumber(String s) {
      this.id = Integer.parseInt(s);
   }

   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof OrderNumber)) return false;

      final OrderNumber orderNumber = (OrderNumber) o;

      if (id != orderNumber.id) return false;

      return true;
   }

   public int hashCode() {
      return id;
   }

   public int intValue() {
      return id;
   }
}
