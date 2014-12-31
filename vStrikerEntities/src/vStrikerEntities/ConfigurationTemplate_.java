package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-12-30T13:37:45.107-0800")
@StaticMetamodel(ConfigurationTemplate.class)
public class ConfigurationTemplate_ {
	public static volatile SingularAttribute<ConfigurationTemplate, Long> configurationTemplateId;
	public static volatile SingularAttribute<ConfigurationTemplate, Boolean> confTempCreateOperation;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempCreatePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, Date> confTempCreatedDate;
	public static volatile SingularAttribute<ConfigurationTemplate, Boolean> confTempDeleteOperation;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempDeletePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, String> confTempDescription;
	public static volatile SingularAttribute<ConfigurationTemplate, Boolean> confTempEnableRandSizeObject;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempLargeSizePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempMaxRandSizeObject;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempMediumSizePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempMinRandSizeObject;
	public static volatile SingularAttribute<ConfigurationTemplate, String> confTempName;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempNumberOfOperations;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempNumberOfRetry;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempNumberOfThreads;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempObjectSize;
	public static volatile SingularAttribute<ConfigurationTemplate, Boolean> confTempReadOperation;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempReadPercent;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempSmallSizePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, Boolean> confTempUpdateOperation;
	public static volatile SingularAttribute<ConfigurationTemplate, Integer> confTempUpdatePercent;
	public static volatile SingularAttribute<ConfigurationTemplate, ObjectSizeReportUnit> objectSizeReportUnit;
}
