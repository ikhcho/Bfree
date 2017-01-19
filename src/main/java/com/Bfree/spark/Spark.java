package com.Bfree.spark;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

public class Spark {

	public void create_room(String email) {
		String https_url = "https://api.ciscospark.com/v1/rooms";
		String room_name = "Bfree Room #" + email.split("@")[0];
		connect(https_url, "POST", "title", room_name, null, null);
		invite(get_room_detail(room_name), email);
	}

	public void invite(String roomId, String email) {
		String https_url = "https://api.ciscospark.com/v1/memberships";
		connect(https_url, "POST", "roomId", roomId, "personEmail", email);
	}

	public void list_room() {
		String https_url = "https://api.ciscospark.com/v1/rooms";
		connect(https_url, "GET", null, null, null, null);
	}
	
	public void message(String email, String text) {
		String https_url = "https://api.ciscospark.com/v1/messages";
		String room_name = "Bfree Room #" + email.split("@")[0];
		connect(https_url, "POST", "roomId", get_room_detail(room_name), "text", text);
	}
	
	public void alarm(String email) {
		String https_url = "https://api.ciscospark.com/v1/messages";
		String room_name = "Bfree Room #" + email.split("@")[0];
		connect(https_url, "POST", "roomId", get_room_detail(room_name), "text", "Warning!!");
	}

	public void connect(String url, String method, String objName1, String objValue1, String objName2,
			String objValue2) {

		URL newurl;

		try {
			newurl = new URL(url);

			HttpsURLConnection con = (HttpsURLConnection) newurl.openConnection();

			con.setRequestMethod(method);
			con.setRequestProperty("Content-type", "application/json; charset=utf-8");
			con.setRequestProperty("Authorization",
					"Bearer MjNjNmMzMDItMTNhNi00ODZjLWFlNWItZmQzMGY5NTQzZTQwYWMzZDZjNjMtMGEz");
							
			// dump all the content

			JSONObject jsonObj = new JSONObject();
			jsonObj.put(objName1, objValue1);
			jsonObj.put(objName2, objValue2);
			String sendMsg = jsonObj.toJSONString();

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(sendMsg);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String get_room_detail(String title) {
		String https_url = "https://api.ciscospark.com/v1/rooms";
		URL newurl;
		try {
			newurl = new URL(https_url);

			HttpsURLConnection con = (HttpsURLConnection) newurl.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json; charset=utf-8");
			con.setRequestProperty("Authorization",
					"Bearer MjNjNmMzMDItMTNhNi00ODZjLWFlNWItZmQzMGY5NTQzZTQwYWMzZDZjNjMtMGEz");
			// dump all the content

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String input;
			String list = "";
			while ((input = br.readLine()) != null) {
				list += input;
			}
			br.close();

			return roomName(list, title);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String roomName(String list, String title) {
		try {
			JSONParser jsonParser = new JSONParser();

			// JSON데이터를 넣어 JSON Object 로 만들어 준다.
			JSONObject jsonObject = (JSONObject) jsonParser.parse(list);

			// books의 배열을 추출
			JSONArray InfoArray = (JSONArray) jsonObject.get("items");

			for (int i = 0; i < InfoArray.size(); i++) {

				// 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
				JSONObject Obj = (JSONObject) InfoArray.get(i);

				if (Obj.get("title").equals(title)) {
					String roomId = Obj.get("id").toString();
					return roomId;
				}
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
