package training.store.domain;

import training.store.common.*;
import training.store.external.orderprocessing.*;
import training.store.external.warehouse.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 16, 2003
 * Time: 9:08:28 AM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class ShoppingCartTest extends OnlineStoreTestCase {
   private OrderProcessor orderProcessing;

   @Before
   public void setUp() throws Exception {
      super.setUp();
      orderProcessing = new OrderProcessor();
      shoppingCart.addProduct(product1);
   }

   @Test
   public void testAddCatalogItem() throws Exception {
      shoppingCart.addProduct(product2);
      assertEquals(2, shoppingCart.getProducts().size());
      Iterator iterator = shoppingCart.getProducts().iterator();
      iterator.next();
      assertEquals(product2, iterator.next());
   }

   @Test
   public void testAddDuplicateCatalogItem() throws Exception {
      shoppingCart.addProduct(product2);

      try {
         shoppingCart.addProduct(product2);
         fail("should throw exception for duplicate");
      } catch (training.store.common.DuplicateProductsException e) {

      }
   }

   @Test
   public void testTotal() throws Exception {
      int totalPrice = 0;
      assertEquals("no items", totalPrice, shoppingCart.getOrder().totalPrice());

      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(CATALOG_NUMBER_1), 2);
      totalPrice += (product1.getPrice() * 2);
      assertEquals("2 items", totalPrice, shoppingCart.getOrder().totalPrice());

      shoppingCart.addProduct(product2);
      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(CATALOG_NUMBER_2), 3);
      totalPrice += (product2.getPrice() * 3);
      assertEquals("multiple items", totalPrice, shoppingCart.getOrder().totalPrice());
   }

   @Test
   public void testAddItemToOrder() throws Exception {
      warehouse.restockInventory(shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(CATALOG_NUMBER_1)), 1);
      int totalPrice = 0;
      assertEquals("empty order", totalPrice, shoppingCart.getOrder().totalPrice());

      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(CATALOG_NUMBER_1), 1);
      totalPrice += product1.getPrice();
      assertEquals("1 item in the order", totalPrice, shoppingCart.getOrder().totalPrice());

      orderProcessing.processOrder(shoppingCart.getOrder());
      totalPrice = 0;
      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(CATALOG_NUMBER_1), 2);
      totalPrice += (product1.getPrice() * 2);
      assertEquals("2 items in the order", totalPrice, shoppingCart.getOrder().totalPrice());
   }

   @Test
   public void testGetProductByCatalogId() throws Exception {
      assertNull("no product in catalog", shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(CATALOG_NUMBER_2)));
      shoppingCart.addProduct(product2);
      IProduct foundProduct = shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(CATALOG_NUMBER_2));
      assertEquals("id 2", product2, foundProduct);

      IProduct product32 = new Product(CatalogNumber.getInstanceFor(32), "32", "32", 100);
      shoppingCart.addProduct(product32);
      assertEquals("id 32", product32, shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(32)));
   }

}
