/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: May 8, 2003
 * Time: 12:29:27 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
package training.store.common;

public class ShippingPolicy {
   public static final ShippingPolicy ALL_AT_ONCE = new ShippingPolicy("All At Once");
   public static final ShippingPolicy ASAP = new ShippingPolicy("ASAP");

   private final String myName; // for debug only

   private ShippingPolicy(String name) {
      myName = name;
   }

   public String toString() {
      return myName;
   }

   public static ShippingPolicy getInstanceFor(String s) {
      if (s.equalsIgnoreCase(ALL_AT_ONCE.toString()))
         return ShippingPolicy.ALL_AT_ONCE;
      if (s.equalsIgnoreCase(ASAP.toString()))
         return ShippingPolicy.ASAP;
      throw new IllegalArgumentException(s + " is not a valid shipping policy");
   }
}
