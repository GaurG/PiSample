package com.iot.pi.recipe;

import java.util.concurrent.TimeUnit;

import com.iot.pi.recipe.interfaces.Display;

public class CommandLineRecipeRunner extends RecipeRunner {

  private final Display display = new CommandLineDisplay();

  @Override
  public Display getDisplay() {
    return display;
  }

  static class CommandLineDisplay implements Display {

    @Override
    public void say(String message) {
      System.out.println(message);
    }

    @Override
    public void say(String message, Object... args) {
      System.out.format(message, args);
    }

    @Override
    public void countdown(int seconds) throws InterruptedException {
      for (int i = seconds; i > 1; i--) {
        say(i + " seconds left");
        TimeUnit.SECONDS.sleep(1);
      }
      say("1 second left");
      TimeUnit.SECONDS.sleep(1);
    }
  }
}
