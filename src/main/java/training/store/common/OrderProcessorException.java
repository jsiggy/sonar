package training.store.common;

public class OrderProcessorException extends Exception {
   public OrderProcessorException(String message, Exception cause) {
      super(message, cause);
   }
}
