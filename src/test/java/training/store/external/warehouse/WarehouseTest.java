package training.store.external.warehouse;

import training.store.common.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 16, 2003
 * Time: 3:58:02 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class WarehouseTest extends OnlineStoreTestCase {
   private boolean listenerNotified;

   @Before
   public void setUp() throws Exception {
      super.setUp();
      shoppingCart.addProduct(product1);
      shoppingCart.addProduct(product2);
      shoppingCart.addProduct(product3);
   }

   @Test
   public void testRestockInventory() throws Exception {
      warehouse.restockInventory(product1, 1);
      warehouse.restockInventory(product2, 3);
      assertEquals("bins #", 2, warehouse.getCountOfBins());
      assertEquals("product 1 count", 1, warehouse.getCountOfProductInInventory(product1));
      assertEquals("product 2 count", 3, warehouse.getCountOfProductInInventory(product2));
   }

   @Test
   public void testDrawFromInventory_NonDownloadable_EnoughInventory() throws Exception {
      assertDrawFromInventoryOk(5, 3, true, 2);
   }

   @Test
   public void testDrawFromInventory_NonDownloadable_DrawZero() throws Exception {
      assertDrawFromInventoryOk(5, 0, true, 5);
   }

   private void assertDrawFromInventoryOk(
      int initialInventory,
      int inventoryToDraw,
      boolean expectedProductAvailable,
      int expectedRemainingInventory) {
      warehouse.restockInventory(product1, initialInventory);
      Bin bin = warehouse.getBin(product1);
      bin.removeQuantity(inventoryToDraw);
      warehouse.updateProductStatus(product1);
      assertEquals("product available", expectedProductAvailable, product1.isAvailable());
      assertEquals("inventory", expectedRemainingInventory, warehouse.getCountOfProductInInventory(product1));
   }

   @Test
   public void testDrawFromInventory_NonDownloadable_NotEnoughInventory() throws Exception {
      warehouse.restockInventory(product1, 5);
      try {
         warehouse.getBin(product1).removeQuantity(6);
         warehouse.updateProductStatus(product1);
         fail("should throw negative inventory exception");
      } catch (IllegalArgumentException e) {
      }
   }

   @Test
   public void testDrawFromInventory_InvalidItem() throws Exception {
      try {
         warehouse.getBin(product1).removeQuantity(1);
         warehouse.updateProductStatus(product1);
         fail("Should throw exception for non existant bin");
      } catch (IllegalArgumentException e) {
      }
   }

   @Test
   public void testAddToBin_NegativeValue() throws Exception {
      try {
         warehouse.restockInventory(product1, -1);
         fail("should throw invalid argument exception");
      } catch (IllegalArgumentException e) {
      }
   }

   @Test
   public void testGetBins() throws Exception {
      warehouse.restockInventory(product1, 2);
      warehouse.restockInventory(product2, 2);
      warehouse.restockInventory(product3, 2);
      assertEquals("bin count", 3, warehouse.getBins().size());

   }

   @Test
   public void testCanRemoveFromBin_NonDownloadable() throws Exception {
      warehouse.restockInventory(product1, 5);
      Bin bin = warehouse.getBin(product1);
      assertTrue("should be able to remove 5 items", bin.getCount() >= 5);
   }

   @Test
   public void testProductStatus() throws Exception {
      warehouse.restockInventory(product1, 0);

      assertTrue("on order", product1.isOnOrder());
      assertFalse("not available", product1.isAvailable());
      warehouse.restockInventory(product1, 1);
      assertTrue("available", product1.isAvailable());
      assertFalse("not on order", product1.isOnOrder());
   }

   @Test
   public void testRestockInventory_NotifyListeners() throws Exception {
      listenerNotified = false;
      warehouse.addListener(new WarehouseListener() {
         public void warehouseUpdated() {
            listenerNotified = true;
         }
      });
      warehouse.restockInventory(product1, 1);
      assertTrue("no notification given", listenerNotified);
   }

/* jss
   @Test
   public void shouldLeaveStatusAloneWhenProductIsNull() {
      warehouse.updateProductStatus(null);

      assertFalse("on order", product1.isOnOrder());
      assertTrue("not available", product1.isAvailable());
   }
*/

}
