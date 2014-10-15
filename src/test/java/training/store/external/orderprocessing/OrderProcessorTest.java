package training.store.external.orderprocessing;

import training.store.common.*;
import training.store.configuration.*;
import training.store.domain.*;
import training.store.external.warehouse.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 4:50:45 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderProcessorTest extends OnlineStoreTestCase {
   private IOrder order;
   private TestOrderProcessing orderProcessing;

   @Before
   public void setUp() throws Exception {
      super.setUp();
      shoppingCart.addProduct(product1);
      shoppingCart.addProduct(product2);
      OrderNumberGenerator.reset();
      order = new Order();
      orderProcessing = new TestOrderProcessing();
      StoreConfiguration.getWarehouse().addListener(orderProcessing);

   }

   @Test
   public void testHasOrder() throws Exception {
      assertFalse(orderProcessing.hasOrder(order));
      orderProcessing.processOrder(order);

      assertTrue(orderProcessing.hasOrder(order));
   }

   @Test
   public void testProcessOrder_WarehouseHasEnough() throws Exception {
      warehouse.restockInventory(product1, 2);
      warehouse.restockInventory(product2, 3);

      OrderLineItem orderLineItem1 = new OrderLineItem(product1, 1);
      OrderLineItem orderLineItem2 = new OrderLineItem(product2, 3);

      order.addLineItem(orderLineItem1);
      order.addLineItem(orderLineItem2);

      orderProcessing.processOrder(order);
      assertEquals(1, warehouse.getCountOfProductInInventory(product1));
      assertEquals(0, warehouse.getCountOfProductInInventory(product2));

      // todo test warehouse supply exhausted

      // test calling processOrder twice
      orderProcessing.processOrder(order);

      assertEquals("order status", OrderStatus.SHIPPED, order.getStatus());
      assertFalse("order still active", orderProcessing.getDeferredOrders().contains(order));
      assertNotNull("shipments", order.getShipments());
      assertEquals("shipments count", 1, order.getShipments().size());
      Collection shipments = order.getShipments();
      Shipment shipment = (Shipment) shipments.iterator().next();
      assertEquals("item # in shipment", 2, shipment.getLineItems().length);
      assertEquals("item 1", orderLineItem1, shipment.getLineItems()[0]);
      assertEquals("item 2", orderLineItem2, shipment.getLineItems()[1]);

      assertEquals("product1 inventory not changed", 1, warehouse.getCountOfProductInInventory(product1));
      assertEquals("product2 inventory not changed", 0, warehouse.getCountOfProductInInventory(product2));

      // todo test warehouse counts still the same if 2nd or later item fails
   }

   @Test
   public void testProcessOrder_WarehouseDoesntHaveEnough() throws Exception {
      warehouse.restockInventory(product1, 2);
      warehouse.restockInventory(product2, 2);


      order.addLineItem(new OrderLineItem(product1, 1));
      order.addLineItem(new OrderLineItem(product2, 3));

      orderProcessing.processOrder(order);
      assertEquals("order status", OrderStatus.ON_ORDER, order.getStatus());
      assertTrue("order not active", orderProcessing.getDeferredOrders().contains(order));

      assertEquals("product1 inventory changed", 2, warehouse.getCountOfProductInInventory(product1));
      assertEquals("product2 inventory changed", 2, warehouse.getCountOfProductInInventory(product2));
   }

   @Test
   public void testProcessOrder_NotEnoughInventory() throws Exception {
      warehouse.restockInventory(product1, 1);
      order.addLineItem(new OrderLineItem(product1, 2));
      orderProcessing.processOrder(order);

      assertFalse("should not be completed", orderProcessing.getCompletedOrders().contains(order));
      assertTrue("not active", orderProcessing.getDeferredOrders().contains(order));
   }

   @Test
   public void testWarehouseUpdated_1OrderActive() throws Exception {
      warehouse.restockInventory(product1, 2);

      order.addLineItem(new OrderLineItem(product1, 1));
      orderProcessing.deferOrder(order);
      orderProcessing.warehouseUpdated();

      assertEquals("order status", OrderStatus.SHIPPED, order.getStatus());
   }

   @Test
   public void testWarehouseUpdated_3OrderActive() throws Exception {
      final Collection resubmittedOrders = new ArrayList();
      orderProcessing = new TestOrderProcessing() {
         protected void resubmitOrder(IOrder resubmittedOrder) {
            resubmittedOrders.add(resubmittedOrder);
         }
      };
      Order order1 = new Order();
      orderProcessing.deferOrder(order1);
      Order order2 = new Order();
      orderProcessing.deferOrder(order2);
      Order order3 = new Order();
      orderProcessing.deferOrder(order3);

      orderProcessing.warehouseUpdated();

      assertEquals("resubmitted count", 3, resubmittedOrders.size());
      assertTrue("order1", resubmittedOrders.contains(order1));
      assertTrue("order2", resubmittedOrders.contains(order2));
      assertTrue("order3", resubmittedOrders.contains(order3));
   }

   private class TestOrderProcessing extends OrderProcessor {
      protected void deferOrder(IOrder order) {
         super.deferOrder(order);
      }
   }

   @Test
   public void testProcessOrder_BuildPartialShipment() throws Exception {
      order.addLineItem(new OrderLineItem(product2, 3));
      order.setShippingPolicy(ShippingPolicy.ASAP);

      restockAndProcessOrder(0);
      assertOrderDeferred(true);
      restockAndProcessOrder(2);
      assertOrderDeferred(true);

      warehouse.restockInventory(product2, 2);
      // notice that processOrder() does not need to be called..

      assertOrderDeferred(false);
   }

   private void restockAndProcessOrder(int restockCount) throws OrderProcessorException {
      warehouse.restockInventory(product2, restockCount);
      orderProcessing.processOrder(order);
   }

   private void assertOrderDeferred(boolean deferred) {
      assertEquals("is in deferred", deferred, orderProcessing.getDeferredOrders().contains(order));
      assertEquals("is in completed list", !deferred, orderProcessing.getCompletedOrders().contains(order));
      assertEquals("everything shipped", !deferred, order.hasEverythingShipped());
   }
}
