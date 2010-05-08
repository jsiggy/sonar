/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 22, 2003
 * Time: 2:55:00 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
package com.sabre.apd.store.common;

public class ProductStatus {
   public static final ProductStatus AVAILABLE = new ProductStatus("ships immediately");
   public static final ProductStatus ON_ORDER = new ProductStatus("on order");

   private final String myName; // for debug only

   private ProductStatus(String name) {
      myName = name;
   }

   public String toString() {
      return myName;
   }
}
