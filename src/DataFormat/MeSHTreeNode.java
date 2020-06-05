package DataFormat;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
public class MeSHTreeNode {
	public  String name;
	public List<MeSHTreeNode> child;
	
	public JSONObject getJSONObject() {
		Map<String, Object> map = new HashMap<>();
        map.put("name", name);
       
        JSONObject obj = new JSONObject(map);
        if (child != null&&child.size()!=0) {
//            JSONObject valObj = new JSONObject(child.getJSONObject());
            JSONArray jsonArray = new JSONArray();
            try {
            	for(MeSHTreeNode m:child) {
            		jsonArray.put(m.getJSONObject());
            	}
                obj.put("children", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
	}
	
}
