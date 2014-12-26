package vStrikerBizModel;
import vStrikerEntities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectSizeReportUnitBiz {
	public static void ObjectSizeReportUnitCreate(ObjectSizeReportUnit  entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void ObjectSizeReportUnitUpdate(ObjectSizeReportUnit entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static ObjectSizeReportUnit ObjectSizeReportUnitSelect(int entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ObjectSizeReportUnit act = entitymanager.find(ObjectSizeReportUnit.class,entityId);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}
	
	public static void  ObjectSizeReportUnitDelete(int entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ObjectSizeReportUnit act = entitymanager.find(ObjectSizeReportUnit.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}

}
