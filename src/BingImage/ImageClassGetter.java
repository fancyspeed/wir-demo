package BingImage;

import java.awt.Image;
import java.io.*;

public class ImageClassGetter {

	public double[][] getImageClass(String query, int nImg, int bins) throws FileNotFoundException {
		BingImageSearch bis = new BingImageSearch();
		ImageGetter ig = new ImageGetter();
		Image img;
		int d = bins*bins*bins;
			
		double feat[][] = new double[nImg][d];
		for(int offset=0; offset<nImg; offset+=30) {
			BingImageResult[] result = bis.getImageResult(query, 30, offset);
			if(result == null) {
				System.out.println("can't get image results!");
				System.exit(1);
			}				
			
			for(int i=0; i<result.length; i++) {
				
				System.out.println("processing image: " + offset+i);
				
				img = ig.downloadImage(result[i].getThumbnail());
				feat[i] = ig.getRGBFeature(img, bins);				
			}
		}
		return feat;
	}
	
	public double[][] getImageFeatures(BingImageResult[] result, int nImg, int bins) throws FileNotFoundException {
		
		int d = bins*bins*bins;
		ImageGetter ig = new ImageGetter();
		Image img;
			
		double feat[][] = new double[nImg][d];
		if(result == null) {
			System.out.println("can't get image results!");
			System.exit(1);
		}				
		
		for(int i=0; i<result.length; i++) {
			
			System.out.println("processing image: " + i);
			
			img = ig.downloadImage(result[i].getThumbnail());
			feat[i] = ig.getRGBFeature(img, bins);				
		}

		return feat;
	}
	
	public void writeNegativeFeatures(String filename, double feat[][]) throws FileNotFoundException {
		OutputStream os = new FileOutputStream(filename);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
		
		int nImg = feat.length;
		int d = feat[0].length;
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<d; j++) {
				writer.println(feat[i][j]);
			}
		}
		writer.close();
	}
	
	public double[][] readNegativeFeatures(String filename, int nImg, int bins) throws IOException {
		InputStream is = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		String line;
		int d = bins*bins*bins;
		double[][] feat = new double[nImg][d];
				
		for(int i=0; i<nImg; ++i) {
			for(int j=0; j<d; j++) {
				line = reader.readLine();
				feat[i][j] = Double.valueOf(line).doubleValue();
			}
		}	
	
		reader.close();	
		
		return feat;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		ImageClassGetter icg = new ImageClassGetter();
		double feat[][] = icg.getImageClass("random images", 200, 8);
		icg.writeNegativeFeatures("RandomImages", feat);
	}
}
