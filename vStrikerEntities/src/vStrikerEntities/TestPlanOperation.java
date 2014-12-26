package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TEST_PLAN_OPERATION database table.
 * 
 */
@Entity
@Table(name="VSTRIKERDB.TEST_PLAN_OPERATION")
@NamedQuery(name="TestPlanOperation.findAll", query="SELECT t FROM TestPlanOperation t")
public class TestPlanOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TEST_PLAN_OPERATION_ID")
	private long testPlanOperationId;

	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	@Column(name="CRUD_OPERATION")
	private int crudOperation;

	@Column(name="CRUD_OPERATION_TYPE_ID")
	private int crudOperationTypeId;

	@Column(name="FILE_URL")
	private String fileUrl;

	public TestPlanOperation() {
	}

	public long getTestPlanOperationId() {
		return this.testPlanOperationId;
	}

	public void setTestPlanOperationId(long testPlanOperationId) {
		this.testPlanOperationId = testPlanOperationId;
	}

	public int getApiTypeId() {
		return this.apiTypeId;
	}

	public void setApiTypeId(int apiTypeId) {
		this.apiTypeId = apiTypeId;
	}

	public int getCrudOperation() {
		return this.crudOperation;
	}

	public void setCrudOperation(int crudOperation) {
		this.crudOperation = crudOperation;
	}

	public int getCrudOperationTypeId() {
		return this.crudOperationTypeId;
	}

	public void setCrudOperationTypeId(int crudOperationTypeId) {
		this.crudOperationTypeId = crudOperationTypeId;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}