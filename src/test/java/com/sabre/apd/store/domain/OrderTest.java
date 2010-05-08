package com.sabre.apd.store.domain;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 2:43:53 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */

import com.sabre.apd.store.common.*;
import org.junit.*;

import java.util.*;

public class OrderTest {
   private Order order;
   private Product product;
   private Product product2;

   @Before
   public void setUp() {
      product = new Product(CatalogNumber.getInstanceFor(1));
      product2 = new Product(CatalogNumber.getInstanceFor(2));
      product.setPrice(100);
      order = new Order();
   }

   @Test
   public void testTotal() throws Exception {
      order.addLineItem(new OrderLineItem(product, 1));
      Assert.assertEquals("1 line item", 100, order.totalPrice());

      order.addLineItem(new OrderLineItem(product, 7));
      Assert.assertEquals("1 line item", 800, order.totalPrice());
   }

   @Test
   public void testOrderNumber() throws Exception {
      Assert.assertNotNull("initial order number not null", order.getOrderNumber());
   }

   @Test
   public void testGetLineItems() throws Exception {
      order.addLineItem(new OrderLineItem(product, 1));
      Collection items = order.getLineItems();
      Assert.assertEquals(1, items.size());
      try {
         items.add(product);
         Assert.fail("collection should be protected");
      } catch (UnsupportedOperationException e) {
      }
   }

   @Test
   public void testStatus() throws Exception {
      Assert.assertEquals("status", OrderStatus.NEW, order.getStatus());
      order.submit();
      Assert.assertEquals("after submit", OrderStatus.SHIPPED, order.getStatus());
   }

   @Test
   public void testIsAnyLineItemOnOrder() throws Exception {
      product.setStatus(ProductStatus.AVAILABLE);
      order.addLineItem(new OrderLineItem(product, 1));

      Assert.assertFalse("all avail", order.isAnyLineItemOnOrder());

      product2.setStatus(ProductStatus.ON_ORDER);
      order.addLineItem(new OrderLineItem(product2, 1));

      Assert.assertTrue("on order", order.isAnyLineItemOnOrder());
   }

   @Test
   public void testIsSubmitted_BackOrdered() throws Exception {
      order.setBackOrder();
      Assert.assertTrue(order.isSubmitted());
   }

   @Test
   public void testIsSubmitted_Submitted() throws Exception {
      order.submit();
      Assert.assertTrue(order.isSubmitted());
   }

   @Test
   public void testIsSubmitted_New() throws Exception {
      Assert.assertFalse(order.isSubmitted());
   }

   @Test
   public void testAddShipment() throws Exception {
      order.addLineItem(new OrderLineItem(product, 1));
      Shipment expectedShipment = new Shipment();
      expectedShipment.addLineItem(product, 1);

      order.addShipment(expectedShipment);

      Collection shipments = order.getShipments();
      Assert.assertEquals("expectedShipment count", 1, shipments.size());
      Shipment shipment = (Shipment) shipments.toArray()[0];
      Assert.assertSame("shipment", expectedShipment, shipment);
   }

   @Test
   public void testHowMuchHasShipped() throws Exception {
      Assert.assertEquals("0 items", 0, order.howMuchHasShipped(product));

      Shipment shipment = new Shipment();
      shipment.addLineItem(product, 1);
      order.addShipment(shipment);
      Assert.assertEquals("1 line item", 1, order.howMuchHasShipped(product));

      shipment.addLineItem(product, 3);
      Assert.assertEquals("2 line items", 4, order.howMuchHasShipped(product));

      shipment = new Shipment();
      shipment.addLineItem(product, 2);
      order.addShipment(shipment);
      Assert.assertEquals("2 shipments", 6, order.howMuchHasShipped(product));
   }

   @Test
   public void testHasEverythingShipped_AllOrNothing() throws Exception {
      order.addLineItem(new OrderLineItem(product, 1));
      Assert.assertFalse("nothing shipped", order.hasEverythingShipped());

      Shipment shipment = new Shipment();
      shipment.addLineItem(product, 1);
      order.addShipment(shipment);
      Assert.assertTrue("everything shipped", order.hasEverythingShipped());
   }

   @Test
   public void testHasEverythingShipped_Partial2Products() throws Exception {
      // todo partial
      order.addLineItem(new OrderLineItem(product, 1));
      Shipment shipment = new Shipment();
      shipment.addLineItem(product, 1);
      order.addShipment(shipment);

      order.addLineItem(new OrderLineItem(product2, 4));
      Assert.assertFalse("product2 not shipped", order.hasEverythingShipped());

      shipment = new Shipment();
      shipment.addLineItem(product2, 2);
      order.addShipment(shipment);
      Assert.assertFalse("product2 not shipped", order.hasEverythingShipped());

      shipment = new Shipment();
      shipment.addLineItem(product2, 2);
      order.addShipment(shipment);
      Assert.assertTrue("two products, both shipped", order.hasEverythingShipped());
   }

   @Test
   public void testAddLineItem() throws Exception {
      order.addLineItem(new OrderLineItem(product, 1));
      order.addLineItem(new OrderLineItem(product, 1));
      Assert.assertEquals("count of line items", 1, order.getLineItems().size());
      Assert.assertEquals("quantity", 2, ((OrderLineItem) order.getLineItems().toArray()[0]).getQuantity());
   }
}
