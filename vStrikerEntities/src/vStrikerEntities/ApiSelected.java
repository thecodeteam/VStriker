package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the API_SELECTED database table.
 * 
 */
@Entity
@Table(name="API_SELECTED", schema="VSTRIKERDB")
@NamedQuery(name="ApiSelected.findAll", query="SELECT a FROM ApiSelected a")
public class ApiSelected implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="API_SELECTED_ID")
	private long apiSelectedId;

	//bi-directional many-to-one association to ApiType
	@ManyToOne
	@JoinColumn(name="API_TYPE_ID")
	private ApiType apiType;

	//bi-directional many-to-one association to ConfigurationTemplate
	@ManyToOne
	@JoinColumn(name="CONFIGURATION_TEMPLATE_ID")
	private ConfigurationTemplate configurationTemplate;

	//bi-directional many-to-one association to TestConfiguration
	@ManyToOne
	@JoinColumn(name="TESTCONFIGURATION_ID")
	private TestConfiguration testConfiguration;

	public ApiSelected() {
	}

	public long getApiSelectedId() {
		return this.apiSelectedId;
	}

	public void setApiSelectedId(long apiSelectedId) {
		this.apiSelectedId = apiSelectedId;
	}

	public ApiType getApiType() {
		return this.apiType;
	}

	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}

	public ConfigurationTemplate getConfigurationTemplate() {
		return this.configurationTemplate;
	}

	public void setConfigurationTemplate(ConfigurationTemplate configurationTemplate) {
		this.configurationTemplate = configurationTemplate;
	}

	public TestConfiguration getTestConfiguration() {
		return this.testConfiguration;
	}

	public void setTestConfiguration(TestConfiguration testConfiguration) {
		this.testConfiguration = testConfiguration;
	}

}