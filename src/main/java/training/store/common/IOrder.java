package training.store.common;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 14, 2003
 * Time: 2:19:56 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public interface IOrder {
   OrderNumber getOrderNumber();

   ShippingPolicy getShippingPolicy();

   int totalPrice();

   void setShippingPolicy(ShippingPolicy shippingPolicy);

   void addLineItem(OrderLineItem orderLineItem);

   Collection getLineItems();

   void addShipment(Shipment shipment);

   Collection getShipments();

   void submit();

   void setBackOrder();

   void shipped();

   OrderStatus getStatus();

   boolean isSubmitted();

   boolean isAnyLineItemOnOrder();

   boolean hasEverythingShipped();

   int howMuchHasShipped(IProduct product);
}
