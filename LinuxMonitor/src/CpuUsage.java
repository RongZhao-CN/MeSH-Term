import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;


public class CpuUsage{
	private static CpuUsage INSTANCE = new CpuUsage();
	
	private CpuUsage(){
	
	}
	
	public static CpuUsage getInstance(){
		return INSTANCE;
	}
	

	public float get() {
		
		float cpuUsage = 0;
		Process pro1,pro2;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "cat /proc/stat";
			//��һ�βɼ�CPUʱ��
			long startTime = System.currentTimeMillis();
			pro1 = r.exec(command);//ִ�������ȡ/proc/stat�ļ�
			BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
			String line = null;
			long idleCpuTime1 = 0, totalCpuTime1 = 0;	//�ֱ�Ϊϵͳ��������е�CPUʱ����ܵ�CPUʱ��
			while((line=in1.readLine()) != null){	
				if(line.startsWith("cpu")){//�����ļ�����ȡCPU��Ϣ
					line = line.trim();
					String[] temp = line.split("\\s+"); 
					idleCpuTime1 = Long.parseLong(temp[4]);
					for(String s : temp){
						if(!s.equals("cpu")){
							totalCpuTime1 += Long.parseLong(s);
						}
					}
					break;
				}						
			}	
			in1.close();
			pro1.destroy();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
			}
			//�ڶ��βɼ�CPUʱ��
			long endTime = System.currentTimeMillis();
			pro2 = r.exec(command);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
			long idleCpuTime2 = 0, totalCpuTime2 = 0;	//�ֱ�Ϊϵͳ��������е�CPUʱ����ܵ�CPUʱ��
			while((line=in2.readLine()) != null){	
				if(line.startsWith("cpu")){
					line = line.trim();
					String[] temp = line.split("\\s+"); 
					idleCpuTime2 = Long.parseLong(temp[4]);
					for(String s : temp){
						if(!s.equals("cpu")){
							totalCpuTime2 += Long.parseLong(s);
						}
					}
					break;	
				}								
			}
			if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){
				cpuUsage = 1 - (float)(idleCpuTime2 - idleCpuTime1)/(float)(totalCpuTime2 - totalCpuTime1);//����CPU������
			}				
			in2.close();
			pro2.destroy();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
		}	
		return cpuUsage;
	}
 
	
	public static void main(String[] args) throws InterruptedException {
		while(true){
			System.out.println(CpuUsage.getInstance().get());
			Thread.sleep(5000);		
		}
	}
}