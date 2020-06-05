package Model;
import java.util.*;
import DataFormat.*;

public class DatabaseOperation {
	private static Database db;
	private JSON2Data j2d;
	private HashMap<String,String> UI2MeSH;
	private HashMap<String,String> MeSH2UI;
	public DatabaseOperation() {
		db=new Database();
		MeSH2UI=new HashMap<String,String>();
		MeSH2UI=db.getMapFromTableField("ui_map",2,1);
		UI2MeSH=new HashMap<String,String>();
		UI2MeSH=db.getMapFromTableField("ui_map",1,2);
	}
	public TermContent getInformation(String term) {
		TermContent res=new TermContent();
		Map<String,String> m=new HashMap<String,String>();
		
		m=db.getInformation(term);
		m.forEach((key,value)->{
			
			switch(key) {
			case "name":res.setName(value);break;
			case "subject":res.setSubject(value);break;
			case "date":res.setDate(value);break;
			case "description":res.setDescription(value);break;
			case "pharm_action":res.setPharmAction(value);break;
			}
		});
		
		return res;
		
	}
	public List<Time> getTimeInfo(String limit) {
		List<Time> res=new ArrayList<Time>();
		TreeMap<String,Integer> m=new TreeMap<String,Integer>();
		String table="date";
		m=db.getInformationBinary(table,1,2);
		int i=0;
		boolean isStart=false;
		
		for(Map.Entry<String,Integer> entry:m.entrySet()){
			
			if(isStart) {
				Time t=new Time(entry.getKey(),entry.getValue());
				res.add(t);
			}
			if(limit.contains(entry.getKey()))
				isStart=true;
            
        }

		return res;
		
	}
	public TreeMap<String,Integer> getTimeInfo1() {
		
		TreeMap<String,Integer> m=new TreeMap<String,Integer>();
		String table="date";
		m=db.getInformationBinary(table,1,2);
		
		return m;
		
	}
	public Map<String,Integer> getTimeInfo2() {
		
		Map<String,Integer> m=new HashMap<String,Integer>();
		String table="journal";
		m=db.getInformationFilter(table,1,2);
		return m;
		
	}
	public Set<Integer> getRandomList(int n,int min,int max)
	{
		Set<Integer> res=new HashSet<Integer>();
		for(int i=0;i<n;i++)
			res.add((int)(Math.random()*(max-min)+min));
		return res;
	}
	public List<TermContent> getInformationLink(String name)
	{ 
		String table="term_links";
		List<TermContent> res=new ArrayList<TermContent>();
		String term=MeSH2UI.get(name);
		System.out.println(name+":"+term);
		ArrayList<List> arr=new ArrayList<List>();
		List<Integer> var=new ArrayList<Integer>();
		var.add(2);var.add(3);
		arr=db.getInformationList(table,term,1,var);
		
		for(List l:arr) {
			for(String s:l.get(0).toString().split("&")) {
				if(!"".equals(s)) {
					TermContent tmp=new TermContent();
					s=UI2MeSH.get(s);
					System.out.println(s);
					tmp=getInformation(s);
					res.add(tmp);
				}
			}
			
		}
		System.out.println(res.size());
		return res;
	}
	public List<MeSHTreeNode> getMeSHTree(String term)
	{
		List<MeSHTreeNode> res=new ArrayList<MeSHTreeNode>();
		
		
		
		ArrayList<List> info=new ArrayList<List>();
		String table="mesh_tree";
		List var=new ArrayList();
		var.add(1);var.add(2);var.add(3);var.add(4);
		info=db.getInformationList(table,term,2,var);
		if(info==null)
			return null;
		for(int i=0;i<info.size();i++) {
			
			String path=info.get(i).get(0).toString();
			String parent=info.get(i).get(3).toString();
			String name=info.get(i).get(1).toString();
			int level=Integer.parseInt(info.get(i).get(2).toString());
			MeSHTreeNode root=new MeSHTreeNode();
			MeSHTreeNode node=new MeSHTreeNode();
			node.name=name;
			ArrayList<List> info1=new ArrayList<List>();
			info1=db.getInformationList(table,parent,2,var);
			String treen="";
			root.name=parent;
			
			if(info1!=null) {
				for(int j=0;j<info1.size();j++) {
					if(path.contains(info1.get(j).get(0).toString())) {
						treen=info1.get(i).get(0).toString();
						root.child=db.getChild(table,info1.get(0).get(1).toString(),String.valueOf(level),3);
						
						break;
					}
						
				}
			}
			if(root.child==null) {
				root.child=new ArrayList<MeSHTreeNode>();
				node.child=db.getChild(table,name,String.valueOf(level+1),3);
				root.child.add(node);
			}
			else {
				for(MeSHTreeNode m:root.child) {
					m.child=db.getChild(table,m.name,String.valueOf(level+1),3);
				}
			}
			
			res.add(root);
		}
		return res;
	}
	public List<List> getResearcherMap(int threshold,int limit,List<Integer> type){
		List<List> res=new ArrayList<List>();
		Map<String,List<Integer>> journal=new HashMap<String,List<Integer>>();
		Map<String,List<Integer>> article=new HashMap<String,List<Integer>>();
		Map<String,String> map=new HashMap<String,String>();
		
		String name="ui_map";
		map=db.getMapFromTableField(name,1,2);
		name="term_paper_web";
		
		
		article=db.getInformationFilterNormal(name,1,threshold,limit,type);
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();  
        while(it.hasNext()){  
            Map.Entry<String, String> entry = it.next();  
            if(!article.containsKey(entry.getKey()))  
                it.remove();//使用迭代器的remove()方法删除元素  
        }
		name="researcher";
		journal=db.getInformationFilterNormal(name,1,map);
		Iterator<Map.Entry<String, List<Integer>>> it1 = article.entrySet().iterator();  
        while(it1.hasNext()){  
        	
            Map.Entry<String, List<Integer>> entry = it1.next();  
            String key=entry.getKey();
            List li=new ArrayList();
            List<Integer> t1=entry.getValue();
            List<Integer> t0=journal.get(key);
            int sum0=t0.get(1)+t0.get(2)+t0.get(3);
            if(sum0>(int)(limit*0.85))
            	continue;
            int sum=(t1.get(1)+t1.get(2)+t1.get(3));
            double tt=sum/(1.0*t1.get(0));
            sum=(int)(tt*100);
            
            li.add(sum0);
            li.add(t0.get(0));
            
            li.add(t1.get(0));
            li.add(sum);
            
            li.add(t1.get(1));
            li.add(t1.get(2));
            li.add(t1.get(3));
            String s=map.get(key);
            s=s.replace("'","");
            li.add("'"+s+"'");
            res.add(li);
        }
        return res;
	}
	
	

}
