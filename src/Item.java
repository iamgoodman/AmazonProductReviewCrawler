import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
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

/**
 * Process Reviews class
 * @author Feiyi(Joey) Xiang 
 *         
 */


public class Item {
	//item id refer to the ASIN  in this particular program, can be changed. 
	public String itemID;
	public ArrayList<Review> reviews;
	//list of user agent for user agent spoofing
	public ArrayList<String> ua = new ArrayList<String>(
		Arrays.asList(
				
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1 ",
				"Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25 ",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2226.0 Safari/537.36 ",
				"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.3a) Gecko/20021207 Phoenix/0.5 ",
				"Mozilla/5.0 (Windows NT 5.2; RW; rv:7.0a1) Gecko/20091211 SeaMonkey/9.23a1pre",
				"Surf/0.4.1 (X11; U; Unix; en-US) AppleWebKit/531.2+ Compatible (Safari; MSIE 9.0)",
				"Mozilla/5.0 (compatible; U; ABrowse 0.6; Syllable) AppleWebKit/420+ (KHTML, like Gecko)",
				"Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser 1.98.744; .NET CLR 3.5.30729) ",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
				"Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727) ",
				"Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; HbTools 4.7.0)",
				"Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.27; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)",
				"Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.19; Windows NT 5.1; Trident/4.0; GTB7.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729) ",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30 ChromePlus/1.6.3.1",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.224 Safari/534.10 ChromePlus/1.5.2.0",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.6b) Gecko/20031212 Firebird/0.7+ ",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; x64; fr; rv:1.9.2.13) Gecko/20101203 Firebird/3.6.13",
				"Mozilla/5.0 (compatible, MSIE 11, Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko",
				"Mozilla/1.22 (compatible; MSIE 10.0; Windows 3.1) ",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; Media Center PC 6.0; InfoPath.3; MS-RTC LM 8; Zune 4.7) ",
				"Lynx/2.8.8dev.3 libwww-FM/2.14 SSL-MM/1.4.1 ",
				"Lynx/2.8.7dev.4 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.8d ",
				"Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16",
				"Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; MyIE2; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0) ",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.3 Safari/534.53.10",
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.1 (KHTML, like Gecko) Maxthon/3.0.8.2 Safari/533.1",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/532.4 (KHTML, like Gecko) Maxthon/3.0.6.27 Safari/532.4",
				"Mozilla/5.0 (X11; U; Linux i686; pt-BR) AppleWebKit/533.3 (KHTML, like Gecko) Navscape/Pre-0.2 Safari/533.3",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; pt-BR) AppleWebKit/534.12 (KHTML, like Gecko) Navscape/Pre-0.1 Safari/534.12"
				

				)
		
			
			
			);
	
	
	
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
	public void fetchReview(Hashtable<String, Integer>ht) throws IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException {
		
		
		//this is intended for all pages
	/*	String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=" + 1;*/
		
		//this is inteded for just the latest reviews.
		
		String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/ref=cm_cr_arp_d_viewopt_srt?showViewpoints=0&sortBy=recent&pageNumber" + 1;
		
        //key for hash table, in this case it is the proxy IP
		String keyht;
		//Value for hash table, in this case it is proxy IP port.
		Integer valueht;
		//string for buffer reader
		String str;

	     //iterator over hash table
		Iterator<Entry<String, Integer>> entries = ht.entrySet().iterator();
		
		 //iterator over hash table to keep a copy to keep track of the beginning, in case iterator has iterated all of the sets.
		Iterator<Entry<String, Integer>> entries1 = ht.entrySet().iterator();
		
		
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
				
				Connection con = Jsoup.connect(url).userAgent( "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
						  .timeout(20000)
						  .followRedirects(true);
				
			
				System.out.println("setting connection time out..");
				//need connection time out to avoid socket timeout execpeiton
            	con.timeout(5000);
    
          //while loop to allow reconnect inorder to solve socket timeout exception
            	//maxium tries of while loop
          int count = 0;
          int maxtries =10;
            	
            	
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
					
					//robot counter,also counter for list of useragent headers
					int rcount = 0;
				
					
					
                while(reviewpage.text().contains("Robot"))
                {
                	//if there is a proxy, get it and move to the next
                	if(entries.hasNext()){
				
					Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)entries.next();
					
					 keyht = (String)entry.getKey();
					 valueht = (Integer)entry.getValue();
                	
                	
               }
               //if no proxy in the list, reuse the first one from begining 
               else
            	   
               {
            	   Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)entries1.next();
					
            		 keyht = (String)entry.getKey();
                	 valueht = (Integer)entry.getValue();
            	   
               }
                	
                   
					
					
					
                	System.out.println("unable to get exception"+reviewpage.text());
            
                	System.out.println("Robot Detection, pausing connection..");
                	
                	con.timeout(5000);
                	
               
                	//craw delay directive value, needs to be 1-30s,create a random number to add to time, where 5000 is maxium and 1000 is min
        			Random rand = new Random();

        			int  time = rand.nextInt(30000) + 10000;
        			
        			//8000mm is the maximum and the 1500mm is our minimum 
        			System.out.println("Robot Detection, need to sleep for a long time, pausing program...");
        			
        			Thread.sleep(1000+time);
        			
        			System.out.println("IM awake to grab more reviews");
        			
        			
   ////////////////////////////setting up proxy///     
        			
        	
         			System.out.println("Setting up Proxy, mimic user, closing connection, trying to reconnect....");
                    
         			
                     
        			
        			//using http for proxy connection
        			  
        			final URL website = new URL(url);
        			//setting up proxy using address and key from 
        			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(keyht, valueht)); // set proxy server and port
        			HttpURLConnection httpUrlConnetion = (HttpURLConnection) website.openConnection(proxy);
        			httpUrlConnetion.setRequestProperty("User-Agent", ua.get(rcount));
        			httpUrlConnetion.setRequestProperty("referrer","http://www.google.com");
        			httpUrlConnetion.connect();
        			
        			// -- Download the website into a buffer
        			BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnetion.getInputStream()));
        			StringBuilder buffer = new StringBuilder();
        		

        			while( (str = br.readLine()) != null )
        			{
        			    buffer.append(str);
        			}

        			// -- Parse the buffer with Jsoup
        			reviewpage = Jsoup.parse(buffer.toString());
 /////////////////////////////////////////////////////////////////////////
        			
        			//original reconnect
         			
        		/*	  //change referrer to pretned not being robot
        			  //reconnect
        			  //change user agent with rcount each time it reconnects
        			  Response response= Jsoup.connect(url)
        					
        			           .ignoreContentType(true)
        			           .userAgent(ua.get(rcount))  
        			           .referrer("http://www.google.com")   
        			           .timeout(24000) 
        			           .followRedirects(true)
        			           .execute();
        			  
               reviewpage = Jsoup.connect(url).header("User-Agent",
            		   "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36").referrer("http://www.google.ca").timeout(70*1000).followRedirects(true).get();
                
        			  reviewpage = response.parse();*/
///////////////////////////////////////////////////////////////////           	
               		rcount++;
               
            	//if robot check has occured 5 times
           	if(rcount == 29)
           	{
           		
           		System.out.println("Oh no, Robot prevention overload, need to wait pause and wait for even longer.....");
           
           		con.timeout(50000);
           		
           		
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
							
					        
					        for(int i =0;i<=31;i++){
					    	
							if(datetext1.equalsIgnoreCase("October"+ " " +i+", "+"2016")
								
				
								)
						
						{
							
								System.out.println("Desired date matched"+" " + " Currently  inside of a specific date"+" "+datetext1+" ,"+" "+"I am about to download review for this date");
							
							
                        Review theReview = cleanReviewBlock(reviewBlock);
                        
                        
						this.addReview(theReview);
					
					
					
						
						}
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
					System.out.println("Not sucessfully connected,  it is " + " "
					+resp.statusCode()+ " " + "Move on to next sku in list, admin please log this sku");
					
			
					
					
					return ;
				}
				
            }
            //catch response exceptions connections 
            
            catch(HttpStatusException e){
            	
            	//if execpetion is 503 meaning amazon prevents frequent connection, pause and retry
	            if(e.getStatusCode() == 503 || e.getStatusCode()  == 500)
	            {
	            	//retry after catch
	            	System.out.println("Status 503, Amazon has throttled the response due to too many requests being send, need to slow down.");
	            	
	            	Thread.sleep(5000);
	            	
	            	System.out.println("slept for 5 seconds, ready to set connection again");
	            	
				con = Jsoup.connect(url).userAgent( "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
							  .timeout(20000)
							  .followRedirects(true);
			
	            	if (++count == maxtries) throw e;
	            	
	            }
			
	            //else fucntion does not exist terminate
	            else
	            {
            	System.out.println("Unable to establish connection, sku might no longer exists, exception code" + e.toString()+ " "
            			+ "will move on to next sku in list" + " "
            			+ "Admin please log this sku");
				
                        
            	
				
            		return;
			
	            }
	
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
