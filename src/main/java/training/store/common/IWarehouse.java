package training.store.common;

import training.store.external.warehouse.*;


/**
 * Created by IntelliJ IDEA.
 * User: jpacklic
 * Date: Apr 18, 2003
 * Time: 3:56:12 PM
 * To change this template use Options | File Templates.
 */
public interface IWarehouse {
   boolean hasProductInInventory(IProduct product);

   int getCountOfProductInInventory(IProduct product);

   void restockInventory(IProduct product, int count);

   void addListener(WarehouseListener warehouseListener);

   Bin getBin(IProduct product);

   void updateProductStatus(IProduct product);
}
