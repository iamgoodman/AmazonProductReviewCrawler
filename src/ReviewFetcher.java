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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
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
	

	
		
		
		
		
	          
//To read input ASINs as excel formatt and parse into java, stored as arrayList
	
		 // Location of the source file
        String sourceFilePath = "c:\\bgc.xls";
          
        FileInputStream fileInputStream = null;
          
        // Array List to store the excel sheet data
        ArrayList excelData = new ArrayList();
          
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
   
                excelData .add(cellData);
            }
              
            // Print retrieved data to the console
            for (int rowNum = 0; rowNum < excelData.size(); rowNum++) {
                  
                ArrayList list = (ArrayList) excelData.get(rowNum);
                  
                for (int cellNum = 0; cellNum < list.size(); cellNum++) {
                      
                    HSSFCell cell = (HSSFCell) list.get(cellNum);
                      
                  
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
			

	
	
//Get rid of curly brackets in data of the formatted arraylist and input into data item to begin fetching product review
		
	for(int i = 0; i <excelData.size();i++)	
	
	
{
		
	
		//get rid of the curly brackets appeared in the list
		
	Item anitem = new Item(excelData.get(i).toString().substring(1, excelData.get(i).toString().length()-1));
		

		
		
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
		
		
	
		
		
		
		
		anitem.fetchReview();
		

		
		System.out.println(anitem.reviews.size());
		
//if server denied the reqeust, fetch again, or in case of empty string
		
		if(anitem.reviews.size()==0){
		
		anitem.fetchReview();
		
	}
    
	
		//if empty after two tries, skip to next iteration
		
		if(anitem.reviews.isEmpty())
			
		{
		
			continue;
			
		}

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
		        sheet.getRow(0).createCell(8).setCellValue("Comment Header");
		        sheet.getRow(0).createCell(9).setCellValue("Comment");
		    

  
                
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
		    
		    
		  
		    FileOutputStream fileOut = new FileOutputStream(anitem.itemID + ".xls");
		    
		    
	        
		    wb.write(fileOut);
	        
		    
		    
	        fileOut.close();
	        

	     
	       System.out.println("file created");
	
		    
	       
		    
    
		    
	}
	

/*	//merge list of genereated prodcut review into a signle file
	
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
        File f = new File("c:\\new\\resultforBGC.xls"); 

      //merge
        Mergexls.mergeExcelFiles(f, l);
	*/
	

	
/*	//attempting to remove duplicate rows of geneated excel. Bugged
	
	File file2 = new File("\\row\\resultforBGC.xls");
	
	  HSSFWorkbook workBook = null;  
	  POIFSFileSystem fs = null;  
	  HSSFSheet sheet = null;  
	  try {  
	   fs = new POIFSFileSystem(new FileInputStream(file2));  
	   workBook = new HSSFWorkbook(fs);  
	   sheet = workBook.getSheetAt(0);  
	   int rows = sheet.getPhysicalNumberOfRows();  
	   System.out.println(rows);  
	   Set s=new HashSet();  
	   String str="";  
	   for (int i = 1; i < rows; i++) {  
	    str="";  
	    HSSFRow row = sheet.getRow((short) i);
	    System.out.println(row);
	    int columns = row.getPhysicalNumberOfCells();  
	    for (int j = 0; j < columns; ++j) {  
	     HSSFCell cell0 = row.getCell((short) j);  
	     int type=cell0.getCellType();  
	     if(type==0){  
	      double intValue= cell0.getNumericCellValue();  
	      str=str+String.valueOf(intValue)+",";  
	      }else if(type==1){  
	      String stringValue=cell0.getStringCellValue();  
	      str=str+stringValue+",";  
	     }  
	       
	    }  
	    str=str  
	    .replace(str.charAt(str  
	      .lastIndexOf(",")), ' ');  
	    s.add(str.trim());  
	   }  
	   StringTokenizer st=null;  
	   String result="";  
	   Iterator iter=s.iterator();  
	     
	   //Create a new workbook for the output excel  
	         HSSFWorkbook workBookOut = new HSSFWorkbook();  
	           
	         //Create a new Sheet in the output excel workbook  
	         HSSFSheet sheetOut = workBookOut.createSheet("Remove Duplicates");  
	         HSSFRow[] row = new HSSFRow[s.size()];  
	         int rowCount=0;  
	         int cellCount=0;  
	   while(iter.hasNext()){  
	    cellCount=0;  
	    row[rowCount] = sheetOut.createRow(rowCount);  
	    result=iter.next().toString();  
	    System.out.println(result);  
	    st=new StringTokenizer(result," ");  
	    HSSFCell[] cell= new HSSFCell[st.countTokens()];  
	    while(st.hasMoreTokens()){  
	     cell[cellCount]=row[rowCount].createCell((short)cellCount);  
	     cell[cellCount].setCellValue(st.nextToken());  
	     ++cellCount;  
	    }  
	    ++rowCount;  
	   }  
	   FileOutputStream fileOut = new FileOutputStream("c:\\new\\output.xls");  
	            workBookOut.write(fileOut);  
	            fileOut.close();  
	  } catch (IOException ioe) {  
	   ioe.printStackTrace();  
	  } 
	
	
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}

	
	
	}
	

	




	
	

