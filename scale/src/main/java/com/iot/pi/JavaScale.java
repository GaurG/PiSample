package com.iot.pi;

import com.iot.pi.recipes.AeroPressCoffee;
import com.iot.pi.recipe.CommandLineRecipeRunner;
import com.iot.pi.recipes.CoffeeCalculator;

public class JavaScale {

  public static void main(String[] args) throws InterruptedException {
    CommandLineRecipeRunner runner = new CommandLineRecipeRunner();
    runner.runRecipe(new AeroPressCoffee(CoffeeCalculator.STRONG));
    runner.close();
  }
}
