package com.sabre.apd.store.configuration;

import com.sabre.apd.store.common.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 16, 2003
 * Time: 1:07:53 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class StoreConfiguration {
   private static IOrderProcessor orderProcessing;
   private static IWarehouse warehouse;
   private static IShoppingCart shoppingCart;


   public StoreConfiguration() {
   }

   public static IShoppingCart getShoppingCart() {
      return shoppingCart;
   }

   public void setShoppingCart(IShoppingCart shoppingCart) {
      StoreConfiguration.shoppingCart = shoppingCart;
   }

   public static IWarehouse getWarehouse() {
      return warehouse;
   }

   public static void setWarehouse(IWarehouse warehouse) {
      StoreConfiguration.warehouse = warehouse;
   }

   public static void setOrderProcessing(IOrderProcessor orderProcessing) {
      StoreConfiguration.orderProcessing = orderProcessing;
   }

   public static synchronized IOrderProcessor getOrderProcessing() {
      return orderProcessing;
   }
}
