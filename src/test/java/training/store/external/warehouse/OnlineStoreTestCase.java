/*
 *  Copyright (c) 2002 Sabre, Inc. All rights reserved.
 */
package training.store.external.warehouse;

import training.store.application.*;
import training.store.common.*;
import training.store.configuration.*;
import training.store.domain.*;
import org.junit.*;

public abstract class OnlineStoreTestCase {
   public static final int CATALOG_NUMBER_1 = 1;
   public static final int CATALOG_NUMBER_2 = 2;
   public static final int CATALOG_NUMBER_3 = 3;
   public static final int INVALID_CATALOG_NUMBER = 100000;
   protected Warehouse warehouse;
   protected IShoppingCart shoppingCart;
   protected Product product1;
   protected Product product2;
   protected Product product3;

   @Before
   public void setUp() throws Exception {
      Application.setUpStoreConfiguration();
      warehouse = (Warehouse) StoreConfiguration.getWarehouse();
      shoppingCart = StoreConfiguration.getShoppingCart();
      product1 = new Product(CatalogNumber.getInstanceFor(1), "Product1", "product 1", 100);
      product2 = new Product(CatalogNumber.getInstanceFor(2), "Product2", "product 2", 200);
      product3 = new Product(CatalogNumber.getInstanceFor(3), "Product3", "product 3", 300);
   }
}
