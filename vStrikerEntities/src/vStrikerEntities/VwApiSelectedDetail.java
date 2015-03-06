package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VW_API_SELECTED_DETAIL database table.
 * 
 */
@Entity
@Table(name="VW_API_SELECTED_DETAIL", schema="VSTRIKERDB")
@NamedQuery(name="VwApiSelectedDetail.findAll", query="SELECT v FROM VwApiSelectedDetail v")
public class VwApiSelectedDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="API_SELECTED_ID")
	private long apiSelectedId;

	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	@Column(name="API_TYPE_NAME")
	private String apiTypeName;

	@Column(name="API_TYPE_URL")
	private String apiTypeUrl;

	@Column(name="CONFIGURATION_TEMPLATE_ID")
	private long configurationTemplateId;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="TESTCONFIGURATION_ID")
	private long testconfigurationId;

	public VwApiSelectedDetail() {
	}

	public long getApiSelectedId() {
		return this.apiSelectedId;
	}

	public void setApiSelectedId(long apiSelectedId) {
		this.apiSelectedId = apiSelectedId;
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

	public long getConfigurationTemplateId() {
		return this.configurationTemplateId;
	}

	public void setConfigurationTemplateId(long configurationTemplateId) {
		this.configurationTemplateId = configurationTemplateId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTestconfigurationId() {
		return this.testconfigurationId;
	}

	public void setTestconfigurationId(long testconfigurationId) {
		this.testconfigurationId = testconfigurationId;
	}

}