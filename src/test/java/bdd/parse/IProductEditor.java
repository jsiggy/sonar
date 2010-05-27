package bdd.parse;

import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;
import com.sabre.apd.store.domain.*;

import java.beans.*;

public class IProductEditor extends PropertyEditorSupport {
   public void setAsText(String productName) throws IllegalArgumentException {
      setValue(createOrFindProduct(productName));
   }

   private IProduct createOrFindProduct(String productName) {
      IProduct product = findProduct(productName);
      return product == null ? createProduct(productName) : product;
   }

   private Product createProduct(String productName) {
      return new Product(new CatalogNumber(1), productName, "a product", 0);
   }

   private IProduct findProduct(String productName) {
      IShoppingCart shoppingCart = StoreConfiguration.getShoppingCart();
      for (Object candidateProduct : shoppingCart.getProducts()) {
         Product realProduct = ((Product) candidateProduct);

         if (realProduct.getName().equals(productName)) {
            return realProduct;

         }

      }
      return null;
   }
}
