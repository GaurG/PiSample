package com.accenture.pi.recipe.interfaces;

import com.accenture.pi.scales.interfaces.Scale;

public interface RecipeEnvironment {

  Display getDisplay();

  Scale getScale();
}
