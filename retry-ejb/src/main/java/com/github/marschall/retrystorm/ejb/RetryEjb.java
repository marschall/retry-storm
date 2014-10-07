package com.github.marschall.retrystorm.ejb;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class RetryEjb {
  
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  
  @Resource
  private TimerService timerService;
  private TimerConfig timerConfig;
  
  
  @PostConstruct
  public void initialize() {
    this.timerConfig = new TimerConfig("RetryEjb", false);
    this.scheduleTimer();
  }
  
  private void scheduleTimer() {
    this.timerService.createSingleActionTimer(SECONDS.toMillis(10L), timerConfig);
  }
  
  @Timeout
  public void onTimeout() {
    LOG.info("onTimeout");
    this.scheduleTimer();
    throw new RuntimeException("retry");
  }

}
