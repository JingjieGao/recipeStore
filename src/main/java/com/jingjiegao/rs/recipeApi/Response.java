package com.jingjiegao.rs.recipeApi;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Response.
 */
public class Response{

    @JsonProperty("meals")
    private List<MealsItem> meals;

    /**
     * Set meals.
     *
     * @param meals the meals
     */
    public void setMeals(List<MealsItem> meals){
        this.meals = meals;
    }

    /**
     * Get meals list.
     *
     * @return the list
     */
    public List<MealsItem> getMeals(){
        return meals;
    }

    @Override
     public String toString(){
        return 
            "Response{" + 
            "meals = '" + meals + '\'' + 
            "}";
        }
}