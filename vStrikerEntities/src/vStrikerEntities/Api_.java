package vStrikerEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-30T13:37:45.076-0800")
@StaticMetamodel(Api.class)
public class Api_ {
	public static volatile SingularAttribute<Api, Integer> apiId;
	public static volatile SingularAttribute<Api, Integer> accountId;
	public static volatile SingularAttribute<Api, Integer> apiTypeId;
	public static volatile SingularAttribute<Api, String> bucket;
	public static volatile SingularAttribute<Api, String> httpAddressIp;
	public static volatile SingularAttribute<Api, String> httpAddressPort;
	public static volatile SingularAttribute<Api, String> httpsAddressIp;
	public static volatile SingularAttribute<Api, String> httpsAddressPort;
	public static volatile SingularAttribute<Api, String> secretKey;
	public static volatile SingularAttribute<Api, String> subtenant;
	public static volatile SingularAttribute<Api, String> url;
}
