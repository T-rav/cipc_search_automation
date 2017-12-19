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

WebUI.delay(20) // wait a bit before we close, don't want to hammer the server in test case mode ;)

WebUI.closeBrowser()

// -- start methods --
static void saveResults(def companyName){
	def newline = '\n';
	def resultTable = extractResultsFromWebPage()
	def file = new File("../Batch_Results/Name_Search/"+companyName+".csv")
	def writeResult = "Enterprise Name,Enterprise/Tracking Number,Status" + newline
		
	if(HasResults(resultTable)){
		resultTable.eachLine{ line, count ->
			if (IsTableRow(count)) {
				def tableRowAsCsv = FormatWebTableRowAsCsv(line);
				writeResult = tableRowAsCsv.concat(newline);
			}
		}
	}else{
		file.write "No results found"
		return
	}
	
	// remove trailing new line
	file.write writeResult.trim() 
	
}

static boolean IsHeaderRow(def count){
	return count > 0
}

static String ExtractResultsFromWebPage(){
	return WebUI.getText(findTestObject('Page_Companies and Intellectual Pro/results_table'))
}

static boolean HasResults(def resultTable){
	return !resultTable.contains("We did not find any enterprises matching your search criteria.")
}

static String FormatWebTableRowAsCsv(def line){
	def cells = line.split(' ')
	def totalCells = cells.length
	def secondFromLastCell = totalCells - 3
	def lastCell = totalCells - 2
	def result = ""
	
	for(def cellPosition = 0; cellPosition < totalCells; cellPosition++){
		result = result.concat(cells[cellPosition])
		if(cellPosition == secondFromLastCell || cellPosition == lastCell){
			result = result.concat(",")
		}else{
			result = result.concat(" ")
		}
	}
	
	return writeResult
}
