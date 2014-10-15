package training.store.domain;

import training.store.common.*;
import training.store.external.warehouse.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: May 12, 2003
 * Time: 4:18:30 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class ShipmentTest extends OnlineStoreTestCase {
   private Shipment shipment;
   private Shipment shipment2;

   @Before
   public void setUp() throws Exception {
      super.setUp();
      shipment = new Shipment();
      shipment2 = new Shipment();
   }

   @Test
   public void testRemove_OneItemFound_LessQuantity() throws Exception {
      shipment.addLineItem(product1, 2);
      shipment2.addLineItem(product1, 1);
      shipment.remove(shipment2);
      assertEquals("product1 quantity", 1, shipment.getLineItems()[0].getQuantity());
   }

   @Test
   public void testRemove_OneItemFound_MoreQuantity() throws Exception {
      shipment.addLineItem(product1, 2);
      shipment2.addLineItem(product1, 3);
      try {
         shipment.remove(shipment2);
         fail("should have thrown a IllegalArgException");
      } catch (IllegalArgumentException e) {
      }
   }

   @Test
   public void testRemove_OneItemFound_SameQuantity() throws Exception {
      shipment.addLineItem(product1, 2);
      shipment2.addLineItem(product1, 2);
      shipment.remove(shipment2);
      assertEquals("line items count", 0, shipment.getLineItems().length);
   }

   @Test
   public void testRemove_OneItemNotFound() throws Exception {
      shipment2.addLineItem(product1, 1);
      shipment.remove(shipment2);
      assertEquals("line items count", 0, shipment.getLineItems().length);
   }

   @Test
   public void testRemove_ThreeItemFound() throws Exception {
      shipment.addLineItem(product1, 10);
      shipment.addLineItem(product2, 20);
      shipment.addLineItem(product3, 30);
      shipment2.addLineItem(product1, 1);
      shipment2.addLineItem(product3, 3);
      shipment.remove(shipment2);
      assertEquals("product1 quantity", 9, shipment.getLineItems()[0].getQuantity());
      assertEquals("product1 quantity", 20, shipment.getLineItems()[1].getQuantity());
      assertEquals("product1 quantity", 27, shipment.getLineItems()[2].getQuantity());
   }

   @Test
   public void testAddLineItem_DuplicateProducts() throws Exception {
      shipment.addLineItem(product1, 10);
      shipment.addLineItem(product1, 20);
      assertEquals("line item count", 1, shipment.getLineItems().length);
   }

}
