package vStrikerEntities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-06T08:44:12.187-0800")
@StaticMetamodel(CrudOperationType.class)
public class CrudOperationType_ {
	public static volatile SingularAttribute<CrudOperationType, Long> crudOperationTypeId;
	public static volatile SingularAttribute<CrudOperationType, String> crud_operation_value;
	public static volatile SingularAttribute<CrudOperationType, String> crudOperationName;
	public static volatile SingularAttribute<CrudOperationType, ApiType> apiType;
}
