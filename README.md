# cipc_search_automation
A set of Katalon scripts to automate the lookup of companies via the cipc.co.za website  
Get Katalon here http://katalon.com/

Only does 'Enterprise Name Search' currently

**To Use**
1) Add all the companies you wish to look up into Batch_Data\NameSearch.xlsx
2) Run the Test Suite *Batch_Name_Search*  
&nbsp;&nbsp;&nbsp;>It will cycle through all the data in NameSearch.xlsx  
&nbsp;&nbsp;&nbsp;**>Be sure to use Chrome (Headless) as the browser so it runs in the background while you work!**
3) Look in Batch_Results\Name_Search for all the results.  
&nbsp;&nbsp;&nbsp;>The result is a csv file per name and will contain the *Entperise Name, Enterprise Number and Status* for every search result.   
&nbsp;&nbsp;&nbsp;>https://github.com/T-rav/cipc_search_automation/blob/master/Batch_Results/Name_Search/zapper.csv  
&nbsp;&nbsp;&nbsp;>If a there are no results the file will contain 'No results found'

**Please Note**  
There is a 20 second delay between lookups, this is to avoid hammering the server.  
It should do about 3,000 lookups per 24 hours at this rate.
