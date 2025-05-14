package com.jingjiegao.rs.restful;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Rest app.
 */
@ApplicationPath("/api")
public class RestApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.add(RecipeApiService.class);
        return classSet;
    }
}