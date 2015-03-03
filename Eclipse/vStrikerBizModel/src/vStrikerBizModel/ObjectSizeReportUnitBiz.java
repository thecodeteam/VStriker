package vStrikerBizModel;
import java.util.List;

import vStrikerEntities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
		entitymanager.merge( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static ObjectSizeReportUnit ObjectSizeReportUnitSelect(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ObjectSizeReportUnit act = entitymanager.find(ObjectSizeReportUnit.class,entityId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}

	public static ObservableList<ObjectSizeReportUnit> ObjectSizeReportUnitSelectAll() throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createNamedQuery("ObjectSizeReportUnit.findAll");
		@SuppressWarnings("unchecked")
		
		List <ObjectSizeReportUnit> list = qry.getResultList();
		entitymanager.close( );
		actfactory.close( );
		
		ObservableList <ObjectSizeReportUnit> list2= FXCollections.observableArrayList();
		for(ObjectSizeReportUnit u :list)
		{
				list2.add(u);
		}
		
		return list2;
	}
	
	

	
	public static void  ObjectSizeReportUnitDelete(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).commit( );

		ObjectSizeReportUnit act = entitymanager.find(ObjectSizeReportUnit.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}

}

