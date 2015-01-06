package vStrikerEntities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the API database table.
 * 
 */
@Entity
@Table(name="API")
@NamedQuery(name="Api.findAll", query="SELECT a FROM Api a")
public class Api implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="API_ID")
	private int apiId;

	@Column(name="ACCOUNT_ID")
	private int accountId;

	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	private String bucket;

	@Column(name="HTTP_ADDRESS_IP")
	private String httpAddressIp;

	@Column(name="HTTP_ADDRESS_PORT")
	private String httpAddressPort;

	@Column(name="HTTPS_ADDRESS_IP")
	private String httpsAddressIp;

	@Column(name="HTTPS_ADDRESS_PORT")
	private String httpsAddressPort;

	@Column(name="SECRET_KEY")
	private String secretKey;

	private String subtenant;

	private String url;

	public Api() {
	}

	public int getApiId() {
		return this.apiId;
	}

	public void setApiId(int apiId) {
		this.apiId = apiId;
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getApiTypeId() {
		return this.apiTypeId;
	}

	public void setApiTypeId(int apiTypeId) {
		this.apiTypeId = apiTypeId;
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

}