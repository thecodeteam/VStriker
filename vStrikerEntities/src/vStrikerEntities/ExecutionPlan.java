package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EXECUTION_PLAN database table.
 * 
 */
@Entity
@Table(name="VSTRIKERDB.EXECUTION_PLAN")
@NamedQuery(name="ExecutionPlan.findAll", query="SELECT e FROM ExecutionPlan e")
public class ExecutionPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EXECUTION_PLAN_ID")
	private long executionPlanId;

	@Column(name="ACCOUNT_ID")
	private int accountId;

	@Column(name="CONFIGURATION_TEMPLATE_ID")
	private int configurationTemplateId;

	@Column(name="EXECUTION_REPORT_ID")
	private int executionReportId;

	@Column(name="TESTCONFIGURATION_ID")
	private int testconfigurationId;

	public ExecutionPlan() {
	}

	public long getExecutionPlanId() {
		return this.executionPlanId;
	}

	public void setExecutionPlanId(long executionPlanId) {
		this.executionPlanId = executionPlanId;
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getConfigurationTemplateId() {
		return this.configurationTemplateId;
	}

	public void setConfigurationTemplateId(int configurationTemplateId) {
		this.configurationTemplateId = configurationTemplateId;
	}

	public int getExecutionReportId() {
		return this.executionReportId;
	}

	public void setExecutionReportId(int executionReportId) {
		this.executionReportId = executionReportId;
	}

	public int getTestconfigurationId() {
		return this.testconfigurationId;
	}

	public void setTestconfigurationId(int testconfigurationId) {
		this.testconfigurationId = testconfigurationId;
	}

}