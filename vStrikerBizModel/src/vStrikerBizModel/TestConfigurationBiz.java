package vStrikerBizModel;
import java.util.List;

import vStrikerEntities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TestConfigurationBiz {
	public static void TestConfigurationCreate(TestConfiguration  entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void TestConfigurationUpdate(TestConfiguration entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.merge( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static TestConfiguration TestConfigurationSelect(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		TestConfiguration act = entitymanager.find(TestConfiguration.class,entityId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}
	
	public static void  TestConfigurationDelete(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		TestConfiguration act = entitymanager.find(TestConfiguration.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	
	public static List<TestConfiguration> ConfigurationTestSelectAll() throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createQuery("SELECT a From TestConfiguration a");
		@SuppressWarnings("unchecked")
		List<TestConfiguration> list = qry.getResultList();

		
		entitymanager.close( );
		actfactory.close( );
		
		return list;
	}
}
