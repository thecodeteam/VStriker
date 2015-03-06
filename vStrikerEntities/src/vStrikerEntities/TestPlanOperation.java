package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TEST_PLAN_OPERATION database table.
 * 
 */
@Entity
@Table(name="TEST_PLAN_OPERATION", schema="VSTRIKERDB")
@NamedQuery(name="TestPlanOperation.findAll", query="SELECT t FROM TestPlanOperation t")
public class TestPlanOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TEST_PLAN_OPERATION_ID")
	private long testPlanOperationId;

	@Column(name="CRUD_OPERATION")
	private int crudOperation;

	@Column(name="FILE_URL")
	private String fileUrl;

	//bi-directional many-to-one association to ApiType
	@ManyToOne
	@JoinColumn(name="API_TYPE_ID")
	private ApiType apiType;

	//bi-directional many-to-one association to CrudOperationType
	@ManyToOne
	@JoinColumn(name="CRUD_OPERATION_TYPE_ID")
	private CrudOperationType crudOperationType;

	public TestPlanOperation() {
	}

	public long getTestPlanOperationId() {
		return this.testPlanOperationId;
	}

	public void setTestPlanOperationId(long testPlanOperationId) {
		this.testPlanOperationId = testPlanOperationId;
	}

	public int getCrudOperation() {
		return this.crudOperation;
	}

	public void setCrudOperation(int crudOperation) {
		this.crudOperation = crudOperation;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public ApiType getApiType() {
		return this.apiType;
	}

	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}

	public CrudOperationType getCrudOperationType() {
		return this.crudOperationType;
	}

	public void setCrudOperationType(CrudOperationType crudOperationType) {
		this.crudOperationType = crudOperationType;
	}

}