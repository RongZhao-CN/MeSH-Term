import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class IoUsage{
    private static IoUsage INSTANCE = new IoUsage();
    
    private IoUsage(){
    
    }
    public static IoUsage getInstance(){
        return INSTANCE;
    }
    
    public float get() {
        float ioUsage = 0.0f;
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "iostat -d -x";
            //执行命令 
            pro = r.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = null;
            int count =  0;
            //解析输入信息流 计算IO占用率
            while((line=in.readLine()) != null){        
                if(++count >= 4){
                    String[] temp = line.split("\\s+");
                    if(temp.length > 1){
                        float util =  Float.parseFloat(temp[temp.length-1]);
                        ioUsage = (ioUsage>util)?ioUsage:util;
                    }
                }
            }
            if(ioUsage > 0){   
                ioUsage /= 100; 
            }            
            in.close();
            pro.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }    
        return ioUsage;
    }

    public static void main(String[] args) throws InterruptedException {
        while(true){
            System.out.println(IoUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }

}