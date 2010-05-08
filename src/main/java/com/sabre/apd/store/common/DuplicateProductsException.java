package com.sabre.apd.store.common;


/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 15, 2003
 * Time: 5:24:59 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class DuplicateProductsException extends Exception {
   public DuplicateProductsException(String message) {
      super(message);
   }
}
