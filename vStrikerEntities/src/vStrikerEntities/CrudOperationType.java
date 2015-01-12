package vStrikerEntities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


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

	//bi-directional one-to-one association to ApiType
	@OneToOne(mappedBy="crudOperationType")
	private ApiType apiType;

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

	public ApiType getApiType() {
		return this.apiType;
	}

	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}

}