package vStrikerTestEngine;
/*
 * @author Sanjeev Chauhan
 */
public interface Engine {
	boolean validateS3Connection(String user, String key, String url, String namespace);
	boolean validateSwiftConnnection(String user, String key, String url, String namespace);
	boolean validateAtmosConnnection(String user, String key, String url, String namespace);
}
