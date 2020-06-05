package DataFormat;

public class TermContent {
	private String name;
	private String pharm_action;
	private String date;
	private String subject;
	private String description;
	public void setName(String n) {
		name=n;
	}
	public void setPharmAction(String n) {
		pharm_action=n;
	}
	public void setDate(String n) {
		date=n;
	}
	public void setSubject(String n) {
		subject=n;
	}
	public void setDescription(String n) {
		description=n;
	}
	public String getName() {
		return name;
	}
	public String getPharmAction() {
		return pharm_action;
	}
	public String getDate() {
		if(date.contains("00:00:00"))
			date=date.split("00:00:00")[0];
		return date;
	}
	public String getSubject() {
		return subject;
	}
	public String getDescription() {
		return description;
	}
	public String toString() {
		return name+"_"+subject;
	}
}
