import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('https://eservices.cipc.co.za/NameSearch.aspx')

WebUI.setText(findTestObject('Page_Companies and Intellectual Pro/input_ctl00cntMaintxtName'), CompanyName)

WebUI.click(findTestObject('Page_Companies and Intellectual Pro/input_ctl00cntMainbtnSearch'))

saveResults(CompanyName)

// WebUI.click(findTestObject('Page_Companies and Intellectual Pro/td_We did not find any enterpr'))

static void saveResults(def companyName){
	def resultTable = extractResultsFromWebPage()
	def file = new File("../Batch_Results/Name_Search/"+companyName+".csv")
	def writeResult = "Enterprise Name, Enterprise/Tracking Number, Status"
	
	// if we have results in the table
	if(HasResults(resultTable)){
		// break up by line in table
		resultTable.eachLine{ line, count ->
			
			// skip header, we already added it
			if (count > 0) {
				// for each 'cell' in a line assemble with ',' in correct location to make well formed csv
				line.splitEachLine(" "){ cells ->
					for(def x = 0; x < cells.count; x++){
						//writeResult = writeResult.concat(cells[x])
						if(x >= cells.count - 3){
							writeResult = writeResult.concat(", ")
						}else{
							writeResult = writeResult.contact(" ")
						}
					}
				}
			}
		}
	}else{
		file.write "No results found"
		return
	}
	
	file.write writeResult.toString()
	
}

static String extractResultsFromWebPage(){
	return WebUI.getText(findTestObject('Page_Companies and Intellectual Pro/results_table'))
}

static boolean HasResults(def resultTable){
	return !resultTable.contains("We did not find any enterprises matching your search criteria.")
}
