import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
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
*/		Item anitem = new Item("B013ILTC3I  ");
		

		
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
		
	
/*		
		for(int i = 0; i < anitem.reviews.size(); i++){
			
			
			
			
			System.out.println(anitem.reviews.get(i).getReviewDate());
			
			
		}
		

			
		*/
		
		//apache POI to populate arraylist to excel and out put excel file
		
		
		ListIterator<Review> iteratori = anitem.reviews.listIterator();
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		
		 int rowIndex = 1;

		    while (iteratori.hasNext()) {

		        Review r = iteratori.next();
           
           
		        Row row = sheet.createRow(rowIndex++);
            
		        sheet.createRow(0).createCell(0).setCellValue("ASIN");
		        sheet.getRow(0).createCell(1).setCellValue("ReviewID");
		        sheet.getRow(0).createCell(2).setCellValue("CustomerName");
		        sheet.getRow(0).createCell(3).setCellValue("Rating Received");
		        sheet.getRow(0).createCell(4).setCellValue("Maxium Rating");
		        sheet.getRow(0).createCell(5).setCellValue("Is verified Purchase?");
		        sheet.getRow(0).createCell(6).setCellValue("RealName");
		        sheet.getRow(0).createCell(7).setCellValue("ReviewDate");
		        sheet.getRow(0).createCell(8).setCellValue("Comment Header");
		        sheet.getRow(0).createCell(9).setCellValue("Comment");
		    

  
                
		        row.createCell(0).setCellValue(r.getItemID());
		        row.createCell(1).setCellValue(r.getReviewID());
		        row.createCell(2).setCellValue(r.getCustomerName());
		        row.createCell(3).setCellValue(r.getRating());
		        row.createCell(4).setCellValue(r.getFullRating());
		        row.createCell(5).setCellValue(r.isVerifiedPurchase());
		        row.createCell(6).setCellValue(r.getRealName());
		        row.createCell(7).setCellValue(r.getReviewDate().toString());
		        row.createCell(8).setCellValue(r.getTitle());
		        row.createCell(9).setCellValue(r.getContent());
		        
		      
		        
		    }
		    
		    
	
		  
		    FileOutputStream fileOut = new FileOutputStream("workbook7.xls");
		    
		    
	        
		    wb.write(fileOut);
	        
		    
		    
	        fileOut.close();
	        
	        
	        
	       System.out.print("file created");
		    
	}
	
	
	
	
	

}


	
	

