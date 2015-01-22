package vStrikerUnitTest;
import java.util.List;

import vStrikerEntities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vStrikerBizModel.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class vStrikerEntitesUnitTest {

	@Test
	public void test() {
	/*	TestConfigTemp();
		TestAccountCreate();
		TestAccountDetail();*/
		testReportExecutionData();
	}
	
	public void testReportExecutionData()
	{
		
		long i=1;
		try {
			vStrikerEntities.ExecutionReport rpt = vStrikerBizModel.ExecutionReportBiz.ExecutionReportSelect(i);
			vStrikerEntities.ExecutionReportData data = new ExecutionReportData();
			data.setDataKey("Put");
			data.setDataValue("100");
			data.setExecutionReport(rpt);
			vStrikerBizModel.ExecutionReportDataBiz.ExecutionReportDataCreate(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void TestConfigTemp()
	{
		try
		{
			long id =1;
			EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
			EntityManager entitymanager = actfactory.createEntityManager( );
			ConfigurationTemplate act = entitymanager.find(ConfigurationTemplate.class,id);

			entitymanager.close( );
			actfactory.close( );
			
			System.out.println(act.getConfigurationTemplateId()+"-"+act.getConfTempName());
		}
	catch(Exception e)
		
		{
			
			e.printStackTrace();
		}
		
	}
	public void TestAccountCreate()
	{
		Account acct = new Account();
		Account acct2 = new Account();
		
		/********** Acct **********/
		acct.setAccountLocation("Boston");
		acct.setName("ECS Boston");
		acct.setValidated(false);
		acct.setLastValidationDate(null);
		
		/********** Acct2 **********/
		acct2.setAccountLocation("Boston");
		acct2.setName("ECS Boston");
		acct2.setValidated(false);
		acct2.setLastValidationDate(null);	
		
		
		
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( acct );
		entitymanager.getTransaction( ).commit( );
		entitymanager.close( );
		actfactory.close( );
	
		EntityManagerFactory actfactory2 = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager2 = actfactory2.createEntityManager( );
		entitymanager2.getTransaction( ).begin( );
		entitymanager2.persist( acct2 );
		entitymanager2.getTransaction( ).commit( );
		entitymanager2.close( );
		actfactory2.close( );
	
		assertNotSame(acct,acct2);
		assertSame(acct.getName(),acct2.getName());
		
		
		
	}
	
	public void TestAccountDetail()
	{
		try
		{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createNamedQuery("VwAccountDetail.findAll",VwAccountDetail.class);
		@SuppressWarnings("unchecked")
		List<VwAccountDetail> list = qry.getResultList();
		
		entitymanager.close( );
		actfactory.close( );
		
		for(VwAccountDetail l : list)
			System.out.println(l.getAccountId()+"-"+l.getName());
		}
		catch(Exception e)
		
		{
			
			System.out.println(e.getMessage());
		}
		
	}
}
