package com.oriental.manage.core.excelUtils;

import com.oriental.manage.core.utils.FormatUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * @param sheet sheet
	 * @param values 结果集
	 * @param copyRow 复制样式的行
	 * @param startRow 结果集开始插入行
	 * @param margeCells 需要合并的单元格
	 * @param totalValue  需要计算总值的列
	 */
	public static void insertRows(HSSFSheet sheet, List<String[]> values,
			int copyRow, int startRow, Map<Integer, Integer> margeCells,Map<Integer, Double> totalValue) {
		HSSFRow targetRow = sheet.getRow(copyRow);  
		// 1传入的list大小确定添加的行数
		if (null != values && values.size() > 0) {
			int n = values.size();
			// 插入行
			if(n > 1){
				sheet.shiftRows(startRow, sheet.getLastRowNum(), n - 1, true, false);
			}
			// 插入值ֵ
			for (int i = 0; i < values.size(); i++) {
				
				String[] field = values.get(i);

				if (null != field && field.length > 0) {
					
                    HSSFCell sourceCell=null;
                    HSSFCell targetCell=null;
                    // 开始的时候有一处空行
					HSSFRow row = sheet.createRow(startRow + i - 1);
					 for (int m = 0; m < targetRow.getPhysicalNumberOfCells(); m++) {   
			                sourceCell = row.createCell(m);   
			                targetCell = targetRow.getCell(m);   
			                if(targetCell==null)
			                	targetCell=targetRow.createCell(m);
			                sourceCell.setCellStyle(targetCell.getCellStyle());   
			                sourceCell.setCellType(targetCell.getCellType());   
			            }   
					for (int j = 0; j < field.length; j++) {
						if (null != field[j]) {
							//判断值是否为空
							String str=field[j];
                            HSSFCell cell=row.getCell(j);
							if(cell==null){
								cell=row.createCell(j);
                            }
                             HSSFRichTextString string = new HSSFRichTextString(str);
                             cell.setCellValue(string);

						}
					}
					// 合并单元格
					if (null != margeCells) {
						for (int key : margeCells.keySet()) {
							int value = margeCells.get(key);	
							CellRangeAddress cellRangeAddress = new CellRangeAddress(
									startRow + i-1, startRow +i- 1, key, value);
							sheet.addMergedRegion(cellRangeAddress);
						}
					}

				}
			}
			
			//设置总值
			if(null!=totalValue){
				int totalRowNum=startRow+values.size()-1;
				HSSFRow totalRow = sheet.getRow(totalRowNum);
				for(int t:totalValue.keySet()){
					String totalString= FormatUtil.tranferObjectToDoubleString(totalValue.get(t));
					totalRow.getCell(t).setCellValue(new HSSFRichTextString(totalString));
				}
			}
			

		}
	}
	/**
	 * 和insertRows 方法区别 totalValue 不再是自动换成double 如笔数等 是非double格式,以传入为准
	 * @param sheet
	 * @param values 结果集
	 * @param copyRow 复制样式的行
	 * @param startRow 结果集开始插入行
	 * @param margeCells 需要合并的单元格
	 * @param totalValue  需要计算总值的列
	 */
	public static void insertRows2(HSSFSheet sheet, List<String[]> values,
			int copyRow, int startRow, Map<Integer, Integer> margeCells,Map<Integer, Object> totalValue) {
		HSSFRow targetRow = sheet.getRow(copyRow);  
		// 1传入的list大小确定添加的行数
		if (null != values && values.size() > 0) {
			int n = values.size();
			// 插入行
			if(n > 1){
				sheet.shiftRows(startRow, sheet.getLastRowNum(), n - 1, true, false);
			}
			// 插入值ֵ
			for (int i = 0; i < values.size(); i++) {
				
				String[] field = values.get(i);
				
				if (null != field && field.length > 0) {
					
					HSSFCell sourceCell=null;
					HSSFCell targetCell=null;
					// 开始的时候有一处空行
					HSSFRow row = sheet.createRow(startRow + i - 1);
					for (int m = 0; m < targetRow.getPhysicalNumberOfCells(); m++) {   
						sourceCell = row.createCell(m);   
						targetCell = targetRow.getCell(m);   
						sourceCell.setCellStyle(targetCell.getCellStyle());   
						sourceCell.setCellType(targetCell.getCellType());   
					}   
					for (int j = 0; j < field.length; j++) {
						if (null != field[j]) {
							//判断值是否为空
							String str=field[j];
							HSSFRichTextString string = new HSSFRichTextString(str);	
							row.getCell(j).setCellValue(string);
						}
					}
					// 合并单元格
					if (null != margeCells) {
						for (int key : margeCells.keySet()) {
							int value = margeCells.get(key);	
							CellRangeAddress cellRangeAddress = new CellRangeAddress(
									startRow + i-1, startRow +i- 1, key, value);
							sheet.addMergedRegion(cellRangeAddress);
						}
					}
					
				}
			}
			
			//设置总值
			if(null!=totalValue){
				int totalRowNum=startRow+values.size()-1;
				HSSFRow totalRow = sheet.getRow(totalRowNum);
				for(int t:totalValue.keySet()){
					String totalString=totalValue.get(t).toString();
					totalRow.getCell(t).setCellValue(new HSSFRichTextString(totalString));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param values 
	 * @param colName 存放对应列号及列名map
	 * @param arraySize 表格列数
	 * @return
	 */
	public static List<String[]> transferMapToString(List<Map<String,Object>> values,Map<Integer,String> colName,Set<Integer> digitCol,int arraySize){
		List<String[]> result=new ArrayList<String []>();
		if(null!=values && values.size()>0){
			for(int i=0;i<values.size();i++){
				Map<String,Object> map=values.get(i);
				String[] value=new String[arraySize];
                for(Map.Entry<Integer,String> entry:colName.entrySet()){
                    //转化数值字段
                    Integer key = entry.getKey();
                    if(null != digitCol && digitCol.contains(key)){
                        value[key]=FormatUtil.tranferObjectToDoubleString(map.get(entry.getValue()));
                    }else{
                        value[key]=FormatUtil.tranferObjectToString(map.get(entry.getValue()));
                    }
                }
                result.add(value);
            }
            return result;
        }
        return null;
    }
	
	
	/**
	 * 
	 * @param values 
	 * @param colName 存放对应列号及列名map
	 * @param arraySize 表格列数
	 * @return
	 */
	public static List<String[]> transferMapToDouble(List<Map<String,Object>> values,Map<Integer,String> colName,Set<Integer> digitCol,int arraySize){
		List<String[]> result=new ArrayList<String []>();
		if(null!=values && values.size()>0){
			for(int i=0;i<values.size();i++){
				Map<String,Object> map=values.get(i);
				String[] value=new String[arraySize];
                for(Map.Entry<Integer,String> entry:colName.entrySet()){
                    //转化数值字段
                    Integer key = entry.getKey();
                    if(null != digitCol && digitCol.contains(key)){
                        value[key]=String.valueOf(map.get(entry.getValue()));
                        
                    }else{
                        value[key]=FormatUtil.tranferObjectToString(map.get(entry.getValue()));
                    }
                }
                result.add(value);
            }
            return result;
        }
        return null;
    }
	
    /**
     *
     * @param values
     * @param colName 存放对应列号及列名map
     * @param arraySize 表格列数
     * @return
     */
    public static List<String[]> transferMapToString1(List<Map<String,Object>> values,Map<Integer,String> colName,Set<Integer> digitCol,int arraySize){
        List<String[]> result=new ArrayList<String []>();
        if(null!=values && values.size()>0){
            for(int i=0;i<values.size();i++){
                Map<String,Object> map=values.get(i);
                String[] value=new String[arraySize];
                for(Map.Entry<Integer,String> entry:colName.entrySet()){
                    /*String str=FormatUtil.tranferObjectToString(map.get(colName.get(key)));
					if(colName.get(key).equals("MCHNT_TYPE")){
						if ("01".equals(str)) {
							str = "集团内部";
						} else if ("02".equals(str)) {
							str = "集团外部";
						} else if ("03".equals(str)) {
							str = "省级内部";
						} else if ("04".equals(str)) {
							str = "省级外部";
						}
					}
					value[key]=str;*/
                    //转化数值字段
                    Integer key = entry.getKey();
                    if(null != digitCol && digitCol.contains(key)){
                        value[key]=FormatUtil.tranferStringNew(map.get(entry.getValue()));
                    }else{
                        value[key]=FormatUtil.tranferObjectToString(map.get(entry.getValue()));
                    }
                }
                result.add(value);
            }
            return result;
        }
        return null;
    }


    /**
     *
     * @param sheet
     * @param values 结果集
     * @param copyRow 复制样式的行
     * @param startRow 结果集开始插入行
     * @param margeCells 需要合并的单元格
     * @param totalValue  需要计算总值的列
     */
    public static void insertRows(HSSFWorkbook book,HSSFSheet sheet, List<String[]> values,
                                  int copyRow, int startRow, Map<Integer, Integer> margeCells,
                                  Map<Integer, Double> totalValue) {
        HSSFRow targetRow = sheet.getRow(copyRow);
        // 1传入的list大小确定添加的行数
        if (null != values && values.size() > 0) {
            int n = values.size();
            // 插入行
            if(n > 1){
                sheet.shiftRows(startRow, sheet.getLastRowNum(), n - 1, true, false);
            }
            HSSFCellStyle moenyStyle=book.createCellStyle();
            HSSFDataFormat format= book.createDataFormat();
            moenyStyle.setDataFormat(format.getFormat("###,###,###,###,###,###,00"));
            // 插入值ֵ
            for (int i = 0; i < values.size(); i++) {

                String[] field = values.get(i);

                if (null != field && field.length > 0) {

                    HSSFCell sourceCell=null;
                    HSSFCell targetCell=null;
                    // 开始的时候有一处空行
                    HSSFRow row = sheet.createRow(startRow + i - 1);
                    for (int m = 0; m < targetRow.getPhysicalNumberOfCells(); m++) {
                        sourceCell = row.createCell(m);
                        targetCell = targetRow.getCell(m);
                        if(targetCell==null)
                            targetCell=targetRow.createCell(m);
                        sourceCell.setCellStyle(targetCell.getCellStyle());
                        sourceCell.setCellType(targetCell.getCellType());
                    }
                    for (int j = 0; j < field.length; j++) {
                        if (null != field[j]) {
                            //判断值是否为空
                            String str=field[j];
                            HSSFCell cell=row.getCell(j);
                            if(cell==null){
                                cell=row.createCell(j);
                            }
                            int cellType= cell.getCellType();
                            if(cellType==HSSFCell.CELL_TYPE_NUMERIC){
                                cell.setCellStyle(moenyStyle);
                                cell.setCellValue(Double.valueOf(str));
                            }else{
                                HSSFRichTextString string = new HSSFRichTextString(str);
                                cell.setCellValue(string);
                            }

                        }
                    }
                    // 合并单元格
                    if (null != margeCells) {
                        for (int key : margeCells.keySet()) {
                            int value = margeCells.get(key);
                            CellRangeAddress cellRangeAddress = new CellRangeAddress(
                                    startRow + i-1, startRow +i- 1, key, value);
                            sheet.addMergedRegion(cellRangeAddress);
                        }
                    }

                }
            }

            //设置总值
            if(null!=totalValue){
                int totalRowNum=startRow+values.size()-1;
                HSSFRow totalRow = sheet.getRow(totalRowNum);
                for(int t:totalValue.keySet()){
                    String totalString=FormatUtil.tranferObjectToDoubleString(totalValue.get(t));
                    totalRow.getCell(t).setCellValue(new HSSFRichTextString(totalString));
                }
            }


        }
    }
    
    
    
    /**
    *
    * @param sheet
    * @param values 结果集
    * @param copyRow 复制样式的行
    * @param startRow 结果集开始插入行
    * @param margeCells 需要合并的单元格
    * @param totalValue  需要计算总值的列
    */
   public static void insertRows(HSSFSheet sheet, List<String[]> values,
                                 int copyRow, int startRow, Map<Integer, Integer> margeCells,
                                 Map<Integer, Double> totalValue,Set<Integer> digitCol) {
       HSSFRow targetRow = sheet.getRow(copyRow);
       // 1传入的list大小确定添加的行数
       if (null != values && values.size() > 0) {
           int n = values.size();
           // 插入行
           if(n > 1){
               sheet.shiftRows(startRow, sheet.getLastRowNum(), n - 1, true, false);
           }
           // 插入值ֵ
           for (int i = 0; i < values.size(); i++) {

               String[] field = values.get(i);

               if (null != field && field.length > 0) {

                   HSSFCell sourceCell=null;
                   HSSFCell targetCell=null;
                   // 开始的时候有一处空行
                   HSSFRow row = sheet.createRow(startRow + i - 1);
                   for (int m = 0; m < targetRow.getPhysicalNumberOfCells(); m++) {
                       sourceCell = row.createCell(m);
                       targetCell = targetRow.getCell(m);
                       if(targetCell==null)
                           targetCell=targetRow.createCell(m);
                       sourceCell.setCellStyle(targetCell.getCellStyle());
                       sourceCell.setCellType(targetCell.getCellType());
                   }
                   for (int j = 0; j < field.length; j++) {
                       if (null != field[j]) {
                           //判断值是否为空
                           String str=field[j];
                           HSSFCell cell=row.getCell(j);
                           if(cell==null){
                               cell=row.createCell(j);
                           }
                           if(digitCol!=null && digitCol.contains(j)){
                               if("null".equals(str)){
                                   cell.setCellValue(new HSSFRichTextString(""));
                               }else{
                                   Double money=Double.valueOf(str);
                                   cell.setCellValue(money);
                               }
                           }else{
                               HSSFRichTextString string = new HSSFRichTextString(str);
                               cell.setCellValue(string);
                           }

                       }
                   }
                   // 合并单元格
                   if (null != margeCells) {
                       for (int key : margeCells.keySet()) {
                           int value = margeCells.get(key);
                           CellRangeAddress cellRangeAddress = new CellRangeAddress(
                                   startRow + i-1, startRow +i- 1, key, value);
                           sheet.addMergedRegion(cellRangeAddress);
                       }
                   }

               }
           }

           //设置总值
           if(null!=totalValue){
               int totalRowNum=startRow+values.size()-1;
               HSSFRow totalRow = sheet.getRow(totalRowNum);
               for(int t:totalValue.keySet()){
            	   if(digitCol!=null && digitCol.contains(t)){
                	   Double money=Double.valueOf(totalValue.get(t));
                	   totalRow.getCell(t).setCellValue(money);
                   }else{
	                   String totalString=FormatUtil.tranferObjectToDoubleString(totalValue.get(t));
	                   totalRow.getCell(t).setCellValue(new HSSFRichTextString(totalString));
                   }
               }
           }


       }
   }
   
   /***
    * 获取某一行中的某一列
    * @param row
    * @param cellnum
    * @return
    */
   public static HSSFCell getCell(HSSFRow row,Integer cellnum){
	   HSSFCell cell=row.getCell(cellnum);
	   if(cell==null){
		   cell=row.createCell(cellnum);
	   }
	   return cell;
   }
   
   
   /***
    * 获取Sheet中的某一行
    * @param sheet
    * @param rowIndex
    * @return
    */
   public static HSSFRow getRow(HSSFSheet sheet,Integer rowIndex){
	   HSSFRow row=sheet.getRow(rowIndex);
	   if(row==null){
		   row=sheet.createRow(rowIndex);
	   }
	   return row;
   }

    public static void setCellValue(HSSFRow row, int cellNo, String value){
        ExcelUtil.setCellValue(row, cellNo, null, value);
    }

    public static void setCellValue(HSSFRow row, int cellNo, HSSFCellStyle style, String value){
        HSSFCell cell = row.getCell(cellNo);
        if(cell == null){
            cell = row.createCell(cellNo);
        }
        if(style != null){
            cell.setCellStyle(style);
        }
        cell.setCellValue(new HSSFRichTextString(value));
    }

    public static void setCellValue(HSSFSheet sheet, int rowNo, int cellNo, String value){
        HSSFRow row = ExcelUtil.getRow(sheet, rowNo);
        ExcelUtil.setCellValue(row, cellNo, value);
    }

    public static void setCellValue(HSSFRow row, int cellNo, int value){
        HSSFCell cell = row.getCell(cellNo);
        if(cell == null){
            cell = row.createCell(cellNo);
        }
        cell.setCellValue(value);
    }

    public static void setCellValue(HSSFRow row, int cellNo, double value){
        HSSFCell cell = row.getCell(cellNo);
        if(cell == null){
            cell = row.createCell(cellNo);
        }
        cell.setCellValue(value);
    }

    public static void setCellValueToDouble(HSSFRow row, int cellNo, Object obj){
        HSSFCell cell = row.getCell(cellNo);
        if(cell == null){
            cell = row.createCell(cellNo);
        }
        cell.setCellValue(Double.parseDouble( obj == null ? "" : String.valueOf("0.0")));
    }
}


