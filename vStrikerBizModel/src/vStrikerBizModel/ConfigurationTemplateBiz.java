package vStrikerBizModel;
import java.util.List;

import vStrikerEntities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ConfigurationTemplateBiz {
	public static void ConfigurationTemplateCreate(ConfigurationTemplate  entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void ConfigurationTemplateUpdate(ConfigurationTemplate entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.merge( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static ConfigurationTemplate ConfigurationTemplateSelect(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ConfigurationTemplate act = entitymanager.find(ConfigurationTemplate.class,entityId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}
	
	public static void  ConfigurationTemplateDelete(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		ConfigurationTemplate act = entitymanager.find(ConfigurationTemplate.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}

	public static List<ConfigurationTemplate> ConfigurationTemplateSelectAll() throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createNamedQuery("ConfigurationTemplate.findAll",ConfigurationTemplate.class);
		@SuppressWarnings("unchecked")
		List<ConfigurationTemplate> list = qry.getResultList();
		
		entitymanager.close( );
		actfactory.close( );
		
		return list;
	}
}
