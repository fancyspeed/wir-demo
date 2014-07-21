package AnnoData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AnnoIO {

	public void writeVocabulary(String filename, String wlist[]) throws FileNotFoundException {
		OutputStream os = new FileOutputStream(filename);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		int nWord = wlist.length;
		writer.println(nWord);
		
		for(int i=0; i<nWord; i++) {
			writer.println(wlist[i]);
		}
		writer.close();
	}
	
	public String [] readVocabulary(String filename) throws NumberFormatException, IOException {
		InputStream is = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	
		int nWord = Integer.parseInt(reader.readLine());		
		String[] wlist = new String[nWord];
		
		for(int i=0; i<nWord; i++) {
			wlist[i] = reader.readLine();
		}
		reader.close();
		
		return wlist;
	}
	
	public double[][] readFeatures(String filename, int nImg, int bins) throws IOException {
		
		String line;
		int d = bins*bins*bins;
		double[][] feat = new double[nImg][d];
		
		InputStream is = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		reader.readLine();
		reader.readLine();
		for(int i=0; i<nImg; i++) {		
			for(int j=0; j<d; j++) {
				line = reader.readLine();
				feat[i][j] = Double.valueOf(line).doubleValue();
			}	
		}
		reader.close();	

		return feat;
	}
	
	public void writeFeatures(String filename, double feat[][]) throws IOException {
		
		int nImg = feat.length;
		int d = feat[0].length;
		
		OutputStream os = new FileOutputStream(filename);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		writer.println(nImg);
		writer.println(d);
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<d; j++) {
				writer.println(feat[i][j]);
			}
		}
		writer.close();
	}
	
	public int[][] readAnnotation(String filename, int nImg, int nLabel) throws IOException {
		String line;
		int[][] labels = new int[nImg][nLabel];
		
		InputStream is = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		reader.readLine();
		reader.readLine();
		for(int i=0; i<nImg; i++) {		
			for(int j=0; j<nLabel; j++) {
				line = reader.readLine();
				labels[i][j] = Integer.parseInt(line);
			}	
		}
		reader.close();	

		return labels;
	}
	
	public void writeAnnotation(String filename, int labels[][]) throws IOException {
		
		int nImg = labels.length;
		int nLabels = labels[0].length;
		
		OutputStream os = new FileOutputStream(filename);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		writer.println(nImg);
		writer.println(nLabels);
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<nLabels; j++) {
				writer.println(labels[i][j]);
			}
		}
		writer.close();
	}
}
