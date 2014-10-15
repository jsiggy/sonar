package training.store.common;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: May 8, 2003
 * Time: 4:23:07 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Shipment {
   private int shipmentNumber;
   private OrderNumber orderNumber;
   private ArrayList lineItems = new ArrayList();

   public Shipment() {
   }

   public Shipment(Collection lineItems) {
      for (Iterator iterator = lineItems.iterator(); iterator.hasNext();) {
         OrderLineItem item = (OrderLineItem) iterator.next();
         addLineItem(item.getProduct(), item.getQuantity());
      }
   }

   public OrderLineItem[] getLineItems() {
      return (OrderLineItem[]) lineItems.toArray(new OrderLineItem[lineItems.size()]);
   }

   public OrderNumber getOrderNumber() {
      return orderNumber;
   }

   public int getShipmentNumber() {
      return shipmentNumber;
   }

   public void addLineItem(IProduct product, int quantity) {
      OrderLineItem lineItem = new OrderLineItem(product, quantity);
      if (lineItems.contains(lineItem)) {
         int lineItemIndex = lineItems.indexOf(lineItem);
         OrderLineItem existingLineItem = (OrderLineItem) lineItems.get(lineItemIndex);
         existingLineItem.setQuantity(existingLineItem.getQuantity() + lineItem.getQuantity());
      } else
         lineItems.add(lineItem);
   }

   public void setOrderNumber(OrderNumber orderNumber) {
      this.orderNumber = orderNumber;
   }

   public void setShipmentNumber(int shipmentNumber) {
      this.shipmentNumber = shipmentNumber;
   }

   public boolean hasLineItems() {
      return getLineItems().length > 0;
   }

   public void remove(Shipment shipment) {
      for (int i = 0; i < shipment.getLineItems().length; i++) {
         OrderLineItem itemToDeduct = shipment.getLineItems()[i];
         int indexOfItemToModify = lineItems.indexOf(itemToDeduct);
         if (indexOfItemToModify >= 0) {
            OrderLineItem itemToModify = (OrderLineItem) lineItems.get(indexOfItemToModify);
            int newQuantity = itemToModify.getQuantity() - itemToDeduct.getQuantity();
            if (newQuantity == 0)
               lineItems.remove(indexOfItemToModify);
            else if (newQuantity > 0)
               itemToModify.setQuantity(newQuantity);
            else
               throw new IllegalArgumentException("Cannot remove " + itemToDeduct + " from " + itemToModify);
         }
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("shipment={\n");
      for (int i = 0; i < getLineItems().length; i++) {
         OrderLineItem item = getLineItems()[i];
         buf.append("   [" + i + "]= ");
         buf.append(item.toString());
         buf.append("\n");
      }
      buf.append("}\n");

      return buf.toString();
   }

}
