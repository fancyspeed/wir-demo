package BingImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.*;
import conf.Configuration;

/**
 * MRF based Web Image Reranking
 * @author liuzuotao
 *
 */
public class ImageReranking {
	private double lambda = 1;
	private double gama1 = 0.1;
	private double gama2 = 0.2;
	private double gama3 = 0.2;
	private double weight = 100;
	
	
	public BingImageResult[] rerankImages(String query, int nImg) throws IOException {
		ImageClassGetter icg = new ImageClassGetter();
		int nNeg = 200;
		int bins = 8;
		int d = bins*bins*bins;
		
		
		BingImageSearch bis = new BingImageSearch();
		BingImageResult[] result = bis.getImageResult(query, nImg, 0);

		double[][] feat = icg.getImageFeatures(result, nImg, bins);	
		double[][] nfeat = icg.readNegativeFeatures(Configuration.RandomFeatFile, nNeg, bins);
		
		// V1(fi)
		double[] P0 = new double[nImg];
		double[] P1 = new double[nImg];
		for(int i=0; i<nImg; i++) {
			P0[i] = 100/(100 + i);
			P1[i] = lambda * P0[i];
		}
		
		int N = 10;
		double[] DN = new double[N];
		int[] IN = new int[N];
		double D[] = new double[nImg + nNeg];
		for(int i=0; i<nImg; i++) {
			
			// calculate distance
			for(int j=0; j<nImg; j++) {
				D[j] = dist_Euclidean(feat[i], feat[j], d);
			}
			for(int j=0; j<nNeg; j++) {
				D[nImg + j] = dist_Euclidean(feat[i], nfeat[j], d);
			}
			
			// get neighbors
			for(int j=0; j<N; j++) {
				DN[j] = 100;
				IN[j] = -1;
			}
			for(int j=0; j<nImg+nNeg; j++) {
				for(int k=0; k<N; k++) {
					if(D[j] < DN[k]) {
						for(int k2=N-1; k2>k; k2--) {
							DN[k2] = DN[k2-1];
							IN[k2] = IN[k2-1];							
						}
						DN[k] = D[j];
						IN[k] = j;
						break;
					}
				}
			}
			
			// V2(fi)
			for(int j=0; j<N; j++) {
				if(IN[j] <nImg) {
					P1[i] += gama1 * P0[IN[j]];
					P1[i] += gama2 * Math.exp(0 - weight*DN[j]);
				}
				else {
					P1[i] -= gama3 * Math.exp(0 - weight*DN[j]);
				}					
			}
		}
		
		// rerank images
		double[] PR = new double[nImg];
		int[] IR = new int[nImg];
		for(int i=0; i<nImg; i++) {
			PR[i] = -100;
			IR[i] = -1;
		}
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<nImg; j++) {
				if(P1[i] > PR[j]) {
					for(int k=nImg-1; k>j; k--) {
						PR[k] = PR[k-1];
						IR[k] = IR[k-1];
					}
					PR[j] = P1[i];
					IR[j] = i;
					break;
				}
			}
		}
		
		BingImageResult[] result2 = new BingImageResult[nImg];
		for(int i=0; i<nImg; i++) {
			result2[i] = result[IR[i]];
			
			System.out.println(i + "th: " + IR[i] + ", value=" + PR[i]);
		}
		
		return result2;
	}
	
	public double dist_Euclidean(double[] f1, double[] f2, int d) {
		
		if(f1.length<d || f2.length<d) {
			System.out.println("feature dimension errors!");
			System.exit(1);
		}
		
		double D = 0;
		for(int i=0; i<d; i++) {
			D += (f1[i]-f2[i])*(f1[i]-f2[i]);
		}
		
		return D;
	}
}
