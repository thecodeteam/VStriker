package vStrikerEntities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-01-06T08:44:12.211-0800")
@StaticMetamodel(ExecutionReport.class)
public class ExecutionReport_ {
	public static volatile SingularAttribute<ExecutionReport, Long> executionReportId;
	public static volatile SingularAttribute<ExecutionReport, String> avgLatencyPerCrudOperation;
	public static volatile SingularAttribute<ExecutionReport, Date> executionDate;
	public static volatile SingularAttribute<ExecutionReport, String> executionName;
	public static volatile SingularAttribute<ExecutionReport, String> maxThroughput;
	public static volatile SingularAttribute<ExecutionReport, String> minThroughput;
	public static volatile SingularAttribute<ExecutionReport, Integer> numberRequestSec;
	public static volatile SingularAttribute<ExecutionReport, Integer> perecentFailedRequest;
	public static volatile SingularAttribute<ExecutionReport, String> totalThroughput;
	public static volatile SingularAttribute<ExecutionReport, String> totalVolumeReceived;
	public static volatile SingularAttribute<ExecutionReport, String> totalVolumeSent;
}
