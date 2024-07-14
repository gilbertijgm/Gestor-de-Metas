/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Meta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.User;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author gilbe
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public UserJpaController() {
        emf = Persistence.createEntityManagerFactory("metasPU");
    }

    public void create(User user) {
        if (user.getMetas() == null) {
            user.setMetas(new ArrayList<Meta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Meta> attachedMetas = new ArrayList<Meta>();
            for (Meta metasMetaToAttach : user.getMetas()) {
                metasMetaToAttach = em.getReference(metasMetaToAttach.getClass(), metasMetaToAttach.getId_meta());
                attachedMetas.add(metasMetaToAttach);
            }
            user.setMetas(attachedMetas);
            em.persist(user);
            for (Meta metasMeta : user.getMetas()) {
                User oldUserOfMetasMeta = metasMeta.getUser();
                metasMeta.setUser(user);
                metasMeta = em.merge(metasMeta);
                if (oldUserOfMetasMeta != null) {
                    oldUserOfMetasMeta.getMetas().remove(metasMeta);
                    oldUserOfMetasMeta = em.merge(oldUserOfMetasMeta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getId_usuario());
            List<Meta> metasOld = persistentUser.getMetas();
            List<Meta> metasNew = user.getMetas();
            List<Meta> attachedMetasNew = new ArrayList<Meta>();
            for (Meta metasNewMetaToAttach : metasNew) {
                metasNewMetaToAttach = em.getReference(metasNewMetaToAttach.getClass(), metasNewMetaToAttach.getId_meta());
                attachedMetasNew.add(metasNewMetaToAttach);
            }
            metasNew = attachedMetasNew;
            user.setMetas(metasNew);
            user = em.merge(user);
            for (Meta metasOldMeta : metasOld) {
                if (!metasNew.contains(metasOldMeta)) {
                    metasOldMeta.setUser(null);
                    metasOldMeta = em.merge(metasOldMeta);
                }
            }
            for (Meta metasNewMeta : metasNew) {
                if (!metasOld.contains(metasNewMeta)) {
                    User oldUserOfMetasNewMeta = metasNewMeta.getUser();
                    metasNewMeta.setUser(user);
                    metasNewMeta = em.merge(metasNewMeta);
                    if (oldUserOfMetasNewMeta != null && !oldUserOfMetasNewMeta.equals(user)) {
                        oldUserOfMetasNewMeta.getMetas().remove(metasNewMeta);
                        oldUserOfMetasNewMeta = em.merge(oldUserOfMetasNewMeta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = user.getId_usuario();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId_usuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<Meta> metas = user.getMetas();
            for (Meta metasMeta : metas) {
                metasMeta.setUser(null);
                metasMeta = em.merge(metasMeta);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
