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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Item {
	
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
	 */
	public void fetchReview() throws IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException {
		
		
		//this is intended for all pages
	/*	String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/?showViewpoints=0&sortBy=byRankDescending&pageNumber=" + 1;*/
		
		//this is inteded for just the latest reviews.
		
		String url = "http://www.amazon.com/product-reviews/" + itemID
				+ "/ref=cm_cr_arp_d_viewopt_srt?showViewpoints=0&sortBy=recent&pageNumber" + 1;
		
		
		
		
		
		try {
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
			
			
			
				org.jsoup.nodes.Document reviewpage = null;
				
                reviewpage = Jsoup.connect(url).timeout(10*1000).get();
                
				if (reviewpage.select("div.a-section.review").isEmpty()) {
					
					System.out.println(itemID + " " + "no reivew");
					
				} else {
					Elements reviewsHTMLs = reviewpage.select(
							"div.a-section.review");
					
					
					for (Element reviewBlock : reviewsHTMLs) {
						
						
						  Elements date = reviewBlock.select("span.review-date");
						  
					        String datetext = date.first().text();
					        
					        datetext= datetext.substring(3);
					        		
					        		
					        System.out.println(datetext);
					        
					   
					    	
						
						if(datetext.equalsIgnoreCase("July 8, 2016") || datetext.equalsIgnoreCase("July 9, 2016") ||
								datetext.equalsIgnoreCase("July 10, 2016") || datetext.equalsIgnoreCase("July 11, 2016") 
								|| datetext.equalsIgnoreCase("July 11, 2016")
								|| datetext.equalsIgnoreCase("July 12, 2016")
								|| datetext.equalsIgnoreCase("July 13, 2016")
								|| datetext.equalsIgnoreCase("July 14, 2016")
								
								
								
								)
						
						{
							
							System.out.println("inside");
							
							
                        Review theReview = cleanReviewBlock(reviewBlock);
                        
                        
						this.addReview(theReview);
						
						
						}
					
						
					}
				}

			}

		/*}*/ 
		
		catch (Exception e) {
			System.out.println(itemID + " " + "Exception" + " " + e.getClass());
			
			
			try{
				
				
			//when HTTPStatus Exception is thrown, need to resign to keep fetching the data.
			
			// Input to Sign;
			SignedRequestsHelper helper = new SignedRequestsHelper();
			
			Map<String, String> variablemap = new HashMap<String, String>();
			//*****ADD YOUR AssociateTag HERE*****
			variablemap.put("AssociateTag", "");
			variablemap.put("Operation", "ItemLookup");
			variablemap.put("Service", "AWSECommerceService");
			variablemap.put("ItemId",itemID);
			variablemap.put("ResponseGroup", "Large");

			// Sign and get the REST url;
		
			helper.sign(variablemap);
			
			
			
			// Get the max number of review pages;
						/*org.jsoup.nodes.Document reviewpage1 = null;
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
							org.jsoup.nodes.Document reviewpage = null;
			                reviewpage = Jsoup.connect(url).timeout(10*1000).get();
							if (reviewpage.select("div.a-section.review").isEmpty()) {
								System.out.println(itemID + " " + "no reivew");
							} else {
								Elements reviewsHTMLs = reviewpage.select(
										"div.a-section.review");
								
								for (Element reviewBlock : reviewsHTMLs) {
									
									
									 Elements date = reviewBlock.select("span.review-date");
								        String datetext = date.first().text();
								        
								        
								        
								        datetext= datetext.substring(3);
								        		
								        		
								        System.out.println(datetext);
								        
								    
								        
								        
									
										
										if(datetext.equalsIgnoreCase("July 8, 2016") || datetext.equalsIgnoreCase("July 9, 2016") ||
												datetext.equalsIgnoreCase("July 10, 2016") || datetext.equalsIgnoreCase("July 11, 2016") 
												|| datetext.equalsIgnoreCase("July 11, 2016")
												|| datetext.equalsIgnoreCase("July 12, 2016")
												|| datetext.equalsIgnoreCase("July 13, 2016")
												|| datetext.equalsIgnoreCase("July 14, 2016")
												
												
												
												){
									
										System.out.println("inside");
										
			                        Review theReview = cleanReviewBlock(reviewBlock);
			                        
									this.addReview(theReview);
									
									}
									
									
									
									
									
									
									
									
									
									
									
								}
							}

			
			
						
			
			
			
		/*}*/
		}catch(Exception ex) {
			
			//handle exception 
			
			try{
			
			
				//when HTTPStatus Exception is thrown, need to resign to keep fetching the data.
				
				// Input to Sign;
				SignedRequestsHelper helper = new SignedRequestsHelper();
				
				Map<String, String> variablemap = new HashMap<String, String>();
				//*****ADD YOUR AssociateTag HERE*****
				variablemap.put("AssociateTag", "");
				variablemap.put("Operation", "ItemLookup");
				variablemap.put("Service", "AWSECommerceService");
				variablemap.put("ItemId",itemID);
				variablemap.put("ResponseGroup", "Large");

				// Sign and get the REST url;
			
				helper.sign(variablemap);
				
				
				
				// Get the max number of review pages;
						/*	org.jsoup.nodes.Document reviewpage1 = null;
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
								org.jsoup.nodes.Document reviewpage = null;
				                reviewpage = Jsoup.connect(url).timeout(10*1000).get();
				                
								if (reviewpage.select("div.a-section.review").isEmpty()) {
									System.out.println(itemID + " " + "no reivew");
								} 
								
								else {
									Elements reviewsHTMLs = reviewpage.select(
											"div.a-section.review");
									for (Element reviewBlock : reviewsHTMLs) {
										
										
										
										
										
										 Elements date = reviewBlock.select("span.review-date");
									        String datetext = date.first().text();
									        
									        
									        datetext= datetext.substring(3);
									        
									        
									        System.out.println(datetext);
										
											
											if(datetext.equalsIgnoreCase("July 8, 2016") || datetext.equalsIgnoreCase("July 9, 2016") ||
													datetext.equalsIgnoreCase("July 10, 2016") || datetext.equalsIgnoreCase("July 11, 2016") 
													|| datetext.equalsIgnoreCase("July 11, 2016")
													|| datetext.equalsIgnoreCase("July 12, 2016")
													|| datetext.equalsIgnoreCase("July 13, 2016")
													|| datetext.equalsIgnoreCase("July 14, 2016")
													
													
													
													 ){
											
											
											System.out.println("inside");
										
				                        Review theReview = cleanReviewBlock(reviewBlock);
				                        
										this.addReview(theReview);
										
										}
										
			
									}
								}

				
				
							
						/*	}*/
			
			
			
			
			
		    
		}catch (Exception exx) {
		    //Handle exception
		}
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
		
		Review thereview = new Review(theitemID, reviewID, customerName,
				customerID, title, rating, fullRating, helpfulVotes,
				totalVotes, verifiedPurchase, realName, reviewDate, content);
		
		
        return thereview;
	}
        
     

	
	
	

}
