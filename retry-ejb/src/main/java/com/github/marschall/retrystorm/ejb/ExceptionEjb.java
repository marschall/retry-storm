package com.github.marschall.retrystorm.ejb;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Stateless;

@Stateless
public class ExceptionEjb implements LocalExceptionEjb {

  private static final AtomicInteger COUNT = new AtomicInteger();

  public ExceptionEjb() {
    if (COUNT.incrementAndGet() > 5) {
      throw new RuntimeException("retry");
    }
  }

  @Override
  public void aMethod() {
    // empty
  }

}
