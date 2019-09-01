package com.revolut.apis.transfer.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.codegen.annotations.DataObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
@DataObject(generateConverter = true, publicConverter = false)
public class Customer implements Serializable {

  public static Customer.CustomerBuilder builder() {
    return new Customer.CustomerBuilder();
  }

  public static final class CustomerBuilder {
    private String userName,firstName,lastName,phoneNumber,email,address, password;
    private List<Account> accounts=new ArrayList<>();
    public Customer.CustomerBuilder withUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Customer.CustomerBuilder withFistName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Customer.CustomerBuilder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }
    public Customer.CustomerBuilder withPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Customer.CustomerBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Customer.CustomerBuilder withAddress(String address) {
      this.address = address;
      return this;
    }

    public Customer.CustomerBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Customer.CustomerBuilder withAccount(Account account) {
      this.accounts.add(account);
      return this;
    }

    public Customer get() {
      Customer customer = new Customer();
      customer.firstName=firstName;
      customer.lastName=lastName;
      customer.userName=userName;
      customer.phoneNumber=phoneNumber;
      customer.email=email;
      customer.password = password;
      customer.address=address;
      customer.accounts=accounts;
      return customer;
    }
  }


  private int id;
  private String userName;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private String password;
  private String address;
  private List<Account> accounts=new ArrayList<>();
  private Customer(){
  }

  public String getUserName() {
    return userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getAddress() {
    return address;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public int getId() {
    return id;
  }
}

