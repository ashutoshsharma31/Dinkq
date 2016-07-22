package com.dinkq.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileOperators {
	private final String BASE = "FileStatus/";
	public void readFile() throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream("input.txt");
			out = new FileOutputStream("output.txt");

			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public String readStatusFromFile(String uid) {
		BufferedReader br = null;
		String status = null;
		File file = null;
		try {
			String sCurrentLine;
			file = new File(BASE+uid+"_c.txt");
			// if file does not exists, then create it
            if (!file.exists()) {
                return null;
            }
			br = new BufferedReader(new FileReader(file));
			status = br.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Status read succesfully "+status);
		return status;
	}
	
	public void writeStatusToFile(String uid, String status) {
		writeCurrentStatusToFile(uid, status);
		writeStatusHistoryToFile(uid, status);
	}

	public void writeStatusHistoryToFile(String uid, String status) {

		BufferedWriter bw = null;
		File file = null;
		try {
			file = new File(BASE+uid+".txt");
			// if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
			bw = new BufferedWriter(new FileWriter(file, true));
			Date date = new Date();
			bw.write(date+" "+uid+" "+status);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {

				}
		}
		
		System.out.println("File wrote successfully"+file.getAbsolutePath());
	}
	public void writeCurrentStatusToFile(String uid, String status) {

		BufferedWriter bw = null;
		File file = null;
		try {
			file = new File(BASE+uid+"_c.txt");
			// if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
			
			
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(status);
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {

				}
		}
		System.out.println("File wrote successfully"+file.getAbsolutePath());
	}
}