package com.sabre.apd.store.configuration;

import com.sabre.apd.store.application.*;
import com.sabre.apd.store.external.warehouse.*;
import org.junit.*;

/*
 * Created by IntelliJ IDEA.
 * User: jpacklic
 * Date: Apr 16, 2003
 * Time: 7:18:57 PM
 * To change this template use Options | File Templates.
 */

public class WarehouseFactoryTest {

   @Test
   public void testGetCatalog() throws Exception {
      StoreConfiguration storeConfiguration = new StoreConfiguration();
      Application.setUpStoreConfiguration();
      Assert.assertSame(storeConfiguration.getWarehouse(), storeConfiguration.getWarehouse());
      Warehouse newWarehouse = new Warehouse();
      StoreConfiguration.setWarehouse(newWarehouse);
      Assert.assertSame(newWarehouse, storeConfiguration.getWarehouse());
   }
}
