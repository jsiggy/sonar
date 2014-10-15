package training.store.application;


import training.store.configuration.*;
import training.store.domain.*;
import training.store.external.orderprocessing.*;
import training.store.external.warehouse.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: May 2, 2003
 * Time: 5:13:00 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Application {
   public static void setUpStoreConfiguration() {
      StoreConfiguration storeConfiguration = new StoreConfiguration();
      storeConfiguration.setOrderProcessing(new OrderProcessor());
      storeConfiguration.setShoppingCart(new ShoppingCart());
      storeConfiguration.setWarehouse(new Warehouse());
   }

}
