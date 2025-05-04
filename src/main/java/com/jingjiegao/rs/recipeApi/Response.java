package com.jingjiegao.rs.recipeApi;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

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
            "Response{" + 
            "meals = '" + meals + '\'' + 
            "}";
        }
}