package com.accenture.pi.recipe;

import com.accenture.pi.recipe.interfaces.Recipe;
import com.accenture.pi.recipe.interfaces.RecipeEnvironment;
import com.accenture.pi.scales.interfaces.Scale;
import com.accenture.pi.scales.UsbScale;

public abstract class RecipeRunner implements RecipeEnvironment {

  private Scale scale;

  @Override
  public Scale getScale() {
    return scale;
  }

  public RecipeRunner() {
    scale = UsbScale.findScale();
    if (scale == null) {
      throw new IllegalStateException("USB device not found. Check deviceId/productId in code against dmesg output.");
    }
    scale.connect();
  }

  public void runRecipe(Recipe recipe) throws InterruptedException {
    getDisplay().say(recipe.description());
    getScale().waitFor(w -> getScale().isStable());
    double val = getScale().getWeight();
    if (val != 0) {
      getDisplay().say("Please empty the scale before we begin " + val);
      getScale().waitFor(w -> w == 0);
    }

    for (Step step : recipe.steps()) {
      step.execute(this);
    }
  }

  public void close() {
    scale.close();
  }
}
