package training.store.util;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class AddressTest {
   @Test
   public void shouldContainCityInAddress() {
      Address address = new Address("1409", "atlanta", "irving");

      String actualAdress = address.getFullAddress();

      assertTrue(actualAdress.contains("irving"));
   }
}
