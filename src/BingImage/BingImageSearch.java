package BingImage;

import com.google.code.bing.search.client.*;
import com.google.code.bing.search.client.BingSearchClient.SearchRequestBuilder;
import com.google.code.bing.search.schema.*;
import com.google.code.bing.search.schema.multimedia.ImageResponse;
import com.google.code.bing.search.schema.multimedia.ImageResult;

/**
 * 
 * @author liuzuotao
 *
 */
public class BingImageSearch {
	private String applicationId = "C956C16BD772AD1842028D51B05D0D790344EE9F";

	public BingImageResult[] getImageResult(String query, int nResult, int offset) {	
		BingSearchServiceClientFactory factory = BingSearchServiceClientFactory.newInstance();
		BingSearchClient client = factory.createBingSearchClient();
	
		SearchRequestBuilder builder = client.newSearchRequestBuilder();
		builder.withAppId(applicationId);
		builder.withQuery(query);
		builder.withSourceType(SourceType.IMAGE);
		builder.withVersion("2.0");
		builder.withMarket("en-us");
		builder.withAdultOption(AdultOption.MODERATE);
		builder.withSearchOption(SearchOption.ENABLE_HIGHLIGHTING);
		builder.withImageRequestCount(new Long(nResult));
		builder.withImageRequestOffset(new Long(offset));
	
		System.out.println("try to process query: " + query);
		
		SearchResponse response = client.search(builder.getResult());	
		ImageResponse imgresponse = response.getImage();
		
		System.out.println("total results: " + imgresponse.getTotal());
		
		if(imgresponse != null) {
			Object[] result = imgresponse.getResults().toArray();
			BingImageResult[] bingresult = new BingImageResult[result.length];
			for ( int i=0; i<result.length; i++) {
				bingresult[i] = new BingImageResult((ImageResult)(result[i]));
	//			System.out.println(bingresult[i].getTitle());
	//			System.out.println(bingresult[i].getThumbnail());
	//			System.out.println(bingresult[i].getDisplayUrl());
			}
			
			return bingresult;
		}
		
		return null;
	}
	
	public static void main(String args[]) {
		new BingImageSearch().getImageResult("random funny images", 10, 0);
	}
}
