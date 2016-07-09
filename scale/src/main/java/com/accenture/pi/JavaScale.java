package com.accenture.pi;

import com.accenture.pi.recipes.AeroPressCoffee;
import com.accenture.pi.recipe.CommandLineRecipeRunner;
import com.accenture.pi.recipes.CoffeeCalculator;

public class JavaScale {

  public static void main(String[] args) throws InterruptedException {
    CommandLineRecipeRunner runner = new CommandLineRecipeRunner();
    runner.runRecipe(new AeroPressCoffee(CoffeeCalculator.STRONG));
    runner.close();
  }
}
