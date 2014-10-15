package training.store.util;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class NameAndAddressTest {
   @Test
   public void nameShouldContainBothFirstAndLast() {
      NameAndAddress nameAndAddress = new NameAndAddress("elvis", "presley");

      String name = nameAndAddress.getFullName();

      assertTrue(name.startsWith("elvis"));
      assertTrue(name.endsWith("presley"));
   }

   @Test
   public void addressShouldContainCity() {
      NameAndAddress nameAndAddress = new NameAndAddress("1409", "atlanta", "irving");

      String name = nameAndAddress.getFullAddress();

      assertTrue(name.contains("irving"));
   }
}
