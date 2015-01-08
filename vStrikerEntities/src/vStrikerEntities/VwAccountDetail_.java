package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-06T21:25:59.142-0800")
@StaticMetamodel(VwAccountDetail.class)
public class VwAccountDetail_ {
	public static volatile SingularAttribute<VwAccountDetail, String> id;
	public static volatile SingularAttribute<VwAccountDetail, Integer> accountId;
	public static volatile SingularAttribute<VwAccountDetail, String> accountLocation;
	public static volatile SingularAttribute<VwAccountDetail, Integer> apiId;
	public static volatile SingularAttribute<VwAccountDetail, String> apiTypeDescription;
	public static volatile SingularAttribute<VwAccountDetail, Integer> apiTypeId;
	public static volatile SingularAttribute<VwAccountDetail, String> apiTypeName;
	public static volatile SingularAttribute<VwAccountDetail, String> apiTypeUrl;
	public static volatile SingularAttribute<VwAccountDetail, String> bucket;
	public static volatile SingularAttribute<VwAccountDetail, String> httpAddressIp;
	public static volatile SingularAttribute<VwAccountDetail, String> httpAddressPort;
	public static volatile SingularAttribute<VwAccountDetail, String> httpsAddressIp;
	public static volatile SingularAttribute<VwAccountDetail, String> httpsAddressPort;
	public static volatile SingularAttribute<VwAccountDetail, Date> lastValidationDate;
	public static volatile SingularAttribute<VwAccountDetail, String> name;
	public static volatile SingularAttribute<VwAccountDetail, String> secretKey;
	public static volatile SingularAttribute<VwAccountDetail, String> subtenant;
	public static volatile SingularAttribute<VwAccountDetail, String> url;
	public static volatile SingularAttribute<VwAccountDetail, Boolean> validated;
}
