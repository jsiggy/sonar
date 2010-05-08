package com.sabre.apd.store.domain;

import com.sabre.apd.store.common.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 9, 2003
 * Time: 3:24:23 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Product extends Object implements IProduct {
   private String name;
   private String description;
   private int price;
   private CatalogNumber catalogId;
   private boolean downloadable = false;
   private ProductStatus status = ProductStatus.AVAILABLE;

   public Product(CatalogNumber catalogId) {
      this.catalogId = catalogId;
   }

   public Product(CatalogNumber catalogId, String name, String description, int price) {
      this(catalogId);
      setName(name);
      setDescription(description);
      setPrice(price);
   }

   public void setStatus(ProductStatus status) {
      this.status = status;
   }

   public boolean isDownloadable() {
      return downloadable;
   }

   public CatalogNumber getCatalogNumber() {
      return catalogId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public static Product parse(String string) {
      StringTokenizer stringTokenizer = new StringTokenizer(string, " ");

      String token = stringTokenizer.nextToken();
      Product product = new Product(CatalogNumber.getInstanceFor(token));

      token = stringTokenizer.nextToken();
      product.setName(token);
      token = stringTokenizer.nextToken();
      product.setPrice(Integer.parseInt(token));

      String description = "";
      while (stringTokenizer.hasMoreTokens())
         description = description + stringTokenizer.nextToken().replace('\"', ' ').trim() + " ";

      product.setDescription(description.trim());

      return product;
   }

   public boolean isOnOrder() {
      return status == ProductStatus.ON_ORDER;
   }

   public boolean isAvailable() {
      return status == ProductStatus.AVAILABLE;
   }

   public void setDownloadable(boolean downloadable) {
      this.downloadable = downloadable;
   }

   public ProductStatus getStatus() {
      return status;
   }

   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof IProduct)) return false;

      final Product product = (Product) o;

      return catalogId.equals(product.catalogId);
   }

   public int hashCode() {
      return catalogId.hashCode();
   }

}
