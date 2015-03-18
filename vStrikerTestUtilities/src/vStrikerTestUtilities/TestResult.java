package vStrikerTestUtilities;

 
public class TestResult {
 
    private long _createtime;
    private long _readtime;
    private long _updatetime;
    private long _deletetime;
    private long _totaltime;
    private long _max;
    private long _min;
    public TestResult() {
        this._createtime = 0;
        this._readtime = 0;
        this._updatetime = 0;
        this._deletetime = 0;
        this._totaltime = 0;
        this._max=0;
        this._min=0;
    }

    public void setCreateTime(long x){this._createtime=x;}
    public void setReadTime(long x){this._readtime=x;}
    public void setUpdateTime(long x){this._updatetime=x;}
    public void setDeleteTime(long x){this._deletetime=x;}
    public void setTotalTime(long x){this._totaltime=x;}
    public void setMin(long x){this._min=x;}
    public void setMax(long x){this._max=x;}

    public long getCreateTime(){return this._createtime;}
    public long getReadTime(){return this._readtime;}
    public long getUpdateTime(){return this._updatetime;}
    public long getDeleteTime(){return this._deletetime;}
    public long getTotalTime(){return this._deletetime;}
    public long getMin(){return this._min;}
    public long getMax(){return this._max;}

}