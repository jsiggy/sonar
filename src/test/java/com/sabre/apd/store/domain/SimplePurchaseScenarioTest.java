/*******************************************************************************
 * @(#)TestSimplePurchaseScenario
 *
 * Copyright (c) 2002 Sabre, Inc. All rights reserved.
 ******************************************************************************/

package com.sabre.apd.store.domain;

import com.sabre.apd.store.application.*;
import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;
import org.junit.*;

public class SimplePurchaseScenarioTest {
   private StoreConfiguration storeConfiguration;
   private IOrderProcessor orderProcessing;
   private IShoppingCart shoppingCart;
   private IWarehouse warehouse;
   private Product penProduct;
   private int penInventory;

   @Before
   public void setUp() throws Exception {

      Application.setUpStoreConfiguration();
   }

   @Test
   public void testPurchaseWithSufficientInventoryAllAtOnce() throws Exception {

      IShoppingCart cart = StoreConfiguration.getShoppingCart();

   }

   @Test
   public void testPurchaseWithInsufficientInventoryAllAtOnce() throws Exception {

   }

   @Test
   public void testPurchaseWithSufficientInventoryPartialASAP() throws Exception {

   }

   @Test
   public void testPurchaseWithInsufficientInventoryPartialASAP() throws Exception {

   }
}
