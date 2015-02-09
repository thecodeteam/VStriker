package vStrikerBizModel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vStrikerEntities.Account;
import vStrikerEntities.Api;

public class ApiBiz {

	public static void ApiCreate(Api entity) throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		entitymanager.persist(entity);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		actfactory.close();

	}

	public static void ApiUpdate(Api entity) throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		entitymanager.merge(entity);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		actfactory.close();
	}

	public static Api ApiSelect(long entityId) throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		Api act = entitymanager.find(Api.class, (int) entityId);

		entitymanager.close();
		actfactory.close();

		return act;
	}

	public static void ApiDelete(long entityId) throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Api act = entitymanager.find(Api.class, (int) entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		actfactory.close();

	}

	public static void ApiDeleteByAcount(long entityId) throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Query qry = entitymanager.createQuery(
				"Delete  from Api a WHERE a.accountId= :Id", Api.class);
		qry.setParameter("Id", entityId);
		qry.executeUpdate();
		entitymanager.getTransaction().commit();

		entitymanager.close();
		actfactory.close();

	}

	public static List<Api> ApiSelectforAccount(Account entityId)
			throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();

		Query qry = entitymanager.createQuery(
				"SELECT a from Api a WHERE a.account= :Id", Api.class);
		qry.setParameter("Id", entityId);
		@SuppressWarnings("unchecked")
		List<Api> apis = qry.getResultList();
		entitymanager.close();
		actfactory.close();
		return apis;
	}

	public static List<Api> ApiSelectAll() throws Exception {
		EntityManagerFactory actfactory = Persistence
				.createEntityManagerFactory("vStrikerEntities");
		EntityManager entitymanager = actfactory.createEntityManager();
		Query qry = entitymanager.createQuery("SELECT a From Api a");
		@SuppressWarnings("unchecked")
		List<Api> list = qry.getResultList();

		entitymanager.close();
		actfactory.close();

		return list;
	}

}
