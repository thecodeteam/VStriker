package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CONFIGURATION_TEMPLATE database table.
 * 
 */
@Entity
@Table(name="CONFIGURATION_TEMPLATE", schema="VSTRIKERDB")
@NamedQuery(name="ConfigurationTemplate.findAll", query="SELECT c FROM ConfigurationTemplate c")
public class ConfigurationTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONFIGURATION_TEMPLATE_ID")
	private long configurationTemplateId;

	@Column(name="CONF_TEMP_CREATE_OPERATION")
	private boolean confTempCreateOperation;

	@Column(name="CONF_TEMP_CREATE_PERCENT")
	private int confTempCreatePercent;

	@Temporal(TemporalType.DATE)
	@Column(name="CONF_TEMP_CREATED_DATE")
	private Date confTempCreatedDate;

	@Column(name="CONF_TEMP_DELETE_OPERATION")
	private boolean confTempDeleteOperation;

	@Column(name="CONF_TEMP_DELETE_PERCENT")
	private int confTempDeletePercent;

	@Column(name="CONF_TEMP_DESCRIPTION")
	private String confTempDescription;

	@Column(name="CONF_TEMP_ENABLE_RAND_SIZE_OBJECT")
	private boolean confTempEnableRandSizeObject;

	@Column(name="CONF_TEMP_LARGE_SIZE_PERCENT")
	private int confTempLargeSizePercent;

	@Column(name="CONF_TEMP_MAX_RAND_SIZE_OBJECT")
	private int confTempMaxRandSizeObject;

	@Column(name="CONF_TEMP_MEDIUM_SIZE_PERCENT")
	private int confTempMediumSizePercent;

	@Column(name="CONF_TEMP_MIN_RAND_SIZE_OBJECT")
	private int confTempMinRandSizeObject;

	@Column(name="CONF_TEMP_NAME")
	private String confTempName;

	@Column(name="CONF_TEMP_NUMBER_OF_OPERATIONS")
	private int confTempNumberOfOperations;

	@Column(name="CONF_TEMP_NUMBER_OF_RETRY")
	private int confTempNumberOfRetry;

	@Column(name="CONF_TEMP_NUMBER_OF_THREADS")
	private int confTempNumberOfThreads;

	@Column(name="CONF_TEMP_OBJECT_SIZE")
	private int confTempObjectSize;

	@Column(name="CONF_TEMP_READ_OPERATION")
	private boolean confTempReadOperation;

	@Column(name="CONF_TEMP_READ_PERCENT")
	private int confTempReadPercent;

	@Column(name="CONF_TEMP_SMALL_SIZE_PERCENT")
	private int confTempSmallSizePercent;

	@Column(name="CONF_TEMP_UPDATE_OPERATION")
	private boolean confTempUpdateOperation;

	@Column(name="CONF_TEMP_UPDATE_PERCENT")
	private int confTempUpdatePercent;

	//bi-directional many-to-one association to ApiSelected
	@OneToMany(mappedBy="configurationTemplate")
	private List<ApiSelected> apiSelecteds;

	//uni-directional one-to-one association to ObjectSizeReportUnit
	@OneToOne
	@JoinColumn(name="CONF_TEMP_OBJECT_SIZE_REPORT_UNIT_ID")
	private ObjectSizeReportUnit objectSizeReportUnit1;

	//bi-directional many-to-one association to ExecutionPlan
	@OneToMany(mappedBy="configurationTemplate")
	private List<ExecutionPlan> executionPlans;

	public ConfigurationTemplate() {
	}

	public long getConfigurationTemplateId() {
		return this.configurationTemplateId;
	}

	public void setConfigurationTemplateId(long configurationTemplateId) {
		this.configurationTemplateId = configurationTemplateId;
	}

	public boolean getConfTempCreateOperation() {
		return this.confTempCreateOperation;
	}

	public void setConfTempCreateOperation(boolean confTempCreateOperation) {
		this.confTempCreateOperation = confTempCreateOperation;
	}

	public int getConfTempCreatePercent() {
		return this.confTempCreatePercent;
	}

	public void setConfTempCreatePercent(int confTempCreatePercent) {
		this.confTempCreatePercent = confTempCreatePercent;
	}

	public Date getConfTempCreatedDate() {
		return this.confTempCreatedDate;
	}

	public void setConfTempCreatedDate(Date confTempCreatedDate) {
		this.confTempCreatedDate = confTempCreatedDate;
	}

	public boolean getConfTempDeleteOperation() {
		return this.confTempDeleteOperation;
	}

	public void setConfTempDeleteOperation(boolean confTempDeleteOperation) {
		this.confTempDeleteOperation = confTempDeleteOperation;
	}

	public int getConfTempDeletePercent() {
		return this.confTempDeletePercent;
	}

	public void setConfTempDeletePercent(int confTempDeletePercent) {
		this.confTempDeletePercent = confTempDeletePercent;
	}

	public String getConfTempDescription() {
		return this.confTempDescription;
	}

	public void setConfTempDescription(String confTempDescription) {
		this.confTempDescription = confTempDescription;
	}

	public boolean getConfTempEnableRandSizeObject() {
		return this.confTempEnableRandSizeObject;
	}

	public void setConfTempEnableRandSizeObject(boolean confTempEnableRandSizeObject) {
		this.confTempEnableRandSizeObject = confTempEnableRandSizeObject;
	}

	public int getConfTempLargeSizePercent() {
		return this.confTempLargeSizePercent;
	}

	public void setConfTempLargeSizePercent(int confTempLargeSizePercent) {
		this.confTempLargeSizePercent = confTempLargeSizePercent;
	}

	public int getConfTempMaxRandSizeObject() {
		return this.confTempMaxRandSizeObject;
	}

	public void setConfTempMaxRandSizeObject(int confTempMaxRandSizeObject) {
		this.confTempMaxRandSizeObject = confTempMaxRandSizeObject;
	}

	public int getConfTempMediumSizePercent() {
		return this.confTempMediumSizePercent;
	}

	public void setConfTempMediumSizePercent(int confTempMediumSizePercent) {
		this.confTempMediumSizePercent = confTempMediumSizePercent;
	}

	public int getConfTempMinRandSizeObject() {
		return this.confTempMinRandSizeObject;
	}

	public void setConfTempMinRandSizeObject(int confTempMinRandSizeObject) {
		this.confTempMinRandSizeObject = confTempMinRandSizeObject;
	}

	public String getConfTempName() {
		return this.confTempName;
	}

	public void setConfTempName(String confTempName) {
		this.confTempName = confTempName;
	}

	public int getConfTempNumberOfOperations() {
		return this.confTempNumberOfOperations;
	}

	public void setConfTempNumberOfOperations(int confTempNumberOfOperations) {
		this.confTempNumberOfOperations = confTempNumberOfOperations;
	}

	public int getConfTempNumberOfRetry() {
		return this.confTempNumberOfRetry;
	}

	public void setConfTempNumberOfRetry(int confTempNumberOfRetry) {
		this.confTempNumberOfRetry = confTempNumberOfRetry;
	}

	public int getConfTempNumberOfThreads() {
		return this.confTempNumberOfThreads;
	}

	public void setConfTempNumberOfThreads(int confTempNumberOfThreads) {
		this.confTempNumberOfThreads = confTempNumberOfThreads;
	}

	public int getConfTempObjectSize() {
		return this.confTempObjectSize;
	}

	public void setConfTempObjectSize(int confTempObjectSize) {
		this.confTempObjectSize = confTempObjectSize;
	}

	public boolean getConfTempReadOperation() {
		return this.confTempReadOperation;
	}

	public void setConfTempReadOperation(boolean confTempReadOperation) {
		this.confTempReadOperation = confTempReadOperation;
	}

	public int getConfTempReadPercent() {
		return this.confTempReadPercent;
	}

	public void setConfTempReadPercent(int confTempReadPercent) {
		this.confTempReadPercent = confTempReadPercent;
	}

	public int getConfTempSmallSizePercent() {
		return this.confTempSmallSizePercent;
	}

	public void setConfTempSmallSizePercent(int confTempSmallSizePercent) {
		this.confTempSmallSizePercent = confTempSmallSizePercent;
	}

	public boolean getConfTempUpdateOperation() {
		return this.confTempUpdateOperation;
	}

	public void setConfTempUpdateOperation(boolean confTempUpdateOperation) {
		this.confTempUpdateOperation = confTempUpdateOperation;
	}

	public int getConfTempUpdatePercent() {
		return this.confTempUpdatePercent;
	}

	public void setConfTempUpdatePercent(int confTempUpdatePercent) {
		this.confTempUpdatePercent = confTempUpdatePercent;
	}

	public List<ApiSelected> getApiSelecteds() {
		return this.apiSelecteds;
	}

	public void setApiSelecteds(List<ApiSelected> apiSelecteds) {
		this.apiSelecteds = apiSelecteds;
	}

	public ApiSelected addApiSelected(ApiSelected apiSelected) {
		getApiSelecteds().add(apiSelected);
		apiSelected.setConfigurationTemplate(this);

		return apiSelected;
	}

	public ApiSelected removeApiSelected(ApiSelected apiSelected) {
		getApiSelecteds().remove(apiSelected);
		apiSelected.setConfigurationTemplate(null);

		return apiSelected;
	}

	public ObjectSizeReportUnit getObjectSizeReportUnit1() {
		return this.objectSizeReportUnit1;
	}

	public void setObjectSizeReportUnit1(ObjectSizeReportUnit objectSizeReportUnit1) {
		this.objectSizeReportUnit1 = objectSizeReportUnit1;
	}


	public List<ExecutionPlan> getExecutionPlans() {
		return this.executionPlans;
	}

	public void setExecutionPlans(List<ExecutionPlan> executionPlans) {
		this.executionPlans = executionPlans;
	}

	public ExecutionPlan addExecutionPlan(ExecutionPlan executionPlan) {
		getExecutionPlans().add(executionPlan);
		executionPlan.setConfigurationTemplate(this);

		return executionPlan;
	}

	public ExecutionPlan removeExecutionPlan(ExecutionPlan executionPlan) {
		getExecutionPlans().remove(executionPlan);
		executionPlan.setConfigurationTemplate(null);

		return executionPlan;
	}

}