package com.accenture.pi.recipe.interfaces;

import com.accenture.pi.recipe.Ingredient;
import com.accenture.pi.recipe.Step;

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
