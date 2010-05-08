package com.sabre.apd.store.common;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 16, 2003
 * Time: 2:53:25 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public interface IOrderProcessor {
   void processOrder(IOrder order) throws OrderProcessorException;

   boolean hasOrder(IOrder order);
}
