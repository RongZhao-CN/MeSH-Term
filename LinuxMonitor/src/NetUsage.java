import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 收集网速信息
 */
public class NetUsage{
    private static NetUsage INSTANCE = new NetUsage();
    private final static float TotalBandwidth = 1000;    //Mbps
    
    private NetUsage(){
    
    }
    
    public static NetUsage getInstance(){
        return INSTANCE;
    }
    
    /**
     * @Purpose:collect Net usage
     * @param args
     * @return float
     */
    
    public float get() {
        float netUsage = 0.0f;
        Process pro1,pro2;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/net/dev";
            //第一次采集数据
            long startTime = System.currentTimeMillis();
            pro1 = r.exec(command);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String line = null;
            long inSize1 = 0, outSize1 = 0;
            while((line=in1.readLine()) != null){    
                line = line.trim();
                if(line.startsWith("eth0")){
                    String[] temp = line.split("\\s+"); 
                    inSize1 = Long.parseLong(temp[0].substring(5));    
                    outSize1 = Long.parseLong(temp[8]);                
                    break;
                }                
            }    
            in1.close();
            pro1.destroy();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                
            }
            //第二次采集数据
            long endTime = System.currentTimeMillis();
            pro2 = r.exec(command);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            long inSize2 = 0 ,outSize2 = 0;
            while((line=in2.readLine()) != null){    
                line = line.trim();
                if(line.startsWith("eth0")){
                    String[] temp = line.split("\\s+"); 
                    inSize2 = Long.parseLong(temp[0].substring(5));
                    outSize2 = Long.parseLong(temp[8]);
                    break;
                }                
            }
            if(inSize1 != 0 && outSize1 !=0 && inSize2 != 0 && outSize2 !=0){
                float interval = (float)(endTime - startTime)/1000;
                //计算带宽利用率
                float curRate = (float)(inSize2 - inSize1 + outSize2 - outSize1)*8/(1000000*interval);
                netUsage = curRate/TotalBandwidth;
            }                
            in2.close();
            pro2.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }    
        return netUsage;
    }

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        while(true){
            System.out.println(NetUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }
}