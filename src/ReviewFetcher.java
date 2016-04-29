import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;



public class ReviewFetcher {

	

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, ParseException,
			ClassNotFoundException, SQLException, InvalidKeyException,
			NoSuchAlgorithmException, InterruptedException {
		

/*
	Create item you are trying to search for	*/
		
		/*	
		work most of time, but some times has  httpexception	B00IITY6QY B013IM0EI4 B013IMJJO4 B013GPHZ7G	B013IMMO4Q B013IM0W36 B013ILWMLW B013ILS8DS B013IM0BUA*/
			
		
	
		
		
	/*	illegal state exception	B00PGCD8WY B013ILTC3I B013IMF16O B013RU4KN2	 */
			
		
/*		
		only show 1 review for B013ILS39C suppose to have 13, showing illegalstate exception*/

		/*only be able to display 3 for B013IMC5OA suppose to have 5*/
		
/* URL http://www.amazon.com/product-reviews/B00PGCD8WY/?showViewpoints=0&sortBy=byRankDescending&pageNumber=1 is correct, but experiences javaIO or jave illegal exception 
*/		Item anitem = new Item("B00IITY6QY  ");
		

		
		/*
	Standard Amazon Sign in  API*/

// Input to Sign;
		SignedRequestsHelper helper = new SignedRequestsHelper();
		
		Map<String, String> variablemap = new HashMap<String, String>();
		//*****ADD YOUR AssociateTag HERE*****
		variablemap.put("AssociateTag", "");
		variablemap.put("Operation", "ItemLookup");
		variablemap.put("Service", "AWSECommerceService");
		variablemap.put("ItemId", anitem.itemID);
		variablemap.put("ResponseGroup", "Large");

		// Sign and get the REST url;
	
		helper.sign(variablemap);
		
		
/*		testing on most popular item
		*/
		
		
		
		anitem.fetchReview();
		

		
		System.out.println(anitem.reviews.size());
		
	
		
		for(int i = 0; i < anitem.reviews.size(); i++){
			
			
			
			
			System.out.println(anitem.reviews.get(i).getReviewDate());
			
			
		}
		

			
		
		
		//apache POI to populate arraylist to excel and out put excel file
		

	
	

}


	
	

