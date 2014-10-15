package training.store.common;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 10, 2003
 * Time: 2:38:39 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class OrderLineItem {
   private IProduct product;
   private int quantity;

   public OrderLineItem(IProduct product, int quantity) {
      this.product = product;
      this.quantity = quantity;
   }

   public int getTotalPrice() {
      return product.getPrice() * quantity;
   }

   public CatalogNumber getCatalogNumber() {
      return product.getCatalogNumber();
   }

   public IProduct getProduct() {
      return product;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public boolean isOnOrder() {
      return product.isOnOrder();
   }

   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof OrderLineItem)) return false;

      final OrderLineItem orderLineItem = (OrderLineItem) o;

      if (!product.equals(orderLineItem.product)) return false;

      return true;
   }

   public int hashCode() {
      int result;
      result = product.hashCode();
      result = 29 * result + quantity;
      return result;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(String.valueOf(quantity));
      buffer.append(" of ");
      buffer.append(String.valueOf(getCatalogNumber().intValue()));
      return buffer.toString();
   }
}
