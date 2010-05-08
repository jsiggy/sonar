package com.sabre.apd.store.domain;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 9, 2003
 * Time: 3:54:19 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */

import com.sabre.apd.store.common.*;
import org.junit.*;

public class ProductTest {
   public static final CatalogNumber CATALOG_NUMBER_1 = CatalogNumber.getInstanceFor(1);

   @Test
   public void testEquals() throws Exception {
      Product productlhs = new Product(CATALOG_NUMBER_1);
      Product productrhs = new Product(CATALOG_NUMBER_1);
      Assert.assertEquals(productlhs, productrhs);
      productrhs = new Product(CatalogNumber.getInstanceFor(2));
      Assert.assertFalse(productlhs.equals(productrhs));
      Assert.assertFalse(productlhs.equals(null));
      Assert.assertFalse(productlhs.equals(new Object()));
      int catalogId = -1;
      Assert.assertFalse(productlhs.equals(new Product(CatalogNumber.getInstanceFor(catalogId))));
      Assert.assertEquals(productlhs, productlhs);
   }

   @Test
   public void testHashCode() {
      Product productlhs = new Product(CatalogNumber.getInstanceFor(666));
      Assert.assertEquals(666, productlhs.hashCode());

   }

   @Test
   public void testParse_NoDescription() throws Exception {
      Product product = Product.parse("1 Widget 49");
      Assert.assertEquals("catalog id", CATALOG_NUMBER_1, product.getCatalogNumber());
      Assert.assertEquals("name", "Widget", product.getName());
      Assert.assertEquals("price", 49, product.getPrice());
   }

   @Test
   public void testParse_WithDescription() throws Exception {
      Product product = Product.parse("1 Widget 49 \"fancy description here\"");
      Assert.assertEquals("description", "fancy description here", product.getDescription());
   }
}

