package vStrikerTestUtilities;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirectoryManager;

import org.apache.commons.io.FileUtils;

import vStrikerBizModel.*;
public class Utilites {

	

	public static List<String> generateTestFileList(String testConfName,int objSize,int numfiles) throws IOException {
		List<String> list = new ArrayList<String>();
        
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		
		File dir = new File(s.substring(0,s.lastIndexOf("\\")+1)+"stagefile");  
		if (!dir.exists()) {dir.mkdir();}        
			    
		String fileName=dir+"\\"+testConfName+"_1.txt";
		String fname=dir+"\\"+testConfName+"_";
		int i=2;
		
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");  
		try  
		{  
		    raf.setLength(objSize);  
		}  
		finally  
		{  
		    raf.close();  
		}
		list.add(fileName);
		File src= new File(fileName);
		while(i<=numfiles)
		{
			String dest=fname+i+".txt";
		File ds= new File(dest);
			
			FileUtils.copyFile(src,ds);
			list.add(dest);
			i++;
			
		}
		return list;
		
	}
 
	// File size code
 
	public void exportResultToFile(String filename,int executionID) {
      
      }
}

