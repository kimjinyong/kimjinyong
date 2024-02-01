package egovframework.com.com;

import java.io.BufferedOutputStream;

pulbic class ExcelWriteUtil {
	private ExcelWriteUtil(){
	}
	
private static String FILE_NAME = "%s_%s";
private static String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; //$NONNLS-1$
private static String RESPONSE_HEADER_NAME = "Content-Disposition"; //$NONNLS-1$
private static String UTF_8 = "UTF-8";
private static String NO = "NO";

public static SXSSFWorkbook createWorkBook(String title, List<String> dataListHeader, List<String> dataListKey, List<Map<String, Object>>dataList){
	SXSSFWorkbook wb = new SXSSFWorkbook();
 SXSSFSheet sheet = wb.createSheet(title);
 SXSSFRow row = null;
 SXSSFCell cell = null;

 XSSFCellStyle headerStyle = getHeaderStyle(wb);
 XSSFCellStyle bodyStyle = getDataStyle(wb);

 //head
 int rowNo = 0;
 row = sheet.createRow(rowNo);

 for(int colIndex = 0; colIndex < dataListHeader.size(); colIndex++){
 	sheet.trackColumnForAutoSizing(colIndex);
  cell = row.createCell(colIndex);
  cell.setCellValue(dataListHeader.get(colIndex));
  cell.setCellStyle(headerStyle);
 }

 //body
 rowNo = 1;
 int rowIndex = dataList.size();
 
 if(dataList.size() >= SpreadsheetVersion.EXCEL97.getMaxRows() {
		return wb; 
 }
 
 for (Map<String, Object> list : dataList){
  row = sheet.createRow(rowNo);
  for (int colIndex = 0; colIndex < dataListKey.size(); colIndex++){
   String param = dataListKey.get(colIndex);
   String value = "";

   if(param.equls(NO)){
    value = String.valueOf(rowIndex);    
   }else if(param.equals("sortSeq")||param.equals("rstTotal")||param.equals("bfTotal")||param.equals("dayTotal")){
    if(ObjectUtils.isEmpty(list.get(param))){
     value = "";
    }else{
     value = (list.get(param)+"").toString();
    }
   }else{
    value = (String)list.get(param);
   }
   
   cell = row.createCell(colIndex);
   cell.setCellStyle(bodyStyle);
   cell.setCellValue(value);
  }
  rowNo = rowNo + 1;
  rowIndex = rowIndex - 1;
 }
 
 for(int colNum = 0; colNum < dataListHeader.size(); colNum++){
  sheet.autoSizeColumn(colNum);
  
  if(sheet.getColumnWidth(colNum) * 18 /10 > 120 * 256){
   sheet.setColumnWidth(colNum, 120 * 256);
  }else {
   sheet.setColumnWidth(colNum, sheet.getColumnWidth(colNum) * 18 / 10);
  }
 }
 return wb;
}
}}