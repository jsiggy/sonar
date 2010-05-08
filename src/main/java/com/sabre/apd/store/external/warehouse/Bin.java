package com.sabre.apd.store.external.warehouse;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 17, 2003
 * Time: 4:41:32 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class Bin {
   private int id;
   private int count;

   public Bin(int catalogId, int count) {
      this.id = catalogId;
      this.count = count;
   }

   public int getCount() {
      return count;
   }

   public void removeQuantity(int count) {
      if (getCount() < count)
         throw new IllegalArgumentException("Unable to remove " + count + " items from bin id " + getId());
      this.count = getCount() - count;
   }

   public void addQuantity(int count) {
      this.count = getCount() + count;
   }

   public int getId() {
      return id;
   }

}
