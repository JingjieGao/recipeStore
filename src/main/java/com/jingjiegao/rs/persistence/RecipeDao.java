package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Recipe;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import java.util.List;

/**
 * The type Recipe dao.
 */
public class RecipeDao extends GenericDao<Recipe> {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new Recipe dao.
     */
    public RecipeDao() {
        super(Recipe.class);
    }

    /**
     * Gets by category id.
     *
     * @param categoryId the category id
     * @return the by category id
     */
    public List<Recipe> getByCategoryId(int categoryId) {
        Session session = getSession();
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