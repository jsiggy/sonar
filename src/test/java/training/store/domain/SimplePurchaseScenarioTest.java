/*******************************************************************************
 * @(#)TestSimplePurchaseScenario
 *
 * Copyright (c) 2002 Sabre, Inc. All rights reserved.
 ******************************************************************************/

package training.store.domain;

import training.store.application.*;
import training.store.common.*;
import org.junit.*;

public class SimplePurchaseScenarioTest {
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
