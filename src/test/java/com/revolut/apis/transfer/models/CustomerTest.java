package com.revolut.apis.transfer.models;


import io.vertx.junit5.VertxExtension;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class CustomerTest {

  @Test
  public void customerBuilderTest(){
    Customer customer=Customer.builder().get();
    Assert.assertTrue(customer.getEmail()==null);
  }

  @Test
  public void customerBuildWithEmailTest(){
    String email = "gasmyr@me.com";
    Customer customer=Customer.builder().withEmail(email).get();
    Assert.assertTrue(customer.getEmail().equalsIgnoreCase(email));
  }

  @Test
  public void customerBuilderFullTest(){
    String email = "myemail@email.com";
    String my_address = "My Address";
    String phoneNumber = "30100010101";
    String fn = "FN";
    String ln = "LN";

    Customer customer=Customer.builder()
      .withAddress(my_address).withEmail(email)
      .withPhoneNumber(phoneNumber)
      .withFistName(fn).withLastName(ln).withUserName("user1").withPassword("pass").withAccount(null)
      .get();

    Assert.assertTrue(customer.
      getEmail().equalsIgnoreCase(email));
    Assert.assertTrue(customer.getAddress().equalsIgnoreCase(my_address));
    Assert.assertTrue(customer.getPhoneNumber().equalsIgnoreCase(phoneNumber));
    Assert.assertTrue(customer.getLastName().equalsIgnoreCase(ln));
    Assert.assertTrue(customer.getFirstName().equalsIgnoreCase(fn));
  }


}
