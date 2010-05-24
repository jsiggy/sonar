package com.sabre.apd.store.common;

import com.sabre.apd.store.application.*;
import com.sabre.apd.store.configuration.*;
import com.sabre.apd.store.domain.*;
import org.junit.*;

/**
 * Created by IntelliJ IDEA.
 * User: jeff.mcguire
 * Date: May 21, 2003
 * Time: 3:12:04 PM
 * To change this template use Options | File Templates.
 * The purpose of this class is to demonstrate the interactions between the user interface layer
 * and the domain layer of the architecture.  It is not intended to be an exhaustive set of tests.
 */
public class ClientLayerTest {
   private IProduct product1;
   private IProduct product2;
   private IProduct product3;
   private IShoppingCart shoppingCart;
   private IOrderProcessor orderProcessing;
   private IWarehouse warehouse;

   @Before
   public void setUp() throws Exception {
      Application.setUpStoreConfiguration();
      warehouse = StoreConfiguration.getWarehouse();
      orderProcessing = StoreConfiguration.getOrderProcessing();
      shoppingCart = StoreConfiguration.getShoppingCart();
      product1 = new Product(CatalogNumber.getInstanceFor(1), "Product1", "product 1", 100);
      product2 = new Product(CatalogNumber.getInstanceFor(2), "Product2", "product 2", 200);
      product3 = new Product(CatalogNumber.getInstanceFor(3), "Product3", "product 3", 300);
   }

   @org.junit.Test
   public void testClient() throws Exception {
      addSomeItemsToTheCatalog();
      browseCatalogItems();
      initiateAnOrder();
   }

   public void addSomeItemsToTheCatalog() throws Exception {
      shoppingCart.addProduct(product1);
      shoppingCart.addProduct(product2);
      shoppingCart.addProduct(product3);
      warehouse.restockInventory(product1, 1);
      warehouse.restockInventory(product2, 1);
      warehouse.restockInventory(product3, 1);
      Assert.assertEquals(3, shoppingCart.getProducts().size());
   }

   public void browseCatalogItems() {
      IProduct foundProduct = shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(1));
      Assert.assertEquals("id 1", product1, foundProduct);
      foundProduct = shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(2));
      Assert.assertEquals("id 2", product2, foundProduct);
      foundProduct = shoppingCart.getProductByCatalogId(CatalogNumber.getInstanceFor(3));
      Assert.assertEquals("id 3", product3, foundProduct);
   }

   public void initiateAnOrder() throws Exception {
      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(1), 1);
      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(2), 1);
      shoppingCart.addItemToOrder(CatalogNumber.getInstanceFor(3), 1);
      shoppingCart.checkOut();
      Assert.assertEquals("3 items", 600, shoppingCart.getOrder().totalPrice());
      Assert.assertEquals("order status", OrderStatus.SHIPPED, shoppingCart.getOrder().getStatus());
   }
}
