package com.sabre.apd.store.domain;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 2:37:59 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */

import com.sabre.apd.store.common.*;
import org.junit.*;

public class OrderLineItemTest {
   @Test
   public void testTotal() throws Exception {
      Product product = new Product(CatalogNumber.getInstanceFor(1));
      product.setPrice(100);
      OrderLineItem lineItem = new OrderLineItem(product, 1);
      Assert.assertEquals("qty 1", 100, lineItem.getTotalPrice());

      lineItem = new OrderLineItem(product, 3);
      Assert.assertEquals("qty 3", 300, lineItem.getTotalPrice());
   }
}
