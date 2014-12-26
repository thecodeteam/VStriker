package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
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

}