package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Category;
import com.jingjiegao.rs.entity.Recipe;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import java.util.List;

/**
 * The type Recipe dao.
 */
public class RecipeDao {
    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public Recipe getById(int id) {
        Session session = sessionFactory.openSession();
        Recipe recipe = session.get(Recipe.class, id);
        session.close();
        return recipe;
    }

    /**
     * Update.
     *
     * @param recipe the recipe
     */
    public void update(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(recipe);
        transaction.commit();
        session.close();
    }

    /**
     * Insert int.
     *
     * @param recipe the recipe
     * @return the int
     */
    public int insert(Recipe recipe) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(recipe);
        transaction.commit();
        id = recipe.getId();
        session.close();
        return id;
    }

    /**
     * Delete.
     *
     * @param recipe the recipe
     */
    public void delete(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(recipe);
        transaction.commit();
        session.close();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Recipe> getAll() {
        Session session = sessionFactory.openSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        List<Recipe> recipes = session.createSelectionQuery( query ).getResultList();
        logger.debug("The list of recipes " + recipes);
        session.close();
        return recipes;
    }

    /**
     * Gets by property like.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property like
     */
    public List<Recipe> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();
        logger.debug("Searching for recipe with {} = {}",  propertyName, value);
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        Expression<String> propertyPath = root.get(propertyName);
        query.where(builder.like(propertyPath, "%" + value + "%"));
        List<Recipe> recipes = session.createSelectionQuery(query).getResultList();
        session.close();
        return recipes;
    }

    /**
     * Gets by category id.
     *
     * @param categoryId the category id
     * @return the by category id
     */
    public List<Recipe> getByCategoryId(int categoryId) {
        Session session = sessionFactory.openSession();
        logger.debug("Searching for recipe with categoryId = {}", categoryId);
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root).where(builder.equal(root.get("category").get("id"), categoryId));
        List<Recipe> recipes = session.createSelectionQuery(query).getResultList();
        session.close();
        return recipes;
    }
}
