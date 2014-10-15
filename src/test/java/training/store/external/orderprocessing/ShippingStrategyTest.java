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
 * Date: May 15, 2003
 * Time: 4:21:31 PM
 * To change this template use Options | File Templates.
 */
public class ShippingStrategyTest extends OnlineStoreTestCase {
   protected ShippingStrategy shipper;
   protected IOrder order;
   public static final int QUANTITY_ORDERED = 3;
   private Shipment everythingThatNeedsToBeShipped;

   @Before
   public void setUp() throws Exception {
      super.setUp();
      shoppingCart.addProduct(product2);
      order = new Order();
      order.addLineItem(new OrderLineItem(product2, QUANTITY_ORDERED));
      assertFalse(product2.isDownloadable());
      order.setShippingPolicy(ShippingPolicy.ASAP);
      shipper = new ShippingStrategy(order, StoreConfiguration.getWarehouse());
   }

   protected Collection assertShipmentCreatedAfterRestockingAndShipOrder(
      int expectedShipmentCount,
      int inventoryAmount) {
      warehouse.restockInventory(product2, inventoryAmount);
      shipper.shipOrder();

      Collection shipments = order.getShipments();
      assertEquals("shipment count", expectedShipmentCount, shipments.size());
      return shipments;
   }

   @Test
   public void testHandleShipment() throws Exception {
      TestShipper shipper = new TestShipper(order, warehouse);
      everythingThatNeedsToBeShipped = new Shipment();
      everythingThatNeedsToBeShipped.addLineItem(product2, 1);
      shipper.handleShipment(everythingThatNeedsToBeShipped);
      assertEquals("call log", "getWhatCanBeShippedNow updateWarehouse ", shipper.getCallLog());
   }

   @Test
   public void testShipWhatICan_EmptyShipment() throws Exception {
      TestShipper shipper = new TestShipper(order, warehouse);
      everythingThatNeedsToBeShipped = new Shipment();
      shipper.shipWhatICan(everythingThatNeedsToBeShipped);
      assertEquals("order shipments", 0, order.getShipments().size());
      assertEquals("call log", "getWhatCanBeShippedNow ", shipper.getCallLog());
   }

   @Test
   public void testShipWhatICan_NonEmptyShipment() throws Exception {
      TestShipper shipper = new TestShipper(order, warehouse);
      everythingThatNeedsToBeShipped = new Shipment();
      everythingThatNeedsToBeShipped.addLineItem(product2, 1);
      OrderLineItem expectedLineItem = everythingThatNeedsToBeShipped.getLineItems()[0];
      Shipment remainingToShip = shipper.shipWhatICan(everythingThatNeedsToBeShipped);
      assertEquals("order shipments", 1, order.getShipments().size());
      Shipment shipment = (Shipment) order.getShipments().toArray()[0];
      assertEquals("shipment line items", 1, shipment.getLineItems().length);
      assertEquals("line item", expectedLineItem, shipment.getLineItems()[0]);
      assertEquals("remaining shipment items", 0, remainingToShip.getLineItems().length);
      assertEquals("call log", "getWhatCanBeShippedNow updateWarehouse ", shipper.getCallLog());
   }

   @Test
   public void testShipOrder_NothingInInventory() throws Exception {
      assertShipmentCreatedAfterRestockingAndShipOrder(0, 0);
      assertFalse("everything shipped", order.hasEverythingShipped());
   }

   @Test
   public void testShipOrder_OrderQuantityMatchesInventoryBoundaryCondition() throws Exception {
      assertShipmentCreatedAfterRestockingAndShipOrder(1, QUANTITY_ORDERED);
      assertTrue("everything shipped", order.hasEverythingShipped());
   }

   @Test
   public void testShipOrder_PartialShipments() throws Exception {
      Collection shipments = assertShipmentCreatedAfterRestockingAndShipOrder(1, 2);

      Shipment shipment = (Shipment) shipments.toArray()[0];
      assertEquals("line items in 1st shipment", 1, shipment.getLineItems().length);

      assertShipmentCreatedAfterRestockingAndShipOrder(2, 2);

      assertTrue("everything shipped", order.hasEverythingShipped());
   }

   @Test
   public void testShipOrder_WithoutInventory() throws Exception {
      assertShipmentCreatedAfterRestockingAndShipOrder(0, 0);
   }

   @Test
   public void testShipOrder_WithInventory() throws Exception {
      assertShipmentCreatedAfterRestockingAndShipOrder(1, QUANTITY_ORDERED);

      Shipment shipment = (Shipment) order.getShipments().toArray()[0];
      assertEquals("line item count", 1, shipment.getLineItems().length);
      OrderLineItem lineItem = shipment.getLineItems()[0];
      assertEquals("product", product2, lineItem.getProduct());
      assertEquals("quantity", QUANTITY_ORDERED, lineItem.getQuantity());
   }

   private class TestShipper extends ShippingStrategy {
      private StringBuffer buffer = new StringBuffer();
      private Shipment newShipment;

      public TestShipper(IOrder order, IWarehouse warehouse) {
         super(order, warehouse);
      }

      protected void updateWarehouse(Shipment shipment) {
         assertEquals("not called with result of getWhatCanBeShippedNow", newShipment, shipment);
         buffer.append("updateWarehouse ");
      }

      public Shipment getWhatCanBeShippedNow(Shipment thingsToShip) {
         assertEquals("shipment", everythingThatNeedsToBeShipped, thingsToShip);
         buffer.append("getWhatCanBeShippedNow ");
         newShipment = new Shipment(Arrays.asList(thingsToShip.getLineItems()));
         return newShipment;
      }

      protected Shipment shipWhatICan(Shipment everythingThatNeedsToBeShipped) {
         return super.shipWhatICan(everythingThatNeedsToBeShipped);
      }

      public Object getCallLog() {
         return buffer.toString();
      }
   }
}
