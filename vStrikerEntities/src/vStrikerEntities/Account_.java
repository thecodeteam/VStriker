package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-30T13:37:44.826-0800")
@StaticMetamodel(Account.class)
public class Account_ {
	public static volatile SingularAttribute<Account, Long> accountId;
	public static volatile SingularAttribute<Account, String> accountLocation;
	public static volatile SingularAttribute<Account, Date> lastValidationDate;
	public static volatile SingularAttribute<Account, String> name;
	public static volatile SingularAttribute<Account, Boolean> validated;
}
