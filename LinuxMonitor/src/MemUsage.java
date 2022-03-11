import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 收集内存使用率数据
 */
public class MemUsage{
    private static MemUsage INSTANCE = new MemUsage();
    
    private MemUsage(){
    
    }
    
    public static MemUsage getInstance(){
        return INSTANCE;
    }
    
   
   
    public float get() {
       
        float memUsage = 0.0f;
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/meminfo";
            //执行读取命令
            pro = r.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = null;
            int count = 0;
            long totalMem = 0, freeMem = 0;
             while((line=in.readLine()) != null){       
                String[] memInfo = line.split("\\s+");
                if(memInfo[0].startsWith("MemTotal")){
                	//获取总内存
                    totalMem = Long.parseLong(memInfo[1]);
                }
                if(memInfo[0].startsWith("MemFree")){
                	//获取空闲内存
                    freeMem = Long.parseLong(memInfo[1]);
                }
                //计算内存利用率
                memUsage = 1- (float)freeMem/(float)totalMem;
           
                if(++count == 2){
                    break;
                }                
            }
            in.close();
            pro.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }    
        return memUsage;
    }
    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        while(true){
            System.out.println(MemUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }
}