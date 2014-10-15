package training.store.external.warehouse;

import org.junit.*;

/**
 * Created by IntelliJ IDEA.
 * User: jpacklic
 * Date: Apr 30, 2003
 * Time: 3:29:39 PM
 * To change this template use Options | File Templates.
 */
public class BinTest {
   private Bin bin;

   @Before
   public void setUp() throws Exception {
      bin = new Bin(1, 5);

   }

   @Test
   public void testRemoveQuantity_ToZero() throws Exception {
      bin.removeQuantity(5);
      Assert.assertEquals("testing boundary condition decrement to zero", 0, bin.getCount());
   }

   @Test
   public void testRemoveQuantity_ToNonZero() throws Exception {
      bin.removeQuantity(4);
      Assert.assertEquals("testing boundary condition to near zero", 1, bin.getCount());
   }

   @Test
   public void testAddQuantity() throws Exception {
      bin.addQuantity(3);
      Assert.assertEquals("newQuantity", 8, bin.getCount());

   }

   @Test
   public void testRemoveQuantity_AskForTooMany() throws Exception {
      try {
         bin.removeQuantity(6);
         Assert.fail("Should throw WarehouseException Exception");
      } catch (IllegalArgumentException e) {
      }
   }

}
