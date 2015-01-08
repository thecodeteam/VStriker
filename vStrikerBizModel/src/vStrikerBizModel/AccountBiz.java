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
		List<Account> listOfAccounts = AccountSelectAll();
		for(Account a: listOfAccounts) {
			if (a.getName().equalsIgnoreCase(acct.getName()) && a.getAccountLocation().equalsIgnoreCase(acct.getAccountLocation())) {
				throw new Exception("Existing account");
			}
		}
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

	public static Account AccountSelect(String acctName, String acctLocation ) throws Exception 
	{
		EntityManagerFactory actfactory = Persistence.createEntityManagerFactory( "vStrikerEntities" );
		EntityManager entitymanager = actfactory.createEntityManager( );
		
		Query qry = entitymanager.createQuery("SELECT a from Account a WHERE a.name= :name AND a.accountLocation= :location", Account.class);
		qry.setParameter("name", acctName);
		qry.setParameter("location", acctLocation);
		@SuppressWarnings("unchecked")
		Account acct = (Account) qry.getSingleResult();
		entitymanager.close( );
		actfactory.close( );
		return acct;
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
