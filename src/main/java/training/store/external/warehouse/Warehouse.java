package training.store.external.warehouse;

import training.store.common.*;
import training.store.configuration.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User:
 * Date: Apr 16, 2003
 * Time: 3:48:15 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Warehouse implements IWarehouse {
   private Map quantityByBinId = new HashMap();
   private WarehouseListener listener;

   public long getCountOfBins() {
      return quantityByBinId.size();
   }

   public void restockInventory(IProduct product, int count) {
      if (count < 0)
         throw new IllegalArgumentException("count must be greater than or equal to 0");
      getOrCreateBin(product).addQuantity(count);
      updateProductStatus(product);
      // todo  let someone know there is inventory available

      if (listener != null)
         listener.warehouseUpdated();
   }

   public int getCountOfProductInInventory(IProduct product) {
      return getBin(product).getCount();
   }

   private Bin getOrCreateBin(IProduct product) {
      Bin bin = null;
      if (hasProductInInventory(product)) {
         bin = getBin(product);
      } else {
         bin = new Bin(product.getCatalogNumber().intValue(), 0);
         addBin(bin);
      }
      return bin;
   }

   public boolean hasProductInInventory(IProduct product) {
      return quantityByBinId.containsKey(getKeyForBin(product));
   }

   public Bin getBin(IProduct product) {
      Bin bin = (Bin) quantityByBinId.get(getKeyForBin(product));
      if (bin == null)
         throw new IllegalArgumentException("Bin id " + product.getCatalogNumber().intValue() + " is invalid for warehouse.");
      return bin;
   }

   private Object getKeyForBin(IProduct product) {
      return product;
   }

   private static IProduct getProductForBin(CatalogNumber catalogNumber) {
      return new StoreConfiguration().getShoppingCart().getProductByCatalogId(catalogNumber);
   }

   private void addBin(Bin bin) {
      CatalogNumber catalogNumber = CatalogNumber.getInstanceFor(bin.getId());
      IProduct product = getProductForBin(catalogNumber);
      quantityByBinId.put(getKeyForBin(product), bin);
   }

   public void updateProductStatus(IProduct product) {
      if (product == null)
         return;  // todo  Throw exception?

      if (isProductAvailable(product)) // TODO: problem with order depleting all inventory will be flagged as backordered?
      {
         product.setStatus(ProductStatus.AVAILABLE);
      } else {
         product.setStatus(ProductStatus.ON_ORDER);
      }
   }

   private boolean isProductAvailable(IProduct product) {
      return getCountOfProductInInventory(product) > 0;
   }

   public Collection getBins() {
      return quantityByBinId.values();
   }

   public void addListener(WarehouseListener warehouseListener) {
      listener = warehouseListener;
   }

}
