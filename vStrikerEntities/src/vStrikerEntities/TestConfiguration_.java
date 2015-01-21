package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-14T17:45:11.234-0800")
@StaticMetamodel(TestConfiguration.class)
public class TestConfiguration_ {
	public static volatile SingularAttribute<TestConfiguration, Long> testconfigurationId;
	public static volatile SingularAttribute<TestConfiguration, Integer> apiSelctedId;
	public static volatile SingularAttribute<TestConfiguration, Boolean> createOperation;
	public static volatile SingularAttribute<TestConfiguration, Integer> createPercent;
	public static volatile SingularAttribute<TestConfiguration, Date> createdDate;
	public static volatile SingularAttribute<TestConfiguration, Boolean> deleteOperation;
	public static volatile SingularAttribute<TestConfiguration, Integer> deletePercent;
	public static volatile SingularAttribute<TestConfiguration, Boolean> enableRandSizeObject;
	public static volatile SingularAttribute<TestConfiguration, Integer> largeSizePercent;
	public static volatile SingularAttribute<TestConfiguration, Integer> maxRanDsizeObject;
	public static volatile SingularAttribute<TestConfiguration, Integer> mediumSizePercent;
	public static volatile SingularAttribute<TestConfiguration, Integer> minRandSizeObject;
	public static volatile SingularAttribute<TestConfiguration, Integer> numberOfOperations;
	public static volatile SingularAttribute<TestConfiguration, Integer> numberOfRetry;
	public static volatile SingularAttribute<TestConfiguration, Integer> numberOfThreads;
	public static volatile SingularAttribute<TestConfiguration, Integer> objectSize;
	public static volatile SingularAttribute<TestConfiguration, Integer> objectSizeReportUnitId;
	public static volatile SingularAttribute<TestConfiguration, Boolean> readOperation;
	public static volatile SingularAttribute<TestConfiguration, Integer> readPercent;
	public static volatile SingularAttribute<TestConfiguration, Integer> smallSizePercent;
	public static volatile SingularAttribute<TestConfiguration, String> testConfigDescription;
	public static volatile SingularAttribute<TestConfiguration, String> testConfigName;
	public static volatile SingularAttribute<TestConfiguration, Boolean> updateOperation;
	public static volatile SingularAttribute<TestConfiguration, Integer> updatePercent;
}
