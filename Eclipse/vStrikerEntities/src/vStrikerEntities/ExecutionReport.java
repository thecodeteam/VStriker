package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EXECUTION_REPORT database table.
 * 
 */
@Entity
@Table(name="EXECUTION_REPORT", schema="VSTRIKERDB")
@NamedQuery(name="ExecutionReport.findAll", query="SELECT e FROM ExecutionReport e")
public class ExecutionReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EXECUTION_REPORT_ID")
	private long executionReportId;

	@Column(name="AVG_LATENCY_PER_CRUD_OPERATION")
	private String avgLatencyPerCrudOperation;

	@Temporal(TemporalType.DATE)
	@Column(name="EXECUTION_DATE")
	private Date executionDate;

	@Column(name="EXECUTION_NAME")
	private String executionName;

	@Column(name="MAX_THROUGHPUT")
	private String maxThroughput;

	@Column(name="MIN_THROUGHPUT")
	private String minThroughput;

	@Column(name="NUMBER_REQUEST_SEC")
	private int numberRequestSec;

	@Column(name="PERECENT_FAILED_REQUEST")
	private int perecentFailedRequest;

	@Column(name="TOTAL_THROUGHPUT")
	private String totalThroughput;

	@Column(name="TOTAL_VOLUME_RECEIVED")
	private String totalVolumeReceived;

	@Column(name="TOTAL_VOLUME_SENT")
	private String totalVolumeSent;

	//bi-directional many-to-one association to ExecutionPlan
	@ManyToOne
	@JoinColumn(name="EXECUTION_PLAN_ID")
	private ExecutionPlan executionPlan;

	//bi-directional many-to-one association to ExecutionReportData
	@OneToMany(mappedBy="executionReport")
	private List<ExecutionReportData> executionReportData;

	public ExecutionReport() {
	}

	public long getExecutionReportId() {
		return this.executionReportId;
	}

	public void setExecutionReportId(long executionReportId) {
		this.executionReportId = executionReportId;
	}

	public String getAvgLatencyPerCrudOperation() {
		return this.avgLatencyPerCrudOperation;
	}

	public void setAvgLatencyPerCrudOperation(String avgLatencyPerCrudOperation) {
		this.avgLatencyPerCrudOperation = avgLatencyPerCrudOperation;
	}

	public Date getExecutionDate() {
		return this.executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public String getExecutionName() {
		return this.executionName;
	}

	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}

	public String getMaxThroughput() {
		return this.maxThroughput;
	}

	public void setMaxThroughput(String maxThroughput) {
		this.maxThroughput = maxThroughput;
	}

	public String getMinThroughput() {
		return this.minThroughput;
	}

	public void setMinThroughput(String minThroughput) {
		this.minThroughput = minThroughput;
	}

	public int getNumberRequestSec() {
		return this.numberRequestSec;
	}

	public void setNumberRequestSec(int numberRequestSec) {
		this.numberRequestSec = numberRequestSec;
	}

	public int getPerecentFailedRequest() {
		return this.perecentFailedRequest;
	}

	public void setPerecentFailedRequest(int perecentFailedRequest) {
		this.perecentFailedRequest = perecentFailedRequest;
	}

	public String getTotalThroughput() {
		return this.totalThroughput;
	}

	public void setTotalThroughput(String totalThroughput) {
		this.totalThroughput = totalThroughput;
	}

	public String getTotalVolumeReceived() {
		return this.totalVolumeReceived;
	}

	public void setTotalVolumeReceived(String totalVolumeReceived) {
		this.totalVolumeReceived = totalVolumeReceived;
	}

	public String getTotalVolumeSent() {
		return this.totalVolumeSent;
	}

	public void setTotalVolumeSent(String totalVolumeSent) {
		this.totalVolumeSent = totalVolumeSent;
	}

	public ExecutionPlan getExecutionPlan() {
		return this.executionPlan;
	}

	public void setExecutionPlan(ExecutionPlan executionPlan) {
		this.executionPlan = executionPlan;
	}

	public List<ExecutionReportData> getExecutionReportData() {
		return this.executionReportData;
	}

	public void setExecutionReportData(List<ExecutionReportData> executionReportData) {
		this.executionReportData = executionReportData;
	}

	public ExecutionReportData addExecutionReportData(ExecutionReportData executionReportData) {
		getExecutionReportData().add(executionReportData);
		executionReportData.setExecutionReport(this);

		return executionReportData;
	}

	public ExecutionReportData removeExecutionReportData(ExecutionReportData executionReportData) {
		getExecutionReportData().remove(executionReportData);
		executionReportData.setExecutionReport(null);

		return executionReportData;
	}

}