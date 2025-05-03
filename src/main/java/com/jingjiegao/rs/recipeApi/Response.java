package com.jingjiegao.rs.recipeApi;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipes{

    @JsonProperty("meals")
    private List<MealsItem> meals;

    public void setMeals(List<MealsItem> meals){
        this.meals = meals;
    }

    public List<MealsItem> getMeals(){
        return meals;
    }

    @Override
     public String toString(){
        return 
            "Recipes{" + 
            "meals = '" + meals + '\'' + 
            "}";
        }
}