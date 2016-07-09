package com.iot.pi.recipe.interfaces;

import com.iot.pi.recipe.Ingredient;
import com.iot.pi.recipe.Step;

public interface Recipe {

  /**
   *
   *
   * @return
   */
  String name();

  /**
   *
   * @return
   */
  String description();

  /**
   *
   * @return
   */
  Ingredient[] ingredients();

  /**
   *
   * @return
   */
  Step[] steps();
}
