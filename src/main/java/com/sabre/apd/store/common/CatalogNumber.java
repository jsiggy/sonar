package com.sabre.apd.store.common;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 30, 2003
 * Time: 4:19:26 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class CatalogNumber {
   private int id;

   public CatalogNumber(int id) {
      this.id = id;
   }

   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof CatalogNumber)) return false;

      final CatalogNumber catalogNumber = (CatalogNumber) o;

      if (id != catalogNumber.id) return false;

      return true;
   }

   public int hashCode() {
      return id;
   }

   public int intValue() {
      return id;
   }

   public static CatalogNumber getInstanceFor(int catalogId) {
      return new CatalogNumber(catalogId);   //hook for optional instance pooling later
   }

   public String toString() {
      return "CatalogNumber(" + this.id + ")";
   }

   public static CatalogNumber getInstanceFor(String s) {
      return getInstanceFor(Integer.parseInt(s));
   }

}
