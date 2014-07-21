package AnnoData;

import java.io.IOException;

import BingImage.ImageClassGetter;

public class DatasetLoader {

	public void downloadDataset(String sVcb, String sFeat, String sAnno, int ipword, int bins) throws NumberFormatException, IOException {
		AnnoIO aio = new AnnoIO();
		String[] vocabulary = aio.readVocabulary(sVcb);
		
		int d = bins*bins*bins;
		int nWord = vocabulary.length;
		int nImg = nWord*ipword;
		double feat[][] = new double[nImg][d];
		int labels[][] = new int[nImg][nWord];
		for(int i=0; i<nImg; i++) {
			for(int j=0; j<nWord; j++) {
				labels[i][j] = 0;
			}
		}
		
		ImageClassGetter icg = new ImageClassGetter();
		for(int i=0; i<nWord; i++) {
			double[][] f = icg.getImageClass(vocabulary[i], ipword, bins);
			for(int j=0; j<ipword; j++) {
				feat[i*ipword+j] = f[j];
				labels[i*ipword+j][i] = 1;
			}
		}
		
		aio.writeFeatures(sFeat, feat);
		aio.writeAnnotation(sAnno, labels);
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException {
		DatasetLoader dl = new DatasetLoader();
		dl.downloadDataset("E:\\lomboz_workspace\\demo\\vocabulary.txt", 
				"E:\\lomboz_workspace\\demo\\feature.txt", 
				"E:\\lomboz_workspace\\demo\\annotation.txt", 
				30, 8);
	}
}
