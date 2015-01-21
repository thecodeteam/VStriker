package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EXECUTION_REPORT_DATA database table.
 * 
 */
@Entity
@Table(name="EXECUTION_REPORT_DATA", schema="VSTRIKERDB")
@NamedQuery(name="ExecutionReportData.findAll", query="SELECT e FROM ExecutionReportData e")
public class ExecutionReportData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EXECUTION_REPORT_DATA_ID")
	private long executionReportDataId;

	@Column(name="DATA_KEY")
	private String dataKey;

	@Column(name="DATA_VALUE")
	private String dataValue;

	//bi-directional many-to-one association to ExecutionReport
	@ManyToOne
	@JoinColumn(name="EXECUTION_REPORT_ID")
	private ExecutionReport executionReport;

	public ExecutionReportData() {
	}

	public long getExecutionReportDataId() {
		return this.executionReportDataId;
	}

	public void setExecutionReportDataId(long executionReportDataId) {
		this.executionReportDataId = executionReportDataId;
	}

	public String getDataKey() {
		return this.dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public ExecutionReport getExecutionReport() {
		return this.executionReport;
	}

	public void setExecutionReport(ExecutionReport executionReport) {
		this.executionReport = executionReport;
	}

}