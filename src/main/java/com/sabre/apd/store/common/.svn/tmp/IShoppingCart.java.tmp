package com.sabre.apd.store.common;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 14, 2003
 * Time: 1:52:08 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public interface IShoppingCart {
   void addItemToOrder(CatalogNumber catalogNumber, int quantity);

   IOrder getOrder();

   void addProduct(IProduct product) throws DuplicateProductsException;

   Collection getProducts();

   IProduct getProductByCatalogId(CatalogNumber catalogNumber);

   void checkOut() throws ShoppingCartException;
}
