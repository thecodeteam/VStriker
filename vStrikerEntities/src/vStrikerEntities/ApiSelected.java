package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the API_SELECTED database table.
 * 
 */
@Entity
@Table(name="VSTRIKERDB.API_SELECTED")
@NamedQuery(name="ApiSelected.findAll", query="SELECT a FROM ApiSelected a")
public class ApiSelected implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="API_SELECTED_ID")
	private long apiSelectedId;

	@Column(name="API_SELECTED_HTTP")
	private boolean apiSelectedHttp;

	@Column(name="API_SELECTED_HTTPS")
	private boolean apiSelectedHttps;

	@Column(name="API_TYPE_ID")
	private int apiTypeId;

	@Column(name="TESTCONFIGURATION_ID")
	private int testconfigurationId;

	public ApiSelected() {
	}

	public long getApiSelectedId() {
		return this.apiSelectedId;
	}

	public void setApiSelectedId(long apiSelectedId) {
		this.apiSelectedId = apiSelectedId;
	}

	public boolean getApiSelectedHttp() {
		return this.apiSelectedHttp;
	}

	public void setApiSelectedHttp(boolean apiSelectedHttp) {
		this.apiSelectedHttp = apiSelectedHttp;
	}

	public boolean getApiSelectedHttps() {
		return this.apiSelectedHttps;
	}

	public void setApiSelectedHttps(boolean apiSelectedHttps) {
		this.apiSelectedHttps = apiSelectedHttps;
	}

	public int getApiTypeId() {
		return this.apiTypeId;
	}

	public void setApiTypeId(int apiTypeId) {
		this.apiTypeId = apiTypeId;
	}

	public int getTestconfigurationId() {
		return this.testconfigurationId;
	}

	public void setTestconfigurationId(int testconfigurationId) {
		this.testconfigurationId = testconfigurationId;
	}

}