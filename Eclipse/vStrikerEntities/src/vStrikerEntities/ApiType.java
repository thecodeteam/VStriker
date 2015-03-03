package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the API_TYPE database table.
 * 
 */
@Entity
@Table(name="API_TYPE", schema="VSTRIKERDB")
@NamedQuery(name="ApiType.findAll", query="SELECT a FROM ApiType a")
public class ApiType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	@Column(name="API_TYPE_DESCRIPTION")
	private String apiTypeDescription;

	@Column(name="API_TYPE_NAME")
	private String apiTypeName;

	@Column(name="API_TYPE_URL")
	private String apiTypeUrl;

	//bi-directional many-to-one association to ApiSelected
	@OneToMany(mappedBy="apiType")
	private List<ApiSelected> apiSelecteds;

	//bi-directional many-to-one association to Api
	@OneToMany(mappedBy="apiType")
	private List<Api> apis;

	//bi-directional many-to-one association to TestPlanOperation
	@OneToMany(mappedBy="apiType")
	private List<TestPlanOperation> testPlanOperations;

	public ApiType() {
	}

	public int getApiTypeId() {
		return this.apiTypeId;
	}

	public void setApiTypeId(int apiTypeId) {
		this.apiTypeId = apiTypeId;
	}

	public String getApiTypeDescription() {
		return this.apiTypeDescription;
	}

	public void setApiTypeDescription(String apiTypeDescription) {
		this.apiTypeDescription = apiTypeDescription;
	}

	public String getApiTypeName() {
		return this.apiTypeName;
	}

	public void setApiTypeName(String apiTypeName) {
		this.apiTypeName = apiTypeName;
	}

	public String getApiTypeUrl() {
		return this.apiTypeUrl;
	}

	public void setApiTypeUrl(String apiTypeUrl) {
		this.apiTypeUrl = apiTypeUrl;
	}

	public List<ApiSelected> getApiSelecteds() {
		return this.apiSelecteds;
	}

	public void setApiSelecteds(List<ApiSelected> apiSelecteds) {
		this.apiSelecteds = apiSelecteds;
	}

	public ApiSelected addApiSelected(ApiSelected apiSelected) {
		getApiSelecteds().add(apiSelected);
		apiSelected.setApiType(this);

		return apiSelected;
	}

	public ApiSelected removeApiSelected(ApiSelected apiSelected) {
		getApiSelecteds().remove(apiSelected);
		apiSelected.setApiType(null);

		return apiSelected;
	}

	public List<Api> getApis() {
		return this.apis;
	}

	public void setApis(List<Api> apis) {
		this.apis = apis;
	}

	public Api addApi(Api api) {
		getApis().add(api);
		api.setApiType(this);

		return api;
	}

	public Api removeApi(Api api) {
		getApis().remove(api);
		api.setApiType(null);

		return api;
	}

	public List<TestPlanOperation> getTestPlanOperations() {
		return this.testPlanOperations;
	}

	public void setTestPlanOperations(List<TestPlanOperation> testPlanOperations) {
		this.testPlanOperations = testPlanOperations;
	}

	public TestPlanOperation addTestPlanOperation(TestPlanOperation testPlanOperation) {
		getTestPlanOperations().add(testPlanOperation);
		testPlanOperation.setApiType(this);

		return testPlanOperation;
	}

	public TestPlanOperation removeTestPlanOperation(TestPlanOperation testPlanOperation) {
		getTestPlanOperations().remove(testPlanOperation);
		testPlanOperation.setApiType(null);

		return testPlanOperation;
	}

}