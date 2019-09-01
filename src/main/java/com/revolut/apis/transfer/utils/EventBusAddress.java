package com.revolut.apis.transfer.utils;

public final class EventBusAddress {

  public final static String GET_ACCOUNTS="accounts.list";
  public final static String GET_ACCOUNT="customers.get";
  public final static String ADD_ACCOUNT="account.add";
  public final static String EDIT_ACCOUNT="account.edit";
  public final static String DELETE_ACCOUNT="account.delete";

  public final static String GET_TRANSACTIONS="transactions.list";
  public final static String GET_TRANSACTION="transactions.get";
  public final static String ADD_TRANSACTION="transaction.add";
  public final static String EDIT_TRANSACTION="transaction.edit";
  public final static String DELETE_TRANSACTION="transaction.delete";

  public final static String GET_CUSTOMERS="customers.list";
  public final static String GET_CUSTOMER="customers.get";
  public final static String ADD_CUSTOMER="customer.add";
  public final static String EDIT_CUSTOMER="customer.edit";
  public final static String DELETE_CUSTOMER="customer.delete";
}
