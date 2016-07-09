package com.iot.pi.recipe.interfaces;

import com.iot.pi.scales.interfaces.Scale;

public interface RecipeEnvironment {

  Display getDisplay();

  Scale getScale();
}
