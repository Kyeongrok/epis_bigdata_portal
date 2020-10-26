import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateGeoJson {
	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\JHY\\Desktop\\대한민국 지도SHP\\새 폴더\\";
		splitFileEMD(path);
		splitFileSIGG(path);
		splitFileSIDO(path);
	}

	public static void splitFileEMD(String path) throws Exception {
		File file = new File(path + "emd_all.geojson");

		if(!file.exists()) {
			return;
		}
		
		InputStream is = null;
		String data = null;

		try {
			is = new FileInputStream(file);
			byte[] buffer = new byte[is.available()];
			while (is.read(buffer, 0, buffer.length) != -1) {
				data = new String(buffer);
			}
		} finally {
			is.close();
		}
	
		JSONObject obj = new JSONObject(data);
		HashMap<String, JSONArray> geoMap = new HashMap<String, JSONArray>();

		JSONArray features = obj.getJSONArray("features");
		for (int i = 0; i < features.length(); i++) {
			JSONObject feature = features.getJSONObject(i);
			JSONObject properties = feature.getJSONObject("properties");

			String sidoCd = properties.getString("EMD_CD").substring(0, 2);
			String siggCd = properties.getString("EMD_CD").substring(0, 5);

			// 시 별로
			JSONArray arr = null;
			if (geoMap.containsKey(sidoCd)) {
				arr = geoMap.get(sidoCd);
			} else {
				arr = new JSONArray();
				geoMap.put(sidoCd, arr);
			}
			arr.put(feature);

			// 시군구 별로
			if (geoMap.containsKey(siggCd)) {
				arr = geoMap.get(siggCd);
			} else {
				arr = new JSONArray();
				geoMap.put(siggCd, arr);
			}
			arr.put(feature);			
		}

		for (Map.Entry<String, JSONArray> entry : geoMap.entrySet()) {
			String key = entry.getKey();
			JSONArray value = entry.getValue();

			File tofile = null;
			if(key.length() > 2) {
				tofile = new File(path + "sigg_emd\\emd_" + key + ".geojson");
			} else {
				tofile = new File(path + "emd\\emd_" + key + ".geojson");
			}
			if (!tofile.getParentFile().exists()) {
				tofile.getParentFile().mkdirs();
			}
			
			OutputStream os = null;
			try {
				os = new FileOutputStream(tofile);
				os.write(("{\r\n" + 
					"\"type\": \"FeatureCollection\",\r\n" + 
					"\"name\": \"emd_" + key + "\",\r\n" + 
					"\"features\": [\r\n").getBytes());
				
				for (int i = 0; i < value.length(); i++) {
					os.write(value.getJSONObject(i).toString().getBytes());
					if(i < value.length()-1) os.write(",".getBytes());
					os.write("\r\n".getBytes());
				}
				
				os.write("]}".getBytes());
			} finally {
				os.close();
			}

		}
	}

	public static void splitFileSIGG(String path) throws Exception {
		File file = new File(path + "sigg_all.geojson");
		
		if(!file.exists()) {
			return;
		}
		
		InputStream is = null;
		String data = null;

		try {
			is = new FileInputStream(file);
			byte[] buffer = new byte[is.available()];
			while (is.read(buffer, 0, buffer.length) != -1) {
				data = new String(buffer);
			}
		} finally {
			is.close();
		}

		JSONObject obj = new JSONObject(data);
		HashMap<String, JSONArray> geoMap = new HashMap<String, JSONArray>();

		JSONArray features = obj.getJSONArray("features");
		for (int i = 0; i < features.length(); i++) {
			JSONObject feature = features.getJSONObject(i);
			JSONObject properties = feature.getJSONObject("properties");

			String sidoCd = properties.getString("SIG_CD").substring(0, 2);
			
			// 시 별로
			JSONArray arr = null;
			if (geoMap.containsKey(sidoCd)) {
				arr = geoMap.get(sidoCd);
			} else {
				arr = new JSONArray();
				geoMap.put(sidoCd, arr);
			}
			arr.put(feature);
		}

		for (Map.Entry<String, JSONArray> entry : geoMap.entrySet()) {
			String key = entry.getKey();
			JSONArray value = entry.getValue();

			File tofile = new File(path + "sigg\\sigg_" + key + ".geojson");
			if (!tofile.getParentFile().exists()) {
				tofile.getParentFile().mkdirs();
			}
			OutputStream os = null;
			try {
				os = new FileOutputStream(tofile);
				os.write(("{\r\n" + 
					"\"type\": \"FeatureCollection\",\r\n" + 
					"\"name\": \"sigg_" + key + "\",\r\n" + 
					"\"features\": [\r\n").getBytes());
				
				for (int i = 0; i < value.length(); i++) {
					os.write(value.getJSONObject(i).toString().getBytes());
					if(i < value.length()-1) os.write(",".getBytes());
					os.write("\r\n".getBytes());
				}
				
				os.write("]}".getBytes());
			} finally {
				os.close();
			}
		}
	}
	
	public static void splitFileSIDO(String path) throws Exception {
		File file = new File(path + "sido_all.geojson");
		
		if(!file.exists()) {
			return;
		}
		
		InputStream is = null;
		String data = null;

		try {
			is = new FileInputStream(file);
			byte[] buffer = new byte[is.available()];
			while (is.read(buffer, 0, buffer.length) != -1) {
				data = new String(buffer);
			}
		} finally {
			is.close();
		}

		JSONObject obj = new JSONObject(data);
		HashMap<String, JSONArray> geoMap = new HashMap<String, JSONArray>();

		JSONArray features = obj.getJSONArray("features");
		for (int i = 0; i < features.length(); i++) {
			JSONObject feature = features.getJSONObject(i);
			JSONObject properties = feature.getJSONObject("properties");

			String sidoCd = properties.getString("CTPRVN_CD").substring(0, 2);
			
			// 시 별로
			JSONArray arr = null;
			if (geoMap.containsKey(sidoCd)) {
				arr = geoMap.get(sidoCd);
			} else {
				arr = new JSONArray();
				geoMap.put(sidoCd, arr);
			}
			arr.put(feature);
		}

		for (Map.Entry<String, JSONArray> entry : geoMap.entrySet()) {
			String key = entry.getKey();
			JSONArray value = entry.getValue();

			File tofile = new File(path + "sido\\sido_" + key + ".geojson");
			if (!tofile.getParentFile().exists()) {
				tofile.getParentFile().mkdirs();
			}
			OutputStream os = null;
			try {
				os = new FileOutputStream(tofile);
				os.write(("{\r\n" + 
					"\"type\": \"FeatureCollection\",\r\n" + 
					"\"name\": \"sido_" + key + "\",\r\n" + 
					"\"features\": [\r\n").getBytes());
				
				for (int i = 0; i < value.length(); i++) {
					os.write(value.getJSONObject(i).toString().getBytes());
					if(i < value.length()-1) os.write(",".getBytes());
					os.write("\r\n".getBytes());
				}
				
				os.write("]}".getBytes());
			} finally {
				os.close();
			}
		}
	}	
}
