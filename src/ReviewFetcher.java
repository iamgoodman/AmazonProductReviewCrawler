import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import org.apache.poi.ss.usermodel.Workbook;



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
		work most of time, but some times has  httpexception	B00IITY6QY B013IM0EI4 B013IMJJO4 B013GPHZ7G	B013IMMO4Q B013IM0W36 B013ILWMLW B013ILS8DS B013IM0BUA B013ILTC3I  B013ILS39C B013IMC5OA B013IMF16O B013RU4KN2	 */
			
		//testing the fetcher by implment Asins into an arraylist
		
	ArrayList<String> a = new ArrayList<String>();
	
	
	

	
	a.add("B00IITY6QY");
	
	a.add("B013IM0EI4");
	
	a.add("B013IMJJO4");
	
	a.add("B013GPHZ7G");
	
	a.add("B013IMMO4Q");
	
	a.add("B013IM0W36");
	
	a.add("B013ILWMLW");
	
	a.add("B013ILS8DS");
	
	a.add("B013IM0BUA");

	a.add("B013ILTC3I");
	
	a.add("B013ILS39C");
	
	
	
			
//add sleep method to pause the this program to prevent httpexception 503
	
	

		
	for(int i = 0; i <a.size();i++)	
	
	
{
		
	
		
	Item anitem = new Item(a.get(i));
		

		
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
		
//if server denied the reqeust, fetch again
		if(anitem.reviews.size()==0){
		
		anitem.fetchReview();
		
	}

		
		//sort by date
		
		Collections.sort(anitem.reviews);
		
		//apache POI to populate arraylist to excel and out put excel file
	
		
		
		ListIterator<Review> iteratori = anitem.reviews.listIterator();
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		
		 int rowIndex = 1;

		    while (iteratori.hasNext()) {

		        Review r = iteratori.next();
  
		     
		        /*
		        r.getReviewDate().after(date);*/
          
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
		    
		    
	
		    //only proceed with creating xls if there is no exception to be throw
		    
		    
		  
		    FileOutputStream fileOut = new FileOutputStream(anitem.itemID + " " + "product review.xls");
		    
		    
	        
		    wb.write(fileOut);
	        
		    
		    
	        fileOut.close();
	        

	     
	       System.out.println("file created");
	
		    
	       
		    
    
		    
	}
	

	}
	
	}
	

	




	
	

