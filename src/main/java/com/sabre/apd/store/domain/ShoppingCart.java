package com.sabre.apd.store.domain;

import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;
import com.sabre.apd.store.external.orderprocessing.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 14, 2003
 * Time: 2:05:26 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class ShoppingCart implements IShoppingCart {
   private IOrder order = new Order();
   private Map productsByCatalogId = new HashMap();
   private IOrderProcessor orderProcessing;

   public ShoppingCart() {
      orderProcessing = StoreConfiguration.getOrderProcessing();
   }

   public ShoppingCart(OrderProcessor orderProcessing) {
      this.orderProcessing = orderProcessing;
   }

   public void addItemToOrder(CatalogNumber catalogNumber, int quantity) {
      if (order.isSubmitted()) //FIXME: this method should not reset the order. We need a more explicit mechanism
         order = new Order();
      IProduct product = getProductByCatalogId(catalogNumber);
      OrderLineItem orderLineItem = new OrderLineItem(product, quantity);
      order.addLineItem(orderLineItem);
   }

   public void addProduct(IProduct product) throws DuplicateProductsException {
      if (productsByCatalogId.containsValue(product))
         throw new DuplicateProductsException("duplicate productsByCatalogId not allowed. catalog ID " + product.getCatalogNumber() + " already exists.");

      productsByCatalogId.put(product.getCatalogNumber(), product);
   }

   public IOrder getOrder() {
      return order;
   }

   public IProduct getProductByCatalogId(CatalogNumber catalogId) {
      return (IProduct) productsByCatalogId.get(catalogId);
   }

   public OrderNumber getOrderNumber() {
      return order.getOrderNumber();
   }

   public Collection getProducts() {
      return Collections.unmodifiableCollection(productsByCatalogId.values());   // todo Return unmodifiable collection
   }
}
