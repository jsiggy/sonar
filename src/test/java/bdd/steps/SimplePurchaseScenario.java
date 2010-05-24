package bdd.steps;

import com.sabre.apd.store.application.*;
import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;
import com.sabre.apd.store.domain.*;
import org.givwenzen.annotations.*;

import java.util.*;

@DomainSteps
public class SimplePurchaseScenario {

   private IWarehouse warehouse;
   private static final String QUANTITY = "(\\d+)";
   private static final String PRODUCT_NAME = "(.*)";
   private static final String DEFAULT_PRODUCT_NAME = "_product";
   private static final String SHIPPING_TYPE = PRODUCT_NAME;

   Map<String, ShippingPolicy> shippingPolicyMap = new HashMap<String, ShippingPolicy>() {
      {
         put("all at once", ShippingPolicy.ALL_AT_ONCE);
         put("ASAP", ShippingPolicy.ASAP);
      }
   };

   public SimplePurchaseScenario() {
      Application.setUpStoreConfiguration();

   }


   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products in inventory")
   public void addAndStockProduct(int quantity, String productName) throws DuplicateProductsException {
      IProduct product = createProduct(productName);
      addToShoppingCart(product);
      restockInventoryFor(quantity, product);
   }

   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products are ordered")
   public void placeAnOrderFor(int quantity, String productName) throws Exception {
      placeAnOrder(quantity, productName);
      processOrder();
   }

   @DomainStep("inventory has " + QUANTITY + " products")
   public void addProductsToInventory(int productQuantity) throws Exception {
      Product product = createProduct(DEFAULT_PRODUCT_NAME);
      addToShoppingCart(product);
      restockInventoryFor(productQuantity, product);
   }

   private void restockInventoryFor(int productQuantity, IProduct product) {
      StoreConfiguration.getWarehouse().restockInventory(product, productQuantity);
   }

   private void addToShoppingCart(IProduct product) throws DuplicateProductsException {
      StoreConfiguration.getShoppingCart().addProduct(product);
   }

   private Product createProduct(String productName) {
      return new Product(new CatalogNumber(1), productName, "a product", 0);
   }

   @DomainStep("product name is " + PRODUCT_NAME)
   public void renameTheCurrentProduct(String productName) {
      IProduct product = findProduct(DEFAULT_PRODUCT_NAME);
      if (product != null && product instanceof Product) {
         ((Product) product).setName(productName);
      }
   }

   @DomainStep("an order for " + QUANTITY + " " + PRODUCT_NAME + " products are placed")
   public void placeAnOrder(int quantity, String productName) throws Exception {

      CatalogNumber catalogNumber = findProduct(productName).getCatalogNumber();

      IShoppingCart shoppingCart = StoreConfiguration.getShoppingCart();
      shoppingCart.addItemToOrder(catalogNumber, quantity);
      //StoreConfiguration.getOrderProcessing().processOrder(shoppingCart.getOrder());
   }

   @DomainStep("with the option of shipping " + SHIPPING_TYPE)
   public void setShippingType(String shippingType) {

      ShippingPolicy shippingPolicy = shippingPolicyMap.get(shippingType);

      if (shippingPolicyMap.containsKey(shippingType)) {
         StoreConfiguration.getShoppingCart().getOrder().setShippingPolicy(shippingPolicyMap.get(shippingType));
      }
   }

   @DomainStep("order was processed")
   public void processOrder() throws Exception {
      StoreConfiguration.getOrderProcessing().processOrder(StoreConfiguration.getShoppingCart().getOrder());
   }

   @DomainStep("inventory will have " + QUANTITY + " " + PRODUCT_NAME + " products")
   public boolean updateInventory(int quantity, String productName) {

      IProduct product = findProduct(productName);
      return StoreConfiguration.getWarehouse().getCountOfProductInInventory(product) == quantity;
   }

   @DomainStep("there will be " + QUANTITY + " shipment/s")
   public boolean retrieveNumberOfShipments(int quantityOfShipments) {
      return StoreConfiguration.getShoppingCart().getOrder().getShipments().size() == quantityOfShipments;
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
