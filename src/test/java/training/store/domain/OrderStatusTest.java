package training.store.domain;

import training.store.common.*;
import org.junit.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 23, 2003
 * Time: 9:19:00 AM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderStatusTest {
   @Test
   public void testDetermineStatus() throws Exception {
      Assert.assertEquals("new", OrderStatus.NEW, OrderStatus.getInstanceFor(OrderStatus.NEW.toString()));
      Assert.assertEquals("on order", OrderStatus.ON_ORDER, OrderStatus.getInstanceFor(OrderStatus.ON_ORDER.toString()));
      Assert.assertEquals("shipped", OrderStatus.SHIPPED, OrderStatus.getInstanceFor(OrderStatus.SHIPPED.toString()));

      try {
         OrderStatus.getInstanceFor("Not a real status");
         Assert.fail("No exception thrown");
      } catch (Exception e) {
      }
   }
}
