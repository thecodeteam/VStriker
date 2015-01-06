package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-06T09:07:47.182-0800")
@StaticMetamodel(Account.class)
public class Account_ {
	public static volatile SingularAttribute<Account, Long> accountId;
	public static volatile SingularAttribute<Account, String> accountLocation;
	public static volatile SingularAttribute<Account, Date> lastValidationDate;
	public static volatile SingularAttribute<Account, String> name;
	public static volatile SingularAttribute<Account, Boolean> validated;
}
