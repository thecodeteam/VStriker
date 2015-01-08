package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EXECUTION_REPORT_DATA database table.
 * 
 */
@Entity
@Table(name="VSTRIKERDB.EXECUTION_REPORT_DATA")
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

	@Column(name="EXECUTION_REPORT_ID")
	private int executionReportId;

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

	public int getExecutionReportId() {
		return this.executionReportId;
	}

	public void setExecutionReportId(int executionReportId) {
		this.executionReportId = executionReportId;
	}

}