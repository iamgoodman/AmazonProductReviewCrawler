# AmazonProductReviewCrawler

##This is designed to retrieve product feedback of an item listed on amazon
##This program will import a list of skus on excel file and generate excel files for reviews on each sku.
##This program sends request asynchronous with a thread pool in total of 3 skus. The program does not have to wait for the response for particular request, it will receive the first responded request and then handle it, then add the next sku to the thread pool
##Each thread will pause for a short period to mimic user actions
##When the server has detcted the program as a robot, the program will close the connection and then reconnect with proxy and changed header.
##This program also counts the current review being fetched, and  can log unusual behaviors(empty sku)
##This program produces detailed log on its own.
