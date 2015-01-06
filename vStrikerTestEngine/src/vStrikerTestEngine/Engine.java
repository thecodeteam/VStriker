package vStrikerTestEngine;
/*
 * @author Sanjeev Chauhan
 */
public interface Engine {
	boolean validateS3Connection(String user, String key, String url, String namespace);
	void validateSwiftConnnection(String user, String key, String url, String namespace);
	void validateAtmosConnnection(String user, String key, String url, String namespace);
}
