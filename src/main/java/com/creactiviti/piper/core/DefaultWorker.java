package com.creactiviti.piper.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DefaultWorker implements Worker {

  @Autowired
  private Map<String, TaskHandler<?>> taskHandlers;
  
  @Lazy
  @Autowired
  private Messenger messenger;
  
  @Override
  public void handle (Task aTask) {
    TaskHandler<?> taskHandler = taskHandlers.get(aTask.getHandler());
    Assert.notNull(taskHandler,"Unknown task handler: " + aTask.getHandler());
    Object output = taskHandler.handle(aTask);
    aTask.setOutput(output);
    messenger.send("completions", aTask);
  }

  @Override
  public void cancel (String aTaskId) {
  }

}