package DataFormat;

public class Time {
	private String time;
	private int num;
	public Time(String t,int n) {
		time=t;
		num=n;
	}
	public Time() {
		
	}
	public void setTime(String t) {
		time=t;
	}
	public void setNum(int t) {
		num=t;
	}
	public String getTime() {
		return time;
	}
	public int getNum() {
		return num;
	}
}
