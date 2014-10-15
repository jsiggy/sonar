package training.store.external.orderprocessing;

import training.store.common.*;
import training.store.external.warehouse.Bin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA. * User: tkmower
 * Date: May 9, 2003
 * Time: 4:58:34 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class DuplicatedShippingStrategy {
   protected IWarehouse warehouse;
   protected IOrder order;
   private DuplicatedShippingStrategy nextShipper;

   public DuplicatedShippingStrategy(IOrder order, IWarehouse warehouse) {
      this.order = order;
      this.warehouse = warehouse;
   }

   public IOrder getOrder() {
      return order;
   }

   public IWarehouse getWarehouse() {
      return warehouse;
   }

   public void shipOrder() {
      Shipment toBeShipped = getEverythingThatHasNotBeenShipped();
      handleShipment(toBeShipped);
   }

   public void handleShipment(Shipment everythingThatNeedsToBeShipped) {
      Shipment remainingToBeShipped = shipWhatICan(everythingThatNeedsToBeShipped);
      letNextShipperHandleShipment(remainingToBeShipped);
   }

   protected Shipment shipWhatICan(Shipment everythingThatNeedsToBeShipped) {
      Shipment ableToShipShipment = getWhatCanBeShippedNow(everythingThatNeedsToBeShipped);

      if (shouldProcessShipment(ableToShipShipment)) {
         everythingThatNeedsToBeShipped.remove(ableToShipShipment);
         updateWarehouse(ableToShipShipment);
         getOrder().addShipment(ableToShipShipment);
      }
      return everythingThatNeedsToBeShipped;
   }

   private void letNextShipperHandleShipment(Shipment everythingThatNeedsToBeShipped) {
      if (hasNextShipper())
         getNextShipper().handleShipment(everythingThatNeedsToBeShipped);
   }

   private boolean shouldProcessShipment(Shipment ableToShipShipment) {
      return ableToShipShipment.hasLineItems();
   }

   private DuplicatedShippingStrategy getNextShipper() {
      return nextShipper;
   }

   private boolean hasNextShipper() {
      return nextShipper != null;
   }

   protected void updateWarehouse(Shipment shipment) {
      // feature envy
      for (int i = 0; i < shipment.getLineItems().length; i++) {
         OrderLineItem item = shipment.getLineItems()[i];
         getWarehouse().getBin(item.getProduct()).removeQuantity(item.getQuantity());
         getWarehouse().updateProductStatus(item.getProduct());
      }
   }

   public Shipment getWhatCanBeShippedNow(Shipment thingsToShip) {
      if (order.getShippingPolicy() == ShippingPolicy.ALL_AT_ONCE)
         return buildAllAtOnceShipment(thingsToShip);
      if (order.getShippingPolicy() == ShippingPolicy.ASAP)
         return buildPartialASAPShipment(thingsToShip);

      throw new IllegalArgumentException("Unknown shipping policy " + order.getShippingPolicy());
   }

   protected Shipment getEverythingThatHasNotBeenShipped() {
      Shipment itemsToShip = new Shipment(getOrder().getLineItems());
      removeItemsAlreadyShippedFrom(itemsToShip);
      return itemsToShip;
   }

   private void removeItemsAlreadyShippedFrom(Shipment itemsToShip) {
      for (Iterator iterator = getOrder().getShipments().iterator(); iterator.hasNext();) {
         Shipment shipment = (Shipment) iterator.next();
         itemsToShip.remove(shipment);
      }
   }

   public void setNextShipper(DuplicatedShippingStrategy shipper) {
      nextShipper = shipper;
   }

   protected Shipment buildPartialASAPShipment(Shipment thingsToShip) {
      Shipment shipment = new Shipment();
      for (int i = 0; i < thingsToShip.getLineItems().length; i++) {
         OrderLineItem item = thingsToShip.getLineItems()[i];
         int amountAvailable = getWarehouse().getCountOfProductInInventory(item.getProduct());
         if (amountAvailable > 0) {
            int amountToShip = Math.min(item.getQuantity(), amountAvailable);
            shipment.addLineItem(item.getProduct(), amountToShip);
         }
      }
      return shipment;
   }

   protected boolean canWarehouseFulfillOrder(Collection orderLineItems) {
      // feature envy
      for (Iterator iterator = orderLineItems.iterator(); iterator.hasNext();) {
         OrderLineItem item = (OrderLineItem) iterator.next();
         Bin bin = getWarehouse().getBin(item.getProduct());
         if (bin.getCount() < (item.getQuantity())) {
            return false;
         }
      }
      return true;
   }

   protected Shipment buildAllAtOnceShipment(Shipment thingsToShip) {
      Collection orderLineItems = Arrays.asList(thingsToShip.getLineItems()); //TODO: shipment.getLineItems() should return a collection
      Shipment shipment = null;
      if (canWarehouseFulfillOrder(orderLineItems))
         shipment = new Shipment(orderLineItems);
      else
         shipment = new Shipment();
      return shipment;
   }
}
