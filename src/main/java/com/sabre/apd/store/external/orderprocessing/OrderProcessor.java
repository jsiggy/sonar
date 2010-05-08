package com.sabre.apd.store.external.orderprocessing;

import com.sabre.apd.store.common.*;
import com.sabre.apd.store.configuration.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 4:36:58 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderProcessor implements IOrderProcessor, WarehouseListener {
   protected Map deferredOrdersByOrderNumber = new HashMap();
   protected Collection completedOrders = new ArrayList();

   public OrderProcessor() {
   }

   public boolean hasOrder(IOrder order) {
      return deferredOrdersByOrderNumber.values().contains(order) || completedOrders.contains(order);
   }

   public void processOrder(IOrder order) throws OrderProcessorException {
      if (!order.isSubmitted())
         fillOrder(order);
   }

   private void fillOrder(IOrder order) {

      ShippingStrategy shipper = new ShippingStrategy(order, StoreConfiguration.getWarehouse());
      shipper.shipOrder();

      if (order.hasEverythingShipped()) {
         completeOrder(order);
      } else {
         deferOrder(order);
      }
   }

   protected void deferOrder(IOrder order) {
      StoreConfiguration.getWarehouse().addListener(this);
      deferredOrdersByOrderNumber.put(order.getOrderNumber(), order);
      completedOrders.remove(order);
      order.setBackOrder();
   }

   private void completeOrder(IOrder order) {
      deferredOrdersByOrderNumber.remove(order.getOrderNumber());
      completedOrders.add(order);
      order.shipped();
   }

   public Collection getDeferredOrders() {
      return deferredOrdersByOrderNumber.values();
   }

   public Collection getCompletedOrders() {
      return Collections.unmodifiableCollection(completedOrders);
   }

   public void warehouseUpdated() {
      for (Iterator iterator = getDeferredOrders().iterator(); iterator.hasNext();) {
         IOrder order = (IOrder) iterator.next();
         resubmitOrder(order);
      }
   }

   protected void resubmitOrder(IOrder order) {
      fillOrder(order);
   }
}
