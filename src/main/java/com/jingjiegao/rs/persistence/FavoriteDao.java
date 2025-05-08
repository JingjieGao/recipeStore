package com.jingjiegao.rs.persistence;

import com.jingjiegao.rs.entity.Favorite;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import java.util.List;

/**
 * The type Favorite dao.
 */
public class FavoriteDao extends GenericDao<Favorite> {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new Favorite dao.
     */
    public FavoriteDao() {
        super(Favorite.class);
    }

    /**
     * Gets favorites by user id.
     *
     * @param userId the user id
     * @return the favorites by user id
     */
    public List<Favorite> getFavoritesByUserId(int userId) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Favorite> query = builder.createQuery(Favorite.class);
        Root<Favorite> root = query.from(Favorite.class);
        query.select(root).where(builder.equal(root.get("user").get("id"), userId));
        List<Favorite> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }

    /**
     * Is recipe favorited by user boolean.
     *
     * @param recipeId the recipe id
     * @param userId   the user id
     * @return the boolean
     */
    public boolean isRecipeFavoritedByUser(int recipeId, int userId) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Favorite> query = builder.createQuery(Favorite.class);
        Root<Favorite> root = query.from(Favorite.class);

        query.select(root).where(
                builder.equal(root.get("recipe").get("id"), recipeId),
                builder.equal(root.get("user").get("id"), userId)
        );

        List<Favorite> results = session.createQuery(query).getResultList();
        session.close();

        return !results.isEmpty();
    }
}
