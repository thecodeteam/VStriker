package vStrikerBizModel;
import java.util.ArrayList;
import java.util.List;

import vStrikerEntities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ExecutionReportDataBiz {
	public static void ExecutionReportDataCreate(ExecutionReportData  entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void ExecutionReportDataUpdate(ExecutionReportData entity ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.merge( entity );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static ExecutionReportData ExecutionReportDataSelect(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ExecutionReportData act = entitymanager.find(ExecutionReportData.class,entityId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}

	public static List<ExecutionReportData> ExecutionReportDataSelectByRprtID(long rptd ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createQuery("SELECT e FROM ExecutionReportData e Where executionReportId=:id");
		qry.setParameter("id", rptd);
		
		@SuppressWarnings("unchecked")
		List<ExecutionReportData> list = qry.getResultList();
		
		entitymanager.close( );
		actfactory.close( );
		
		return list;
	}
	
	public static void  ExecutionReportDataDelete(long entityId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		ExecutionReportData act = entitymanager.find(ExecutionReportData.class,entityId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static List<String> ExecutionReportDataGetApiList(List<ExecutionReportData> data)
	{
		List<String> list = new ArrayList<String>();
		String str="";
		
		for(ExecutionReportData a:data)
		{
			if(!a.getDataKey().equalsIgnoreCase(str))
			{
				str=a.getDataKey();
				list.add(str);
			}
		}
		
		return list;
	}
	
	public static List<String> ExecutionReportDataGetApiCrudList(List<ExecutionReportData> data, String api,String crud)
	{
		List<String> list = new ArrayList<String>();
		
		for(ExecutionReportData a:data)
		{
			if(a.getDataKey().equalsIgnoreCase(api)&&a.getCrudValue().equalsIgnoreCase(crud))
			{
				
				list.add(a.getDataValue());
			}
		}
		
		return list;
	}
	
}
