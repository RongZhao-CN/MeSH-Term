package DataFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class SCRs extends DatabaseInterface{
	private String substance_name;
	private SCRAttri SCRAttri;
	public class SCRAttri{
		private List idenfitier;
		private List note;
		private List synonym;
		private List source;
		private List Heading_mapped;
		private List pharma_action;
		private List Heading_related;
		private List rev_date;
		private List entry_date;
		private List type;
		public String getType() {

			return this.type.get(this.type.size()-1).toString();
		}
		
		public String getNote() {
			String res="";
			if(this.note!=null&&this.note.size()==1) {
				res+=this.note.get(0).toString();
			}
			if(this.pharma_action!=null) {
				res+="\npharmacological action:"+this.pharma_action.get(0);
				for(int i=1;i<this.pharma_action.size();i++)
					res+="/"+this.pharma_action.get(i);
			}
			res=res.replace("'", "  ");
			return res;
			
		}
		public String getSource() {
			String res="";
			if(this.source!=null&&this.source.size()>=1) {
				for(int i=0;i<this.source.size();i++) {
					res+=this.source.get(i).toString();
				}
			}
			return res;
		}
		public Date getDate() {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
			Date date=new Date();
			try{
				if(this.entry_date!=null&&this.entry_date.size()>=1) {
					date=sdf.parse(this.entry_date.get(this.entry_date.size()-1).toString());
					
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
			if(this.idenfitier!=null&&this.idenfitier.size()>=1) {
				return this.idenfitier.get(this.idenfitier.size()-1).toString();
			}
			return "";
		}
	}
	public String getName() {
		return this.substance_name;
	}
	public String getType() {
		return this.SCRAttri.getType();
	}
	@Override
	public List<List> getDataByNum()
	{
		List<List> li=new ArrayList();
		List list=new ArrayList();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
		list.add(this.substance_name);
		list.add(this.getType());
		list.add("SCRs");
		list.add(this.SCRAttri.getUI());
		list.add(this.SCRAttri.getNote());
		list.add(sdf.format(this.SCRAttri.getDate()));
		list.add("1");
		list.add(this.SCRAttri.getSource());
		
		list.add(this.substance_name);
		li.add(list);
		return li;
	}
	@Override
	public String get(int n) {
		String res="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
		if(n==5)
			res=this.SCRAttri.getUI();
		else if(n==6)
			res=this.SCRAttri.getNote();
		else if(n==7)
			res=sdf.format(this.SCRAttri.getDate());
		res=res.replace("'", "  ");
		return res;
	}
	public String toString() {
		return this.substance_name+"("+this.SCRAttri.idenfitier+")";
	}
}
