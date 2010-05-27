package bdd.steps;

import com.sabre.apd.store.application.*;
import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;
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

   @DomainStep("an order with " + QUANTITY + " " + PRODUCT_NAME + " products")
   public void createAnOrderWith(int quantity, IProduct product) throws Exception {
      addAndStockProduct(0, product);
      addProductToOrder(quantity, product);
   }

   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products have been shipped")
   public void placeAndMakeOrderShippable(int quantity, IProduct product) throws OrderProcessorException {
      restockInventoryFor(quantity, product);
      processOrder();
   }

   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products in inventory")
   public void addAndStockProduct(int quantity, IProduct product) throws DuplicateProductsException {
      addToShoppingCart(product);
      restockInventoryFor(quantity, product);
   }

   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products are ordered")
   public void placeAnOrderFor(int quantity, IProduct productName) throws Exception {
      addProductToOrder(quantity, productName);
      processOrder();
   }

   private void processOrder() throws OrderProcessorException {
      StoreConfiguration.getOrderProcessing().processOrder(StoreConfiguration.getShoppingCart().getOrder());
   }

   private void addProductToOrder(int quantity, IProduct product) {
      CatalogNumber catalogNumber = product.getCatalogNumber();
      IShoppingCart shoppingCart = StoreConfiguration.getShoppingCart();
      shoppingCart.addItemToOrder(catalogNumber, quantity);
   }

   @DomainStep("with the option of shipping " + SHIPPING_TYPE)
   public void setShippingType(String shippingType) {

      ShippingPolicy shippingPolicy = shippingPolicyMap.get(shippingType);

      if (shippingPolicyMap.containsKey(shippingType)) {
         StoreConfiguration.getShoppingCart().getOrder().setShippingPolicy(shippingPolicyMap.get(shippingType));
      }
   }

   @DomainStep("inventory will have " + QUANTITY + " " + PRODUCT_NAME + " products")
   public boolean updateInventory(int quantity, IProduct product) {
      return StoreConfiguration.getWarehouse().getCountOfProductInInventory(product) == quantity;
   }

   @DomainStep("there will be " + QUANTITY + " shipment/s")
   public boolean retrieveNumberOfShipments(int quantityOfShipments) {
      return StoreConfiguration.getShoppingCart().getOrder().getShipments().size() == quantityOfShipments;
   }

   @DomainStep(QUANTITY + " " + PRODUCT_NAME + " products are restocked in inventory")
   public void restockInventoryFor(int productQuantity, IProduct product) {
      StoreConfiguration.getWarehouse().restockInventory(product, productQuantity);
   }

   private void addToShoppingCart(IProduct product) throws DuplicateProductsException {
      if (!StoreConfiguration.getShoppingCart().getProducts().contains(product)) {
         StoreConfiguration.getShoppingCart().addProduct(product);
      }
   }
}
