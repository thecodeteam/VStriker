package vStrikerEntities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the VW_ACCOUNT_DETAIL database table.
 * 
 */
@Entity
@Table(name="VW_ACCOUNT_DETAIL",schema="VSTRIKERDB")
@NamedQuery(name="VwAccountDetail.findAll", query="SELECT v FROM VwAccountDetail v")
public class VwAccountDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="ACCOUNT_ID")
	private int accountId;

	@Column(name="ACCOUNT_LOCATION")
	private String accountLocation;

	@Column(name="API_ID")
	private int apiId;

	@Column(name="API_TYPE_DESCRIPTION")
	private String apiTypeDescription;

	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	@Column(name="API_TYPE_NAME")
	private String apiTypeName;

	@Column(name="API_TYPE_URL")
	private String apiTypeUrl;

	private String bucket;

	@Column(name="HTTP_ADDRESS_IP")
	private String httpAddressIp;

	@Column(name="HTTP_ADDRESS_PORT")
	private String httpAddressPort;

	@Column(name="HTTPS_ADDRESS_IP")
	private String httpsAddressIp;

	@Column(name="HTTPS_ADDRESS_PORT")
	private String httpsAddressPort;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_VALIDATION_DATE")
	private Date lastValidationDate;

	private String name;

	@Column(name="SECRET_KEY")
	private String secretKey;

	private String subtenant;

	private String url;

	private boolean validated;

	public VwAccountDetail() {
	}

	public String getId() {
		return this.id;
	}
	public void setId(String Id) {
		this.id = Id;
	}
	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountLocation() {
		return this.accountLocation;
	}

	public void setAccountLocation(String accountLocation) {
		this.accountLocation = accountLocation;
	}

	public int getApiId() {
		return this.apiId;
	}

	public void setApiId(int apiId) {
		this.apiId = apiId;
	}

	public String getApiTypeDescription() {
		return this.apiTypeDescription;
	}

	public void setApiTypeDescription(String apiTypeDescription) {
		this.apiTypeDescription = apiTypeDescription;
	}

	public int getApiTypeId() {
		return this.apiTypeId;
	}

	public void setApiTypeId(int apiTypeId) {
		this.apiTypeId = apiTypeId;
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

	public String getBucket() {
		return this.bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getHttpAddressIp() {
		return this.httpAddressIp;
	}

	public void setHttpAddressIp(String httpAddressIp) {
		this.httpAddressIp = httpAddressIp;
	}

	public String getHttpAddressPort() {
		return this.httpAddressPort;
	}

	public void setHttpAddressPort(String httpAddressPort) {
		this.httpAddressPort = httpAddressPort;
	}

	public String getHttpsAddressIp() {
		return this.httpsAddressIp;
	}

	public void setHttpsAddressIp(String httpsAddressIp) {
		this.httpsAddressIp = httpsAddressIp;
	}

	public String getHttpsAddressPort() {
		return this.httpsAddressPort;
	}

	public void setHttpsAddressPort(String httpsAddressPort) {
		this.httpsAddressPort = httpsAddressPort;
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

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSubtenant() {
		return this.subtenant;
	}

	public void setSubtenant(String subtenant) {
		this.subtenant = subtenant;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getValidated() {
		return this.validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

}
