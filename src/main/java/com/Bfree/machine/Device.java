package com.Bfree.machine;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.Bfree.spark.Spark;
import com.Bfree.machine.MachineVo;

import java.net.HttpURLConnection;

public class Device {

	public void setThreshold(String name, String ip, String threshold) {
		String query = "?name=" + name + "&ip=" + ip + "&threshold=" + threshold;
		System.out.println(comRaspi("register", query));
	}
	
	public void state(String name, String roomId) {
		Spark machine = new Spark();
		
		String query = "?name=" + name;
		String dataValue = comRaspi("state", query);
		
		String text = "Current value : " + dataValue;
		
		String https_url = "https://api.ciscospark.com/v1/messages";
		machine.connect(https_url, "POST", "roomId", roomId, "text", text);
	}
	
	public void list(String text, String roomId){
		Spark mList = new Spark();
		
		String https_url = "https://api.ciscospark.com/v1/messages";
		mList.connect(https_url, "POST", "roomId", roomId, "text", text);
		
	}
	
	public String comRaspi(String path, String query)
	{
		String https_url = "http://172.30.1.32:80/" + path;
		
		try {
			URL newurl;
			newurl = new URL(https_url + query);
			HttpURLConnection con = (HttpURLConnection) newurl.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String input;
			String res = "";
			while ((input = br.readLine()) != null) {
				res += input;
			}
			br.close();

			return res;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String sortList(List<MachineVo> LMV)
	{

		String text = "";
		int size = LMV.size();
		
		if(size == 0)
		{
			text = "There are no registered devices.";
		}
		else
		{
			for(int i=0; i<size; i++)
			{
				text += (i+1)+". " + LMV.get(i).getName() + "(" + LMV.get(i).getIp() + "), threshold : " + LMV.get(i).getThreshold() + "\n";
			}
		}
		
		return text;
	}

}
