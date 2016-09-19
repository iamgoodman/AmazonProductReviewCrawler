import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Item {
	//item id refer to the ASIN  in this particular program, can be changed. 
	public String itemID;
	public ArrayList<Review> reviews;
	
	
	public Item(String theitemid) {
		itemID = theitemid;
		reviews = new ArrayList<Review>();
	}

	public void addReview(Review thereview) {
		reviews.add(thereview);
	}

	/**
	 * Fetch all reviews for the item from Amazon.com
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws InterruptedException 
	 */
	public void fetchReview() throws IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException {
		
		
		//this is intended for all pages
	/*	String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=" + 1;*/
		
		//this is inteded for just the latest reviews.
		
		String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/ref=cm_cr_arp_d_viewopt_srt?showViewpoints=0&sortBy=recent&pageNumber" + 1;
		
		
/*	Response c = Jsoup.connect(url).userAgent("Mozilla/17.0").execute();
		
		System.out.println(c.statusCode());
	*/
		
	/*	try {*/
			/*// Get the max number of review pages;
			org.jsoup.nodes.Document reviewpage1 = null;
			reviewpage1 = Jsoup.connect(url).timeout(10*1000).get();
			int maxpage = 1;
			Elements pagelinks = reviewpage1.select("a[href*=pageNumber=]");
			if (pagelinks.size() != 0) {
				ArrayList<Integer> pagenum = new ArrayList<Integer>();
				for (Element link : pagelinks) {
					try {
						pagenum.add(Integer.parseInt(link.text()));
					} catch (NumberFormatException nfe) {
					}
				}
				maxpage = Collections.max(pagenum);
			}
			// collect review from each of the review pages;
			for (int p = 1; p <= maxpage; p = p + 1) {
				url = "http://www.amazon.com/product-reviews/"
						+ itemID
						+ "/?sortBy=helpful&pageNumber="
						+ p;*/
			
			    
			//there is no review page initially 
				org.jsoup.nodes.Document reviewpage = null;
		
				System.out.println("trying for connection");
				
				Connection con = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
						  .timeout(20000)
						  .followRedirects(true);
				
				System.out.println("setting connection time out..");
				//need connection time out to avoid socket timeout execpeiton
            	con.timeout(5000);
    
          //while loop to allow reconnect inorder to solve socket timeout exception
            	//maxium tries of while loop
          int count = 0;
          int maxtries =3;
            	
            	
          while(true)
          {
            try{
            	Response resp = con.execute();
				
            
		
				int statuscode = resp.statusCode();
				
	
			
				if(statuscode == 200)
				{
	
					System.out.println("able to connect ");
					
					
                	
					
					
					//in the past if unable to get reconnect try it again, unstable, need to fix robot issue
					//set time out value to avoid get time out
					//set up referr to skip robot detection
					
					//waste resource
					/*reviewpage = Jsoup.connect(url).header("User-Agent",
		                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").referrer("http://www.google.com")
							.timeout(60*1000).followRedirects(true).get();*/
					
					reviewpage = resp.parse();
					
					//robot counter
					int rcount = 0;
					
                while(reviewpage.text().contains("Robot"))
                {
                	System.out.println("unable to get exception"+reviewpage.text());
            
                	System.out.println("Robot Detection, pausing connection..");
                	
                	con.timeout(5000);
                	
               
                	//craw delay directive value, needs to be 1-30s,create a random number to add to time, where 5000 is maxium and 1000 is min
        			Random rand = new Random();

        			int  time = rand.nextInt(30000) + 10000;
        			
        			//8000mm is the maximum and the 1500mm is our minimum 
        			System.out.println("Robot Detection, need to sleep for a long time, pausing program...");
        			
        			Thread.sleep(1000+time);
        			
        			  System.out.println("Im awake to grab more review"); 

        			  
        			  //change referrer to pretned not being robot
        			  //reconnect
        			  
        			  Response response= Jsoup.connect(url)
        			           .ignoreContentType(true)
        			           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
        			           .referrer("http://www.google.com")   
        			           .timeout(24000) 
        			           .followRedirects(true)
        			           .execute();
        			  
            /*   reviewpage = Jsoup.connect(url).header("User-Agent",
            		   "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36").referrer("http://www.google.ca").timeout(70*1000).followRedirects(true).get();*/
                
        			  reviewpage = response.parse();
           	
               		rcount++;
               
            	//if robot check has occured 5 times
           	if(rcount == 5)
           	{
           		
           		System.out.println("Oh no, Robot prevention overload, need to wait pause and wait for even longer.....");
           
           		con.timeout(60000);
           		
           		
           		Thread.sleep(100000);
           		
          
           		
           	 //reset counter
           	 rcount = 0;
           		
           	}
           	
           	
                }
                
                
                
                System.out.println("Passed the robot test validation, fetching ");
                
                
                //div.a refers to <div> in the review section
                
            
    			  
    			  
				if (reviewpage.select("div.a-section.review").isEmpty()) {
					
					System.out.println(itemID + " " + "has no review at all in html revie tag, no review will be fetched");
					//break out of the while loop due to no review
					break;
							
					
				} else {
					System.out.println(itemID+ " " + "review fetched, picking the deisred dates..");
					Elements reviewsHTMLs = reviewpage.select(
							"div.a-section.review");
					
					
					for (Element reviewBlock : reviewsHTMLs) {
						
						
						  Elements date = reviewBlock.select("span.review-date");
						  
	
					        String datetext = date.first().text();
					        
					        
					        String  datetext1= datetext.substring(3);
					        
					
					        
					        System.out.println("This is the current iteration for review date" + " " + " " +datetext1);
							
					    	
							if(datetext1.equalsIgnoreCase("September 3, 2016") || datetext1.equalsIgnoreCase("September 4, 2016") ||
									datetext1.equalsIgnoreCase("September 5, 2016") || datetext1.equalsIgnoreCase("September 6, 2016") 
									|| datetext1.equalsIgnoreCase("September 7, 2016")
									|| datetext1.equalsIgnoreCase("September 8, 2016")
									|| datetext1.equalsIgnoreCase("September 9, 2016")
									|| datetext1.equalsIgnoreCase("September 10, 2016")
									|| datetext1.equalsIgnoreCase("September 11, 2016")
									|| datetext1.equalsIgnoreCase("September 12, 2016")
									|| datetext1.equalsIgnoreCase("September 13, 2016")
									|| datetext1.equalsIgnoreCase("September 14, 2016")
									|| datetext1.equalsIgnoreCase("September 15, 2016")
									
								
								)
						
						{
							
								System.out.println("Desired date matched"+" " + " Currently  inside of a specific date"+" "+datetext1+" ,"+" "+"I am about to download review for this date");
							
							
                        Review theReview = cleanReviewBlock(reviewBlock);
                        
                        
						this.addReview(theReview);
						
						//sucessfully added to item break out of the while loop
						break;
						
						}
					
						
					}
				}
				
				
			//craw delay directive value, needs to be 1-30s,create a random number to add to time, where 5000 is maxium and 1000 is min
    			Random rand = new Random();

    			int  time = rand.nextInt(5000) + 1000;
    			
    			
    			
    			System.out.println("Going to sleep to avoid being robot");
    			
    			Thread.sleep(1000+time);
    			
    			 System.out.println("Im awake to grab more review");  
    			 
    			//break out of the loop for the single item
    			 break;

				}
				
				else{
					
					//non 202 successful connection, might not be necessary, just incase
					System.out.println("Not sucessfully connected, getting non 202 resp, it is " + " "
					+resp.statusCode()+ " " + "Move on to next sku in list, admin please log this sku");
					
					return;
				}
				
            }
            //catch non 202 connections 
            
            catch(HttpStatusException e){
            	
            	System.out.println("Unable to establish connection, sku might no longer exists, exception code" + e.toString()+ " "
            			+ "will move on to next sku in list" + " "
            			+ "Admin please log this sku");
				
			return;
	
            }
		
            //catch socket timeout exception
            catch(SocketTimeoutException e){
            	
            	
            	System.out.println("socket excpetion" + " " + e);
            	
            	//exception is handled by retry, 
            	System.out.println("establishing reconnection....");
            	  //allow maxium connection of 3 times
                if (++count == maxtries) throw e;
            	
            }
				
		}
          
	}

	
	

	/**
	 * cleans the html block that contains a review
	 * 
	 * @param reviewBlock
	 *            a html review block (Jsoup Element)
	 * @return
	 * @throws ParseException
	 */
	public Review cleanReviewBlock(Element reviewBlock) throws ParseException {
		
		String theitemID = this.itemID;
		String reviewID = "";
		String customerName = "";
		String customerID = "";
		String title = "";
		int rating = 0;
		int fullRating = 5;
		int helpfulVotes = 0;
		int totalVotes = 0;
		boolean verifiedPurchase = false;
		String realName = "N/A"; 
		String comments;

		String content = "";

		// review id
		reviewID = reviewBlock.id();
//        System.out.println(reviewID);
        // customer name and id
		Elements customerIDs = reviewBlock.getElementsByAttributeValueContaining(
				"href", "/gp/pdp/profile/");
		if (customerIDs.size() > 0) {
			Element customer = customerIDs.first();
			String customerhref = customer.attr("href");
			String patternString = "(/gp/pdp/profile/)(.+)";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(customerhref);
			matcher.find();
			// cutomer id;
			customerID = matcher.group(2);
			// customer name;
			customerName = customer.text();
        }
		
        	
     

		// rating
		Element star = reviewBlock.select("i.a-icon-star").first();
		String starinfo = star.text();
		rating = Integer.parseInt(starinfo.substring(0, 1));

	/*	// usefulness voting
		Elements votes = reviewBlock.select("span.review-votes");
		if (votes.size() > 0) {
			String votingtext = votes.first().text();
			Pattern pattern2 = Pattern.compile("(\\S+)( of )(\\S+)");
			Matcher matcher2 = pattern2.matcher(votingtext);
			matcher2.find();
			// customer id;
			helpfulVotes = Integer.parseInt(matcher2.group(1).replaceAll(",", ""));
			totalVotes = Integer.parseInt(matcher2.group(3).replaceAll(",", ""));
		}*/

		// verified purchase
		Elements verified = reviewBlock.select("span.a-size-mini:contains(Verified Purchase)");
		
		if (verified.size() > 0){
            verifiedPurchase = true;
        }


		// review date
		Elements date = reviewBlock.select("span.review-date");
        String datetext = date.first().text();
        datetext = datetext.substring(3); // remove "On "
        Date reviewDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
				.parse(datetext);

		// review content
		Element contentDoc = reviewBlock.select("span.review-text").first();
		content = contentDoc.text();
		
		
		/*//review comment
		System.out.println("trying for comments");
		//does not work
		Elements comment = reviewBlock.select("div.a-row a-spacing-mini review-comments-header");
		System.out.println("Im here in the comment section");
		System.out.println("what i get is " + comment.size());
		if(comment.hasText()){
		comments = comment.text();
		System.out.println("Im here and comment is "+comments);
		}
		else
		{
			comments ="";
			
		}*/
		
		//temporarily setting comments to be null, will be reset, when implementing commnets section 
		String comments1 = "";
				
		Review thereview = new Review(theitemID, reviewID, customerName,
				customerID, title, rating, fullRating, helpfulVotes,
				totalVotes, verifiedPurchase, realName, reviewDate, content,comments1);
		
		
		
		
		
        return thereview;
	}
        
     

	
	
	

}
