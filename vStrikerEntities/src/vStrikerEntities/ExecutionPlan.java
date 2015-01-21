package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the EXECUTION_PLAN database table.
 * 
 */
@Entity
@Table(name="EXECUTION_PLAN", schema="VSTRIKERDB")
@NamedQuery(name="ExecutionPlan.findAll", query="SELECT e FROM ExecutionPlan e")
public class ExecutionPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EXECUTION_PLAN_ID")
	private long executionPlanId;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;

	//bi-directional many-to-one association to ConfigurationTemplate
	@ManyToOne
	@JoinColumn(name="CONFIGURATION_TEMPLATE_ID")
	private ConfigurationTemplate configurationTemplate;

	//bi-directional many-to-one association to TestConfiguration
	@ManyToOne
	@JoinColumn(name="TESTCONFIGURATION_ID")
	private TestConfiguration testConfiguration;

	//bi-directional many-to-one association to ExecutionReport
	@OneToMany(mappedBy="executionPlan")
	private List<ExecutionReport> executionReports;

	public ExecutionPlan() {
	}

	public long getExecutionPlanId() {
		return this.executionPlanId;
	}

	public void setExecutionPlanId(long executionPlanId) {
		this.executionPlanId = executionPlanId;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ConfigurationTemplate getConfigurationTemplate() {
		return this.configurationTemplate;
	}

	public void setConfigurationTemplate(ConfigurationTemplate configurationTemplate) {
		this.configurationTemplate = configurationTemplate;
	}

	public TestConfiguration getTestConfiguration() {
		return this.testConfiguration;
	}

	public void setTestConfiguration(TestConfiguration testConfiguration) {
		this.testConfiguration = testConfiguration;
	}

	public List<ExecutionReport> getExecutionReports() {
		return this.executionReports;
	}

	public void setExecutionReports(List<ExecutionReport> executionReports) {
		this.executionReports = executionReports;
	}

	public ExecutionReport addExecutionReport(ExecutionReport executionReport) {
		getExecutionReports().add(executionReport);
		executionReport.setExecutionPlan(this);

		return executionReport;
	}

	public ExecutionReport removeExecutionReport(ExecutionReport executionReport) {
		getExecutionReports().remove(executionReport);
		executionReport.setExecutionPlan(null);

		return executionReport;
	}

}