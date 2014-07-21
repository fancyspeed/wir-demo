package AnnoData;

import java.awt.Image;
import java.io.IOException;

import conf.Configuration;
import BingImage.BingImageResult;
import BingImage.ImageGetter;
import BingImage.ImageReranking;

public class Annotater {

	public String[] annotation(Image img, int nLabel) throws NumberFormatException, IOException {
		AnnoIO aio = new AnnoIO();
		String[] vocabulary = aio.readVocabulary(Configuration.VocabularyFile);
		
		int nWord = vocabulary.length;
		System.out.println("size of vocabulary:" + nWord);
		
		int pImg = Configuration.PIMGS;
		int bins = Configuration.BINS;
		int nImg = nWord * pImg;
		int d = bins*bins*bins;
		double[][] feat = aio.readFeatures(Configuration.AnnoFeatureFile, nImg, bins);
		int[][] anno = aio.readAnnotation(Configuration.AnnotationFile, nImg, nWord);
		
		System.out.println("size of anno:" + anno.length + "," + anno[0].length);
		
		ImageGetter ig = new ImageGetter();
		double[] f = ig.getRGBFeature(img, 8);
		
		double[] D = new double[nImg];
		ImageReranking ir = new ImageReranking();
		for(int i=0; i<nImg; i++) {
			D[i] = ir.dist_Euclidean(feat[i], f, d);
			
		//	System.out.println(i +",dist: " + D[i]);
		}
		
		//find neighbors
		// get neighbors
		int N = 10;
		int[] IN = new int[N];
		double[] DN = new double[N];
		for(int j=0; j<N; j++) {
			DN[j] = 100;
			IN[j] = -1;
		}
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<N; j++) {
				if(D[i] < DN[j]) {
					for(int k=N-1; k>j; k--) {
						DN[k] = DN[k-1];
						IN[k] = IN[k-1];							
					}
					DN[j] = D[i];
					IN[j] = i;
					break;
				}
			}
		}
		
		// get probability
		double[] L = new double[nWord];
		for(int i=0; i<nWord; i++) {
			L[i] = 0;
		}
		for(int i=0; i<N; i++) {
			System.out.println("neig " + i + ": " + IN[i] + ",dist: " + DN[i]);
			double p = Math.exp(0 - 10 * DN[i]);
			for(int j=0; j<nWord; j++) {
				L[j] += p * anno[IN[i]][j];
			}
		}
		
		for(int i=0; i<nWord; i++) {
			System.out.println(i + "th: " + vocabulary[i] + ", vaule=" + L[i]);
		}

	
		// rank labels
		int[] LN = new int[nLabel];
		double[] PN = new double[nLabel];
		for(int j=0; j<nLabel; j++) {
			PN[j] = -1;
			LN[j] = -1;
		}
		for(int i=0; i<nWord; i++) {
			for(int j=0; j<nLabel; j++) {
				if(L[i] > PN[j]) {
					for(int k=nLabel-1; k>j; k--) {
						PN[k] = PN[k-1];
						LN[k] = LN[k-1];							
					}
					PN[j] = L[i];
					LN[j] = i;
					break;
				}
			}
		}
	
		String[] result2 = new String[nLabel];
		for(int i=0; i<nLabel; i++) {
			result2[i] = vocabulary[LN[i]];
			
			
		}
		return result2;
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException {
		ImageGetter ig = new ImageGetter();
		Image img = ig.getImage("E:\\lomboz_workspace\\demo\\4.jpg");
		Annotater annotater = new Annotater();
		String[] labels = annotater.annotation(img, 2);
		
		for(int i=0; i<2; i++) {
			System.out.println(labels[i]);
		}
	}
}
