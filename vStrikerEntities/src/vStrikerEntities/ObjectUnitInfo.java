package vStrikerEntities;

public class ObjectUnitInfo {

	    private String name;
	    private long Id;
	    

	    @Override
	    public String toString() {
	        return name;
	    }

	    public ObjectUnitInfo(String name, long  id) {
	        this.name = name;
	        this.Id = id;

	    }

	    public String getName() {
	        return name;
	    }

	    public long geID() {
	        return Id;
	    }
}
