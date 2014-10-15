package training.store.common;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 14, 2003
 * Time: 2:51:39 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderNumberGenerator {
   private static int nextOrderId = 1;

   public synchronized static int nextOrderNumber() {
      return nextOrderId++;
   }

   public static void reset() {
      nextOrderId = 1;
   }
}
