package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CRUD_OPERATION_TYPE database table.
 * 
 */
@Entity
@Table(name="CRUD_OPERATION_TYPE", schema="VSTRIKERDB")
@NamedQuery(name="CrudOperationType.findAll", query="SELECT c FROM CrudOperationType c")
public class CrudOperationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CRUD_OPERATION_TYPE_ID")
	private long crudOperationTypeId;

	@Column(name="\"CRUD OPERATION VALUE\"")
	private String crud_operation_value;

	@Column(name="CRUD_OPERATION_NAME")
	private String crudOperationName;

	//bi-directional many-to-one association to TestPlanOperation
	@OneToMany(mappedBy="crudOperationType")
	private List<TestPlanOperation> testPlanOperations;

	public CrudOperationType() {
	}

	public long getCrudOperationTypeId() {
		return this.crudOperationTypeId;
	}

	public void setCrudOperationTypeId(long crudOperationTypeId) {
		this.crudOperationTypeId = crudOperationTypeId;
	}

	public String getCrud_operation_value() {
		return this.crud_operation_value;
	}

	public void setCrud_operation_value(String crud_operation_value) {
		this.crud_operation_value = crud_operation_value;
	}

	public String getCrudOperationName() {
		return this.crudOperationName;
	}

	public void setCrudOperationName(String crudOperationName) {
		this.crudOperationName = crudOperationName;
	}

	public List<TestPlanOperation> getTestPlanOperations() {
		return this.testPlanOperations;
	}

	public void setTestPlanOperations(List<TestPlanOperation> testPlanOperations) {
		this.testPlanOperations = testPlanOperations;
	}

	public TestPlanOperation addTestPlanOperation(TestPlanOperation testPlanOperation) {
		getTestPlanOperations().add(testPlanOperation);
		testPlanOperation.setCrudOperationType(this);

		return testPlanOperation;
	}

	public TestPlanOperation removeTestPlanOperation(TestPlanOperation testPlanOperation) {
		getTestPlanOperations().remove(testPlanOperation);
		testPlanOperation.setCrudOperationType(null);

		return testPlanOperation;
	}

}