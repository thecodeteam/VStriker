package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@Table(name="ACCOUNT", schema="VSTRIKERDB")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACCOUNT_ID")
	private long accountId;

	@Column(name="ACCOUNT_LOCATION")
	private String accountLocation;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_VALIDATION_DATE")
	private Date lastValidationDate;

	private String name;

	private boolean validated;

	//bi-directional many-to-one association to Api
	@OneToMany(mappedBy="account")
	private List<Api> apis;

	//bi-directional many-to-one association to ExecutionPlan
	@OneToMany(mappedBy="account")
	private List<ExecutionPlan> executionPlans;

	public Account() {
	}

	public long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountLocation() {
		return this.accountLocation;
	}

	public void setAccountLocation(String accountLocation) {
		this.accountLocation = accountLocation;
	}

	public Date getLastValidationDate() {
		return this.lastValidationDate;
	}

	public void setLastValidationDate(Date lastValidationDate) {
		this.lastValidationDate = lastValidationDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getValidated() {
		return this.validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public List<Api> getApis() {
		return this.apis;
	}

	public void setApis(List<Api> apis) {
		this.apis = apis;
	}

	public Api addApi(Api api) {
		getApis().add(api);
		api.setAccount(this);

		return api;
	}

	public Api removeApi(Api api) {
		getApis().remove(api);
		api.setAccount(null);

		return api;
	}

	public List<ExecutionPlan> getExecutionPlans() {
		return this.executionPlans;
	}

	public void setExecutionPlans(List<ExecutionPlan> executionPlans) {
		this.executionPlans = executionPlans;
	}

	public ExecutionPlan addExecutionPlan(ExecutionPlan executionPlan) {
		getExecutionPlans().add(executionPlan);
		executionPlan.setAccount(this);

		return executionPlan;
	}

	public ExecutionPlan removeExecutionPlan(ExecutionPlan executionPlan) {
		getExecutionPlans().remove(executionPlan);
		executionPlan.setAccount(null);

		return executionPlan;
	}

}