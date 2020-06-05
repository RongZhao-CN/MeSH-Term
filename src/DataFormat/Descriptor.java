package DataFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Descriptor extends DatabaseInterface{
	private String mesh_heading;
	private MeshAttri MeshAttri;
	
	public class MeshAttri{
		private List tree_number;
		private List parent;
		private List qual;
		private List entry;
		private List annotation;
		private List rev_date;
		private List entry_date;
		private List date_desp_esta;
		private List consider_also_xref;
		private List forw_cross_ref;
		private List identifier;
		private List pharma_action;
		public List getParent() {
			return this.parent;
		}
		public List getTreeNumber() {
			return this.tree_number;
		}
		public List getEntry() {
			return this.entry;
		}
		public String getAnnotation() {
			String res="";
			if(this.annotation!=null&&this.annotation.size()==1) {
				res=this.annotation.get(0).toString();
			}
			if(this.pharma_action!=null) {
				res+="\npharmacological action:"+this.pharma_action.get(0);
				for(int i=1;i<this.pharma_action.size();i++)
					res+="/"+this.pharma_action.get(i);
			}
			res=res.replace("'", "  ");
			return res;
			
		}
		
		public Date getDate() {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
			Date date=new Date();
			try{
				if(this.entry_date!=null&&this.entry_date.size()>=1) {
					date=sdf.parse(this.entry_date.get(this.entry_date.size()-1).toString());
				
					
				}
				else if(this.date_desp_esta!=null&&this.date_desp_esta.size()>=1) {
					date=sdf.parse(this.date_desp_esta.get(this.date_desp_esta.size()-1).toString());
				
				}
				else if(this.rev_date!=null&&this.rev_date.size()>=1) {
					date=sdf.parse(this.rev_date.get(this.rev_date.size()-1).toString());
					
				}
				
			}catch (Exception e) {  
	            e.printStackTrace();  
	        }  
			return date;
		}
		
		public String getUI() {
			if(this.identifier!=null&&this.identifier.size()>=1) {
				return this.identifier.get(this.identifier.size()-1).toString();
			}
			return "";
		}
	}

	public String getMeshHeading() {
		return this.mesh_heading;
		
	}
	public List getParent() {
		return this.MeshAttri.getParent();
		
	}
	
	public String getUI()
	{
		return this.MeshAttri.getUI();
	}
	public Date getDate()
	{
		return this.MeshAttri.getDate();
	}
	public String getAnnotation()
	{
		return this.MeshAttri.getAnnotation();
	}
	public List getEntry()
	{
		return this.MeshAttri.getEntry();
	}
	@Override
	public List<List> getDataByNum()
	{
		List<List> li=new ArrayList();
		List tmp=new ArrayList();
		List tmp1=new ArrayList();
		tmp=this.getParent();
		if(tmp==null)
			return li;
		for(int i=0;i<tmp.size();i++) {
			if(tmp1.contains(tmp.get(i)))
				continue;
			List list=new ArrayList();
			tmp1.add(tmp.get(i));
			String s1=tmp.get(i).toString().split("&")[0];
			String s2=tmp.get(i).toString().split("&")[1];
			list.add(this.getMeshHeading());
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
    		list.add(s1);
    		list.add("descriptor");
    		list.add(this.getUI());
    		list.add(this.getAnnotation());
    		list.add(sdf.format(this.getDate()));
    		list.add("1");
    		list.add(s2);
    		list.add(this.getMeshHeading());
    		li.add(list);
		}
		return li;
	}
	@Override
	public String get(int n) {
		String res="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
		if(n==5)
			res=this.getUI();
		else if(n==6)
			res=this.getAnnotation();
		else if(n==7)
			res=sdf.format(this.getDate());
		res=res.replace("'", "  ");
		return res;
	}
	
	

	public String toString() {
		return this.mesh_heading+"("+this.MeshAttri.identifier+")";
	}
	
}
