package Model;

//import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;


import java.util.*;

public class JSON2Data {
	
	public static String arrayToString(List list,String sep) {
		String res="";
		List li=new ArrayList();
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				if(li.contains(list.get(i)))
					continue;
				li.add(list.get(i));
				res+=list.get(i).toString()+sep;
			}
		}
		return res;
	}
	public static List mySplit(String str,String regx) {
		String[] arr=str.split("|");
		String s1="";
		String s2="";
		List res=new ArrayList();
		boolean isStart=false;
		for(String s:arr) {
			if(s.equals(regx)) {
				isStart=true;
				continue;
			}
			if(isStart)
				s2+=s;
			else
				s1+=s;
			
			
		}
		res.add(s1);
		res.add(s2);
		return res;
		
	}
	public static boolean lengthCheck(String cstr,int n) throws UnsupportedEncodingException{
		int num=cstr.getBytes("utf-8").length;
		if(num>n) {
			return false;
//			System.out.println(cstr);
		}
		return true;
		
	}
	public static void reWriteXML(String fileName,String fileName1) {
		try {
			String url="http://dtd.nlm.nih.gov/ncbi/pubmed/out/";
			String url1="G:\\XMLBulk\\";
			BufferedInputStream bufStream=new BufferedInputStream(new FileInputStream(new File(fileName)));
			BufferedOutputStream bufStreamOut=new BufferedOutputStream(new FileOutputStream(new File(fileName1)));
			int size=0;
			String content=null;
            byte[] buffer=new byte[10240];
            while((size=bufStream.read(buffer))!=-1){
            	content=new String(buffer,0,size);
            	if(content.contains(url)) {
            		content=content.replace(url, "G:\\XMLBulk\\");
            		buffer=content.getBytes();
            		int i=0;
            		for(;i<buffer.length;i++) {
            			if(buffer[i]=='\0')
            				break;
            		}
            		size=i;
            	}
            		
            	bufStreamOut.write(buffer, 0, size);
            }
			//			doc = reader.read(bufStream);  
            bufStreamOut.flush();
            bufStreamOut.close();
            bufStream.close();
			System.out.println(fileName);
		}catch (Exception e) {  
            e.printStackTrace();  
        }
	}
}
