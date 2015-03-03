package vStrikerBizModel;
import java.util.List;

import vStrikerEntities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ApiSelectedBiz {
	public static void ApiSelectCreate(ApiSelected  entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void ApiSelectUpdate(ApiSelected entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static ApiSelected ApiSelectSelect(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ApiSelected act = entitymanager.find(ApiSelected.class,entityId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}
	
	public static void  ApiSelectDelete(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		ApiSelected act = entitymanager.find(ApiSelected.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static List<VwApiSelectedDetail> ApiSelectedSelectByConfTempID(long id ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		
		Query qry = entitymanager.createQuery("SELECT a from VwApiSelectedDetail a WHERE a.configurationTemplateId= :Id", VwApiSelectedDetail.class);
		qry.setParameter("Id", id);
		@SuppressWarnings("unchecked")
		List<VwApiSelectedDetail> acct = (List<VwApiSelectedDetail>) qry.getResultList();
		entitymanager.close( );
		actfactory.close( );
		return acct;
	}

	public static List<VwApiSelectedDetail> ApiSelectedSelectByTestID(long id ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		
		Query qry = entitymanager.createQuery("SELECT a from VwApiSelectedDetail a WHERE a.testconfigurationId= :Id", VwApiSelectedDetail.class);
		qry.setParameter("Id", id);
		@SuppressWarnings("unchecked")
		List<VwApiSelectedDetail> acct = (List<VwApiSelectedDetail>) qry.getResultList();
		entitymanager.close( );
		actfactory.close( );
		return acct;
	}
	

}
