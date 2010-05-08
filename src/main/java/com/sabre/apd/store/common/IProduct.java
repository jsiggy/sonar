package com.sabre.apd.store.common;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 14, 2003
 * Time: 3:05:23 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public interface IProduct {
   CatalogNumber getCatalogNumber();

   String getName();

   String getDescription();

   int getPrice();

   void setStatus(ProductStatus status);

   boolean isOnOrder();

   boolean isAvailable();

   void setDownloadable(boolean downloadable);

   boolean isDownloadable();


}
