package vStrikerBizModel;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vStrikerEntities.Account;
public class AccountBiz {

	public static void AccountCreate(Account acct ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( acct );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	public static void AccountUpdate(Account acct ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		entitymanager.getTransaction( ).begin( );
		entitymanager.persist( acct );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
	}
	
	public static Account AccountSelect(int acctId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Account act = entitymanager.find(Account.class,acctId);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
		return act;
	}

	public static List<Account> AccountSelectAll() throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Query qry = entitymanager.createQuery("SELECT a From Account a");
		@SuppressWarnings("unchecked")
		List<Account> list = qry.getResultList();
		
		//entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
		return list;
	}
	
	public static void  AccountDelete(int acctId ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		Account act = entitymanager.find(Account.class,acctId);
		entitymanager.remove(act);
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		actfactory.close( );
		
	}
	
	
	
}
