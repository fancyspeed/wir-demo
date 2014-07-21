package BingImage;

import com.google.code.bing.search.schema.multimedia.ImageResult;

public class BingImageResult {
	ImageResult result;
	
	public BingImageResult(ImageResult imageResult) {
		result = imageResult;
	}
	
	public String getTitle() {
		return result.getTitle();
	}
	
	public String getThumbnail() {
		return result.getThumbnail().getUrl();
	}
	
	public String getUrl() {
		return result.getUrl();
	}	
	public String getDisplayUrl() {
		return result.getDisplayUrl();
	}
}
