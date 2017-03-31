package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SettingsLoader {
	
	public static int maxFood1 = 2000, maxFood2 = 50, maxFood3 = 10, maxPlayers = 100, serverPort = 25700;
	public static File configFile = new File("config.YML");
	
	public static void init() {
		if(configFile.exists()) {
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
				String line = "";
				boolean found = false;
				try {
					while((line = br.readLine()) != null) {
						
						if(line.startsWith("maxFood1")){
							
							maxFood1 = Integer.parseInt(line.substring(line.lastIndexOf("=")+1, line.length()));
							found = true;
						}else if(line.startsWith("maxFood2")){
							
							maxFood2 = Integer.parseInt(line.substring(line.lastIndexOf("=")+1, line.length()));
							found = true;
							
						}else if(line.startsWith("maxFood3")){
							maxFood2 = Integer.parseInt(line.substring(line.lastIndexOf("=")+1, line.length()));
							found = true;
						}else if(line.startsWith("maxPlayers")){
							maxPlayers = Integer.parseInt(line.substring(line.lastIndexOf("=")+1, line.length()));
							found = true;
						}else if(line.startsWith("serverPort")){
							serverPort = Integer.parseInt(line.substring(line.lastIndexOf("=")+1, line.length()));
							found = true;
						}else if(!found){
							save();
						}
						
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
		}else {
			save();
		}
	}
	
	public static void save() {
		try {
			PrintWriter pw = new PrintWriter(configFile);
			
			pw.println("maxFood1="+maxFood1);
			pw.println("maxFood2="+maxFood2);
			pw.println("maxFood3="+maxFood3);
			pw.println("maxPlayers="+maxPlayers);
			pw.println("serverPort="+serverPort);
			pw.flush();
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

}
