package com.iot.pi.recipe.interfaces;

public interface Display {

    void say(String message);

    void say(String message, Object... args);

    void countdown(int seconds) throws InterruptedException;
}
