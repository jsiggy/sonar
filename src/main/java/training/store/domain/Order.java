package training.store.domain;

import training.store.common.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 2:45:40 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Order implements IOrder {
   private List orderLineItems = new LinkedList();

   private OrderNumber orderNumber = new OrderNumber();
   private OrderStatus orderStatus = OrderStatus.NEW;
   private ArrayList shipments = new ArrayList();
   private ShippingPolicy shippingPolicy = ShippingPolicy.ALL_AT_ONCE;

   public int totalPrice() {
      int total = 0;
      for (Iterator iterator = orderLineItems.iterator(); iterator.hasNext();) {
         OrderLineItem item = (OrderLineItem) iterator.next();
         total += item.getTotalPrice();
      }
      return total;
   }

   public void addLineItem(OrderLineItem orderLineItem) {
      if (orderLineItems.contains(orderLineItem)) {
         int lineItemIndex = orderLineItems.indexOf(orderLineItem);
         OrderLineItem existingLineItem = (OrderLineItem) orderLineItems.get(lineItemIndex);
         existingLineItem.setQuantity(existingLineItem.getQuantity() + orderLineItem.getQuantity());
      } else
         orderLineItems.add(orderLineItem);
   }

   public Collection getLineItems() {
      return Collections.unmodifiableCollection(orderLineItems);
   }

   public OrderNumber getOrderNumber() {
      return orderNumber;
   }

   public void submit() {
      orderStatus = OrderStatus.SHIPPED;
   }

   public boolean isSubmitted() {
      return OrderStatus.NEW != orderStatus;
   }

   public OrderStatus getStatus() {
      return orderStatus;
   }

   public void setBackOrder() {
      orderStatus = OrderStatus.ON_ORDER;
   }

   public void shipped() {
      orderStatus = OrderStatus.SHIPPED;
   }

   public boolean isAnyLineItemOnOrder() {
      for (Iterator iterator = orderLineItems.iterator(); iterator.hasNext();) {
         OrderLineItem lineItem = (OrderLineItem) iterator.next();
         if (lineItem.isOnOrder())
            return true;
      }
      return false;
   }

   public Collection getShipments() {
      return shipments;
   }

   public ShippingPolicy getShippingPolicy() {
      return shippingPolicy;
   }

   public void setShippingPolicy(ShippingPolicy shippingPolicy) {
      this.shippingPolicy = shippingPolicy;
   }

   public void addShipment(Shipment shipment) {
      shipments.add(shipment);
      shipment.setShipmentNumber(shipments.size());
      shipment.setOrderNumber(getOrderNumber());
   }

   public int howMuchHasShipped(IProduct product) {
      int totalShipped = 0;
      for (Iterator iterator = shipments.iterator(); iterator.hasNext();) {
         Shipment shipment = (Shipment) iterator.next();
         OrderLineItem matchingLineItem = getOrderLineItemFromShipmentForProduct(shipment, product);
         if (matchingLineItem != null) {
            totalShipped += matchingLineItem.getQuantity();
         }
      }
      return totalShipped;
   }

   private OrderLineItem getOrderLineItemFromShipmentForProduct(Shipment shipment, IProduct product) {
      OrderLineItem matchingLineItem = null;
      for (int i = 0; i < shipment.getLineItems().length; i++) {
         OrderLineItem orderLineItem = shipment.getLineItems()[i];
         if (orderLineItem.getProduct().equals(product))
            matchingLineItem = orderLineItem;
      }
      return matchingLineItem;
   }

   public boolean hasEverythingShipped() {
      for (Iterator iterator = orderLineItems.iterator(); iterator.hasNext();) {
         OrderLineItem iOrderLineItem = (OrderLineItem) iterator.next();
         if (iOrderLineItem.getQuantity() != howMuchHasShipped(iOrderLineItem.getProduct()))
            return false;
      }
      return true;
   }

}
