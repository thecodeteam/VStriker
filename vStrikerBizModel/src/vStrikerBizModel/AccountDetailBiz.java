package vStrikerBizModel;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vStrikerEntities.VwAccountDetail;
public class AccountDetailBiz {

	
	public static VwAccountDetail AccountSelect(long acctId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		VwAccountDetail act = entitymanager.find(VwAccountDetail.class,acctId);
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}

	public static List<VwAccountDetail> AccountSelectAll() throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createNamedQuery("VwAccountDetail.findAll",VwAccountDetail.class);
		@SuppressWarnings("unchecked")
		List<VwAccountDetail> list = qry.getResultList();
		
		entitymanager.close( );
		actfactory.close( );
		
		return list;

	}
	
	
	
	
}
