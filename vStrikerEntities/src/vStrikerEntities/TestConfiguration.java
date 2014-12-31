package vStrikerEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TEST_CONFIGURATION database table.
 * 
 */
@Entity
@Table(name="VSTRIKERDB.TEST_CONFIGURATION")
@NamedQuery(name="TestConfiguration.findAll", query="SELECT t FROM TestConfiguration t")
public class TestConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TESTCONFIGURATION_ID")
	private long testconfigurationId;

	@Column(name="API_SELCTED_ID")
	private int apiSelctedId;

	@Column(name="CREATE_OPERATION")
	private boolean createOperation;

	@Column(name="CREATE_PERCENT")
	private int createPercent;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DELETE_OPERATION")
	private boolean deleteOperation;

	@Column(name="DELETE_PERCENT")
	private int deletePercent;

	@Column(name="ENABLE_RAND_SIZE_OBJECT")
	private boolean enableRandSizeObject;

	@Column(name="LARGE_SIZE_PERCENT")
	private int largeSizePercent;

	@Column(name="MAX_RAN_DSIZE_OBJECT")
	private int maxRanDsizeObject;

	@Column(name="MEDIUM_SIZE_PERCENT")
	private int mediumSizePercent;

	@Column(name="MIN_RAND_SIZE_OBJECT")
	private int minRandSizeObject;

	@Column(name="NUMBER_OF_OPERATIONS")
	private int numberOfOperations;

	@Column(name="NUMBER_OF_RETRY")
	private int numberOfRetry;

	@Column(name="NUMBER_OF_THREADS")
	private int numberOfThreads;

	@Column(name="OBJECT_SIZE")
	private int objectSize;

	@Column(name="OBJECT_SIZE_REPORT_UNIT_ID")
	private int objectSizeReportUnitId;

	@Column(name="READ_OPERATION")
	private boolean readOperation;

	@Column(name="READ_PERCENT")
	private int readPercent;

	@Column(name="SMALL_SIZE_PERCENT")
	private int smallSizePercent;

	@Column(name="TEST_CONFIG_DESCRIPTION")
	private String testConfigDescription;

	@Column(name="TEST_CONFIG_NAME")
	private String testConfigName;

	@Column(name="UPDATE_OPERATION")
	private boolean updateOperation;

	@Column(name="UPDATE_PERCENT")
	private int updatePercent;

	public TestConfiguration() {
	}

	public long getTestconfigurationId() {
		return this.testconfigurationId;
	}

	public void setTestconfigurationId(long testconfigurationId) {
		this.testconfigurationId = testconfigurationId;
	}

	public int getApiSelctedId() {
		return this.apiSelctedId;
	}

	public void setApiSelctedId(int apiSelctedId) {
		this.apiSelctedId = apiSelctedId;
	}

	public boolean getCreateOperation() {
		return this.createOperation;
	}

	public void setCreateOperation(boolean createOperation) {
		this.createOperation = createOperation;
	}

	public int getCreatePercent() {
		return this.createPercent;
	}

	public void setCreatePercent(int createPercent) {
		this.createPercent = createPercent;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean getDeleteOperation() {
		return this.deleteOperation;
	}

	public void setDeleteOperation(boolean deleteOperation) {
		this.deleteOperation = deleteOperation;
	}

	public int getDeletePercent() {
		return this.deletePercent;
	}

	public void setDeletePercent(int deletePercent) {
		this.deletePercent = deletePercent;
	}

	public boolean getEnableRandSizeObject() {
		return this.enableRandSizeObject;
	}

	public void setEnableRandSizeObject(boolean enableRandSizeObject) {
		this.enableRandSizeObject = enableRandSizeObject;
	}

	public int getLargeSizePercent() {
		return this.largeSizePercent;
	}

	public void setLargeSizePercent(int largeSizePercent) {
		this.largeSizePercent = largeSizePercent;
	}

	public int getMaxRanDsizeObject() {
		return this.maxRanDsizeObject;
	}

	public void setMaxRanDsizeObject(int maxRanDsizeObject) {
		this.maxRanDsizeObject = maxRanDsizeObject;
	}

	public int getMediumSizePercent() {
		return this.mediumSizePercent;
	}

	public void setMediumSizePercent(int mediumSizePercent) {
		this.mediumSizePercent = mediumSizePercent;
	}

	public int getMinRandSizeObject() {
		return this.minRandSizeObject;
	}

	public void setMinRandSizeObject(int minRandSizeObject) {
		this.minRandSizeObject = minRandSizeObject;
	}

	public int getNumberOfOperations() {
		return this.numberOfOperations;
	}

	public void setNumberOfOperations(int numberOfOperations) {
		this.numberOfOperations = numberOfOperations;
	}

	public int getNumberOfRetry() {
		return this.numberOfRetry;
	}

	public void setNumberOfRetry(int numberOfRetry) {
		this.numberOfRetry = numberOfRetry;
	}

	public int getNumberOfThreads() {
		return this.numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	public int getObjectSize() {
		return this.objectSize;
	}

	public void setObjectSize(int objectSize) {
		this.objectSize = objectSize;
	}

	public int getObjectSizeReportUnitId() {
		return this.objectSizeReportUnitId;
	}

	public void setObjectSizeReportUnitId(int objectSizeReportUnitId) {
		this.objectSizeReportUnitId = objectSizeReportUnitId;
	}

	public boolean getReadOperation() {
		return this.readOperation;
	}

	public void setReadOperation(boolean readOperation) {
		this.readOperation = readOperation;
	}

	public int getReadPercent() {
		return this.readPercent;
	}

	public void setReadPercent(int readPercent) {
		this.readPercent = readPercent;
	}

	public int getSmallSizePercent() {
		return this.smallSizePercent;
	}

	public void setSmallSizePercent(int smallSizePercent) {
		this.smallSizePercent = smallSizePercent;
	}

	public String getTestConfigDescription() {
		return this.testConfigDescription;
	}

	public void setTestConfigDescription(String testConfigDescription) {
		this.testConfigDescription = testConfigDescription;
	}

	public String getTestConfigName() {
		return this.testConfigName;
	}

	public void setTestConfigName(String testConfigName) {
		this.testConfigName = testConfigName;
	}

	public boolean getUpdateOperation() {
		return this.updateOperation;
	}

	public void setUpdateOperation(boolean updateOperation) {
		this.updateOperation = updateOperation;
	}

	public int getUpdatePercent() {
		return this.updatePercent;
	}

	public void setUpdatePercent(int updatePercent) {
		this.updatePercent = updatePercent;
	}

}