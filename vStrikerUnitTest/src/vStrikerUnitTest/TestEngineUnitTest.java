package vStrikerUnitTest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import vStrikerTestUtilities.*;

import org.junit.Test;

public class TestEngineUnitTest {

	@Test
	public  void test() throws Exception {

		TestFileGeneration();
		
	}
	
	public void TestFileGeneration()
	{
		String testConfName="testme";
		int objSize=1024*1024;
		int numfiles=4;
		List<String> list;
		try {
			list = Utilites.generateTestFileList(testConfName, objSize, numfiles);
			for(String s :list)
				System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//return list;

	}
}
