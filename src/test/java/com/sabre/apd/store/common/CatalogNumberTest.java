package com.sabre.apd.store.common;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 30, 2003
 * Time: 3:11:54 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */

import org.junit.*;

public class CatalogNumberTest {
   @Test
   public void testEquals() throws Exception {
      CatalogNumber lhs = CatalogNumber.getInstanceFor(1);
      CatalogNumber rhs = CatalogNumber.getInstanceFor(2);
      Assert.assertFalse("different", lhs.equals(rhs));
      Assert.assertEquals("self", lhs, lhs);
      Assert.assertFalse("null", lhs.equals(null));

   }

   @Test
   public void testHashcode() throws Exception {
      CatalogNumber lhs = CatalogNumber.getInstanceFor(1);
      CatalogNumber rhs = CatalogNumber.getInstanceFor(2);
      Assert.assertFalse("different", lhs.hashCode() == rhs.hashCode());
      Assert.assertEquals("self", lhs.hashCode(), lhs.hashCode());
   }
}
