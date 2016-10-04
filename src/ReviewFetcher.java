

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Async Computation for fetching review
 * @author Feiyi(Joey) Xiang 
 *         
 */

public class ReviewFetcher {

	

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InterruptedException
	 */
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ParseException,
			ClassNotFoundException, SQLException, InvalidKeyException,
			NoSuchAlgorithmException, InterruptedException {
	

	
		//initialize hashtable
		final Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		
		//add proxy list to hash table
		ht.put("137.135.166.225", 8133);
		ht.put("52.43.200.172", 1080);
		ht.put("8.38.145.178", 48111);
		ht.put("45.40.143.57", 80);
		ht.put("68.100.232.240", 44180);
		ht.put("52.59.23.236", 80);
		
		
		
	          
//To read input ASINs as excel formatt and parse into java, stored as arrayList
	
	
	/*	//counter to keep track of how many review fetched
		int count = 0;*/
		
		//using class to increment
		final FinalCounter count = new FinalCounter(0);
		 // Location of the source file
        String sourceFilePath = "Z:\\Staffs\\Joey\\Developer\\SKUlist\\bgc.xls";
          
        FileInputStream fileInputStream = null;
          
        // Array List to store the excel sheet data
        //original version
        //final ArrayList excelData = new ArrayList();
        final ArrayList<String> excelData = new ArrayList<String>();
          
        try {
              
        	
            // FileInputStream to read the excel file
            fileInputStream = new FileInputStream(sourceFilePath);
   
            // Create an excel workbook
            HSSFWorkbook excelWorkBook = new HSSFWorkbook(fileInputStream);
              
            // Retrieve the first sheet of the workbook.
            HSSFSheet excelSheet = excelWorkBook.getSheetAt(0);
   
            // Iterate through the sheet rows and cells. 
            // Store the retrieved data in an arrayList
            java.util.Iterator<Row> rows = excelSheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                java.util.Iterator<Cell> cells = row.cellIterator();
   
                ArrayList cellData = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    cellData.add(cell);
                }
   
                excelData .add(cellData.toString().substring(1, cellData.toString().length()-1));
            }
              
         /*   // Print retrieved data to the console
            for (int rowNum = 0; rowNum < excelData.size(); rowNum++) {
                  
                ArrayList list = (ArrayList) excelData.get(rowNum);
                  
                for (int cellNum = 0; cellNum < list.size(); cellNum++) {
                      
                    HSSFCell cell = (HSSFCell) list.get(cellNum);
                      
                  
                }
                
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
			


	
//Get rid of curly brackets in data of the formatted arraylist and input into data item to begin fetching product review
		
        ///from here and on write as async
        
        
        
        
        //async calls
        java.util.Date startTime = new java.util.Date();
        System.out.println("Start Work"  + startTime);
        
        //thread pools for the number of tasks to be executed simutineously
        final ExecutorService pool = Executors.newFixedThreadPool(3);
        
        //completon service to check completed tasks , avoided blocking, does not need to sit and wait around for the first to be compelted and then check the second
        final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(pool);
        
        
        
	/*for( final int i = 0; i <excelData.size();i++)	*/
        //enhanced for loop nneded
       for(final String itemid : excelData)
	
	
{
    
    	 
		//making async calls with completion service wrap 
		completionService.submit(new Callable() {
	        @Override
	        public Object call() throws Exception {
	        	
	        	
	        	
	        	//original 
	    	/*	System.out.println(excelData.get(i));*/
	        	
	        	//new 
	        	System.out.println("Parrallel execution begin to execute " +
	        			itemid + " " + "is now entering to the thread pool. Threadpool allows 3 itemids to be executed at once. Once full," +
	        					"an new item id will be only added after at least one of the item id in the pool is completed"
	        			+ " " + "Please note this program is utilized in a way such the completion of tasks is not in order for effeiency purpose"
	        					+ " as soon as one item id  has been fetched/completed among the 3, a new item id will be added immidiately." 
	        					);
	        	
	    		
	    		//get rid of the curly brackets appeared in the list
	    		//original version 
	    	/*Item anitem = new Item(excelData.get(i).toString().substring(1, excelData.get(i).toString().length()-1));*/
	    	
	    	//new version
	    	
	    	Item anitem = new Item(itemid);
	    		

	    		
	    		
	    	//Use Standard Amazon API to sign in Amazon to begin fetching

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
	    		
	    		
	    		anitem.fetchReview(ht);
	        	
	    		
	    		
	    		
	    		
	    	//might need to put out side of tasks, 	
	    		
	    		
	    		
	    		if(anitem.reviews.size()==0||anitem.reviews.isEmpty())
	    			
	    		{
	    			System.out.println("no review fetched for this sku from crawler, skip to next sku in list");
	    			
	    			count.increment();
	    			System.out.println("currently at " +count.getVal() + " " + "in the sku list and this sku has no review");
	    			return null;
	    			
	    		}
	    		
	    		
	    		System.out.println("The SKU" +" " + anitem.itemID + "has "
	    		+ " " + anitem.reviews.size() 
	    		+ "fetched reviews for specified date,begin to out put to excel.." );

	    		count.increment();
	    		
	    		System.out.println("currently at the " + " " +count.getVal()+" "+ " th sku in list, begining to sort review date and add to excel");
	    		//sort review by date as requested, implemented comparable in itmes, sort in descending order
	    		
	    		Collections.sort(anitem.reviews);
	    		
	    		
	    		
	    		//apache POI to populate arraylist to excel and out put excel files of product reviews of each ASIn
	    	
	    		
	    		
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
	    		        sheet.getRow(0).createCell(8).setCellValue("Review Header");
	    		        sheet.getRow(0).createCell(9).setCellValue("Review");
	    		        

	      
	                    
	    		        row.createCell(0).setCellValue(r.getItemID());
	    		        row.createCell(1).setCellValue(r.getReviewID());
	    		        row.createCell(2).setCellValue(r.getCustomerName());
	    		        row.createCell(3).setCellValue(r.getRating());
	    		        row.createCell(4).setCellValue(r.getFullRating());
	    		        row.createCell(5).setCellValue(r.isVerifiedPurchase());
	    		        row.createCell(6).setCellValue(r.getRealName());
	    		        row.createCell(7).setCellValue(r.getReviewDate().toString().substring(3, r.getReviewDate().toString().length()));
	    		        row.createCell(8).setCellValue(r.getTitle());
	    		        row.createCell(9).setCellValue(r.getContent());
	    		       
	    		      
	    		        
	    		    }
	    		    
	    		    
	    	
	    		    //only proceed with creating xls if there is no exception to be throw
	    		    
	    		    
	    		  
	    		    FileOutputStream fileOut = new FileOutputStream("Z:\\Staffs\\Joey\\Developer\\crawled report\\BGC\\"+anitem.itemID + ".xls");
	    		    
	    		    
	    	        
	    		    wb.write(fileOut);
	    	   
	    		    
	    	        fileOut.close();
	    	        

	    	     
	    	       System.out.println("file created");
	    	       
	    	       
	    	
	    	       System.out.println("Going to sleep to avoid being robot");
	    	   	//craw delay directive value, needs to be 1-30s,create a random number to add to time, where 5000 is maxium and 1000 is min
	    			Random rand = new Random();

	    			int  time = rand.nextInt(5000) + 1000;
	    			
	    	      
	    		  System.out.println("Im awake to grab more review");  
	    		
	
	            return null;
	    	
	        }
	    });
		

}
		
     
	
		
	for (int i = 0; i < excelData.size(); i++) {
       
          try {
        	  
        	  String result = completionService.take().get(); // find the first completed task
            
        	  
        	  System.out.println("Fetched content (empty/full)for " +excelData.get(i)+" " + "Has arrived");
          /*   if(result :anItem){
            	
            	 System.out.println("this completed task has no review, null returned by previous one");
            	 continue;
             }*/
              
          } catch (InterruptedException e) {
        	  
          } catch (ExecutionException e) {
        	  
        	  
          }
      }
    

	System.out.println("All goood things come to an end, we are now at the end of the parallel execution" +
			", all files should have been fetched, executor shutting down...");
	
	pool.shutdown();

	  java.util.Date endTime = new java.util.Date();
      System.out.println("End Work"  + endTime);
		    
	}
	
		
		
		
/*
	//merge list of genereated prodcut review into a signle file
	
	//location of list of excel files of product reviews
	 File file = new File("\\Excel2");
	 
	 FileInputStream fis = null;
	 //make it as a list
	  File[] listOfFiles = file.listFiles();
	  //make it into List<fileinput stream> formatt
	  List<FileInputStream> l =  new ArrayList<FileInputStream>();
	  
	  for (int i = 0; i < listOfFiles.length; i++) {
		  
		  l.add(fis = new FileInputStream(listOfFiles[i]));
		  
		  
	  }
	 
        //checking for sucess
	  
	  for(FileInputStream l1 : l)
		  
	  {
		  
		  System.out.println(l1);
	  }
      
	  //out put location of the combined excels of product reviews
        File f = new File("c:\\new\\resultforBGC-14-17-5-16.xls"); 

      //merge
        Mergexls.mergeExcelFiles(f, l);
	
*/
	

	
	
	}

	
	

	

	




	
	

