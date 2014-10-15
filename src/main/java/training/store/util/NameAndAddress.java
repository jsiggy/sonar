package training.store.util;

public class NameAndAddress {

   public String firstname;
   public String lastname;
   private String street;
   private String city;
   private String zipCode;
//   private Address address;

   public NameAndAddress(String firstname, String lastname) {
      this.firstname = firstname;
      this.lastname = lastname;
   }

public NameAndAddress(String street, String city, String zipCode) {
      this.street = street;
      this.city = city;
      this.zipCode = zipCode;
//      this.address = new Address(street, city, zipCode);
   }

   public NameAndAddress(String firstname, String lastname, String street, String city, String zipCode) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.street = street;
      this.city = city;
      this.zipCode = zipCode;
//      this.address = new Address(street, city, zipCode);
   }

   public String getFullName() {
      return firstname + " " + lastname;
   }

   public String getFullAddress() {
      return street + " " + city + " " + zipCode;
//      return address.street + " " + address.city + " " + address.zipCode;
   }
}