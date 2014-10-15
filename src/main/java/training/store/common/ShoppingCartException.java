package training.store.common;

/**
 * Created by IntelliJ IDEA.
 * User: tkmower
 * Date: Apr 17, 2003
 * Time: 5:19:58 PM
 * Copyright (c) 2003 Sabre, Inc. All rights reserved.
 */
public class ShoppingCartException extends Exception {
   public ShoppingCartException(String message, Exception cause) {
      super(message, cause);
   }
}
