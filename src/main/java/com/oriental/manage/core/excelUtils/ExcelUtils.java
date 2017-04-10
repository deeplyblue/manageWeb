package com.oriental.manage.core.excelUtils;


import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.StringCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);
	/**
	 * 读取excel中的数据 返回一个list
	 * @param fileName 文件
	 * @param startline 从第几行开始读取
	 * @param sheetNum  从第几列开始读取
	 * @return
	 * @throws Exception
	 */
	public static List<Object []> getDataFromExcel(File fileName,int startline,int sheetNum) throws Exception {
		ArrayList<Object []> arraylist;
		try {
			// 先用excel2003的方法 读取数据
			arraylist = (ArrayList<Object []>) getDataFromExcel03(fileName, startline,sheetNum);
		} catch (Exception e) {
			LOG.debug("异常 ::", e);
			// 如果出现异常， 则用excel2007的方法 读取数据
			arraylist = (ArrayList<Object []>) getDataFromExcel07(fileName, startline,sheetNum);
		}
		return arraylist;
	}


	/**
	 * 读取excel中的数据 返回一个list
	 * @param fileName 文件
	 * @param startline 从第几行开始读取
	 * @param sheetNum  从第几列开始读取
	 * @param word0
	 * @return
	 * @throws Exception
	 */
    public static List<Object []> getDataFromExcel(File fileName,int startline,int sheetNum,int word0) throws Exception {
        ArrayList<Object []> arraylist;
        try {
            // 先用excel2003的方法 读取数据
            arraylist = (ArrayList<Object []>) getDataFromExcel03(fileName, startline,sheetNum,word0);
        } catch (Exception e) {
            log.error("使用excel2003读取数据失败，下一步，使用excel2007",e);
            // 如果出现异常， 则用excel2007的方法 读取数据
            arraylist = (ArrayList<Object []>) getDataFromExcel07(fileName, startline,sheetNum,word0);
        }
        return arraylist;
    }

    /**
	 * 读取excel数据 适用excel2007
	 * @param filepath 文件路径
	 * @param startline 读取开始行数
	 * @return
	 * @throws Exception
	 */
	public static List<Object []> getDataFromExcel07(String filepath, int startline) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(filepath);
		ArrayList<Object []> arraylist = new ArrayList<Object []>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet xssfsheet = (XSSFSheet) wb.getSheetAt(i);
			for (int j = 0; j < xssfsheet.getPhysicalNumberOfRows(); j++) {
				if (j > startline) {
					XSSFRow xssfrow = xssfsheet.getRow(j);
					if (xssfrow == null) {
						break;
					}
					short word0 = xssfrow.getLastCellNum();
					if (word0 >= 0) {
						Object[] aobj = new Object[word0];
						for (int k = 0; k < word0; k++) {
							XSSFCell xssfcell = xssfrow.getCell((short) k);
							if (xssfcell != null)
								switch (xssfcell.getCellType()) {
								case 0:
									double d = xssfcell.getNumericCellValue();
									int intD = (int)d;
									if (intD == Double.doubleToRawLongBits(d)) {
										aobj[k] = intD;
										continue;
									}
									aobj[k] = d;
									break;
								case 1:
									aobj[k] = xssfcell.getStringCellValue().trim();
									break;
								case 2:
									aobj[k] = xssfcell.getCellFormula().trim();
									break;
								case 3:
									aobj[k] = "";
									break;
								case 4:
									aobj[k] = Boolean.valueOf(xssfcell.getBooleanCellValue());
									break;
								case 5:
									aobj[k] = Byte.valueOf(xssfcell.getErrorCellValue());
									break;
								default:
									aobj[k] = "";
									break;
								}
							else {
								aobj[k] = "";
							}
						}
						arraylist.add(aobj);
					}
				}
			}
		}
		return arraylist;
	}

	/**
	 * 读取excel数据 适用excel2003
	 * @param fileName 文件名
	 * @param startline 读取开始行数
	 * @param sheetNum  开始读取的列
	 * @return 解析后的数据
	 * @throws Exception
	 */
	public static List<Object []> getDataFromExcel03(File fileName, int startline, int sheetNum) throws Exception {
		ArrayList<Object[]> arraylist = new ArrayList<Object[]>();
		InputStream inputstream = new FileInputStream(fileName);
		HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);
	    HSSFSheet hssfsheet = hssfworkbook.getSheetAt(sheetNum);
        // 获取标题行号
        int titleLineNo = startline > 0 ? startline - 1 : 0;
        // 获取标题列数
        short columnNum = hssfsheet.getRow(titleLineNo).getLastCellNum();
        // 获取每行数据
        for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
            if (j >= startline) {
                HSSFRow hssfrow = hssfsheet.getRow(j);
                if (hssfrow == null) {
                    break;
                }
                // 创建于标题列数相同大小的数组,用于保存每一行数据
                Object[] aobj = new Object[columnNum];
                for (int k = 0; k < columnNum; k++) {
                    HSSFCell hssfcell = ExcelUtil.getCell(hssfrow, k);
                    if (hssfcell != null){
                        switch (hssfcell.getCellType()) {
                        case 0:
                            double d = hssfcell.getNumericCellValue();
                            if(String.valueOf(d).contains("E") ){
                                DecimalFormat df = new DecimalFormat("#0.00");
                                aobj[k] = df.format(d * 100);
                            }else if(String.valueOf(d).contains(".")){
                                DecimalFormat format = new DecimalFormat("#####0.00");
                                aobj[k] = format.format(d * 100);
                            }else{
                                if (HSSFDateUtil.isCellDateFormatted(hssfcell)) {
                                    aobj[k] = new java.sql.Date(HSSFDateUtil.getJavaDate(d).getTime());
                                    continue;
                                }
                                int intD = (int) d;
                                if (intD == Double.doubleToRawLongBits(d)) {
                                    aobj[k] = intD;
                                    continue;
                                }
                                aobj[k] = d;
                            }
                            break;
                        case 1:
                            aobj[k] = hssfcell.getRichStringCellValue().getString().trim();
                            break;
                        case 2:
                            aobj[k] = hssfcell.getCellFormula().trim();
                            break;
                        case 3:
                            aobj[k] = hssfcell.getRichStringCellValue().getString().trim();
                            break;
                        case 4:
                            aobj[k] = hssfcell.getBooleanCellValue();
                            break;
                        case 5:
                            aobj[k] = hssfcell.getErrorCellValue();
                            break;
                        default:
                            aobj[k] = "";
                            break;
                        }
                    } else {
                        aobj[k] = "";
                    }
                }
                arraylist.add(aobj);
            }
        }
		return arraylist;
	}

	/**
	 * 读取excel数据 适用excel2003
	 * @param fileName
	 * @param startline 读取开始行数
	 * @param sheetNum  开始读取的列
	 * @param word0
	 * @return
	 * @throws Exception
	 */
	public static List<Object []> getDataFromExcel03(File fileName, int startline,int sheetNum,int word0) throws Exception {
        ArrayList<Object []> arraylist = new ArrayList<Object []>();
        InputStream inputstream = new FileInputStream(fileName);
        HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(sheetNum);
        for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
            if (j >= startline) {
                HSSFRow hssfrow = hssfsheet.getRow(j);
                if (hssfrow == null) {
                    break;
                }
                if (word0 >= 0) {
                    Object[] aobj = new Object[word0];
                    for (int k = 0; k < word0; k++) {
                        HSSFCell hssfcell = hssfrow.getCell(k);
                        if (hssfcell != null){
                            switch (hssfcell.getCellType()) {
                                case 0:
                                    double d = hssfcell.getNumericCellValue();
                                    if (HSSFDateUtil.isCellDateFormatted(hssfcell)) {
                                        aobj[k] = new java.sql.Date(HSSFDateUtil.getJavaDate(d).getTime());
                                        continue;
                                    }
                                    int intD = (int) d;
                                    if (intD == Double.doubleToRawLongBits(d)) {
                                        aobj[k] = intD;
                                        continue;
                                    }
                                    aobj[k] = d;
                                    break;
                                case 1:
                                    aobj[k] = hssfcell.getRichStringCellValue().getString().trim();
                                    break;
                                case 2:
                                    aobj[k] = hssfcell.getCellFormula().trim();
                                    break;
                                case 3:
                                    aobj[k] = "";
                                    break;
                                case 4:
                                    aobj[k] = Boolean.valueOf(hssfcell.getBooleanCellValue());
                                    break;
                                case 5:
                                    aobj[k] = Byte.valueOf(hssfcell.getErrorCellValue());
                                    break;
                                default:
                                    aobj[k] = "";
                                    break;
                            }
                        }else {
                            aobj[k] = "";
                        }
                    }
                    arraylist.add(aobj);
                }
            }
        }
        return arraylist;
    }

	/**
	 * 读取excel数据 适用excel2007
	 * @param fileName
	 * @param startline 读取开始行数
	 * @param sheetNum  开始读取的列
	 * @param word0
	 * @return
	 * @throws Exception
	 */
    public static List<Object []> getDataFromExcel07(File fileName, int startline,int sheetNum,int word0) throws Exception {
        ArrayList<Object []> arraylist = new ArrayList<Object []>();
        InputStream inputstream = new FileInputStream(fileName);
        XSSFWorkbook wb = new XSSFWorkbook(inputstream);
        XSSFSheet xssfsheet = (XSSFSheet) wb.getSheetAt(sheetNum);
        for (int j = 0; j < xssfsheet.getPhysicalNumberOfRows(); j++) {
            if (j > startline) {
                XSSFRow xssfrow = xssfsheet.getRow(j);
                if (xssfrow == null) {
                    break;
                }
                if (word0 >= 0) {
                    Object[] aobj = new Object[word0];
                    for (int k = 0; k < word0; k++) {
                        XSSFCell xssfcell = xssfrow.getCell((short) k);
                        if (xssfcell != null){
                            switch (xssfcell.getCellType()) {
                                case 0:
                                    double d = xssfcell.getNumericCellValue();
                                    int intD = (int) d;
                                    if (intD == Double.doubleToRawLongBits(d)) {
                                        aobj[k] = intD;
                                        continue;
                                    }
                                    aobj[k] = d;
                                    break;
                                case 1:
                                    aobj[k] = xssfcell.getStringCellValue().trim();
                                    break;
                                case 2:
                                    aobj[k] = xssfcell.getCellFormula().trim();
                                    break;
                                case 3:
                                    aobj[k] = "";
                                    break;
                                case 4:
                                    aobj[k] = Boolean.valueOf(xssfcell.getBooleanCellValue());
                                    break;
                                case 5:
                                    aobj[k] = Byte.valueOf(xssfcell.getErrorCellValue());
                                    break;
                                default:
                                    aobj[k] = "";
                                    break;
                            }
                        }
                        else {
                            aobj[k] = "";
                        }
                    }
                    arraylist.add(aobj);
                }
            }
        }
        return arraylist;
    }

	/**
	 * 读取excel数据 适用excel2007
	 * @param fileName
	 * @param startline 读取开始行数
	 * @param sheetNum
	 * @return
	 * @throws Exception
	 */
	public static List<Object []> getDataFromExcel07(File fileName, int startline,int sheetNum) throws Exception {
		ArrayList<Object []> arraylist = new ArrayList<Object []>();
		InputStream inputstream = new FileInputStream(fileName);
		XSSFWorkbook wb = new XSSFWorkbook(inputstream);
			XSSFSheet xssfsheet = (XSSFSheet) wb.getSheetAt(sheetNum);
			for (int j = 0; j < xssfsheet.getPhysicalNumberOfRows(); j++) {
				if (j > startline) {
					XSSFRow xssfrow = xssfsheet.getRow(j);
					if (xssfrow == null) {
						break;
					}
					short word0 = xssfrow.getLastCellNum();
					if (word0 >= 0) {
						Object[] aobj = new Object[word0];
						for (int k = 0; k < word0; k++) {
							XSSFCell xssfcell = xssfrow.getCell((short) k);
							if (xssfcell != null)
								switch (xssfcell.getCellType()) {
								case 0:
									double d = xssfcell.getNumericCellValue();
									int intD = (int) d;
									if (intD == Double.doubleToRawLongBits(d)) {
										aobj[k] = intD;
										continue;
									}
									aobj[k] = d;
									break;
								case 1:
									aobj[k] = xssfcell.getStringCellValue().trim();
									break;
								case 2:
									aobj[k] = xssfcell.getCellFormula().trim();
									break;
								case 3:
									aobj[k] = "";
									break;
								case 4:
									aobj[k] = Boolean.valueOf(xssfcell.getBooleanCellValue());
									break;
								case 5:
									aobj[k] = Byte.valueOf(xssfcell.getErrorCellValue());
									break;
								default:
									aobj[k] = "";
									break;
								}
							else {
								aobj[k] = "";
							}
						}
						arraylist.add(aobj);
					}
				}
			}
		return arraylist;
	}


	/**
	 * 往Excel03中写入数据  本方法是写入第一列
	 * @param fileName 要写入的文件
	 * @param list 要写入的数据
	 * @param startline 开始行号
	 * @param sheetNum 第几Sheet页
	 * @param cellNum 第几个单元格
	 */
	public static boolean setDataToExcel03(File fileName, List<Object[]> list, int startline, int sheetNum,int cellNum) {
		boolean result = false;
		FileOutputStream fileoutputstream = null;
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream(fileName);
			HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);
			HSSFSheet hssfsheet = hssfworkbook.getSheetAt(sheetNum);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow hssfrow = hssfsheet.getRow(startline + i);
				HSSFCell hssfcell = hssfrow.createCell(cellNum);
				String res = new String(list.get(i)[0].toString());
				hssfcell.setCellValue(new HSSFRichTextString(res));
			}
			fileoutputstream = new FileOutputStream(fileName.getAbsolutePath());
			hssfworkbook.write(fileoutputstream);
			result = true;
		} catch (FileNotFoundException e) {
			LOG.error("",e);
		} catch (IOException e) {
			LOG.error("",e);
		} finally {
			IOUtils.closeQuietly(fileoutputstream);
			IOUtils.closeQuietly(inputstream);
		}
		return result;
	}


    /**
     * 往Excel03中写入数据  本方法是写入第一列
     * @param fileName 要写入的文件
     * @param list 要写入的数据
     * @param startline 开始行号
     * @param sheetNum 第几Sheet页
     * @param cellNum 第几个单元格
     * @param columNum 插入第几列
     */
    public static boolean setDataToExcel03(File fileName, List<Object[]> list, int startline, int sheetNum,int cellNum,int columNum) {
        boolean result = false;
        FileOutputStream fileoutputstream = null;
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(fileName);
            HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);
            HSSFSheet hssfsheet = hssfworkbook.getSheetAt(sheetNum);
            for (int i = 0; i < list.size(); i++) {
                HSSFRow hssfrow = hssfsheet.getRow(startline + i);
                HSSFCell hssfcell = hssfrow.createCell(cellNum);
                String res = new String(list.get(i)[columNum].toString());
                hssfcell.setCellValue(new HSSFRichTextString(res));
            }
            fileoutputstream = new FileOutputStream(fileName.getAbsolutePath());
            hssfworkbook.write(fileoutputstream);
            result = true;
        } catch (FileNotFoundException e) {
            LOG.error("",e);
        } catch (IOException e) {
            LOG.error("",e);
        } finally {
            IOUtils.closeQuietly(fileoutputstream);
            IOUtils.closeQuietly(inputstream);
        }
        return result;
    }

    /**
     * 往指定单元格写入数据
     * @param msg 写入内容
     * @param rowNum 目标行
     * @param columnNum 第几个单元格
     * @return
     */
    public static void setDataToExcel03(HSSFSheet sheet, String msg, int rowNum,int columnNum) throws Exception{
        HSSFRow hssfrow = sheet.getRow(rowNum);
        hssfrow = hssfrow==null?sheet.createRow(rowNum):hssfrow;
        HSSFCell hssfcell = hssfrow.getCell(columnNum);
        hssfcell = hssfcell==null?hssfrow.createCell(columnNum):hssfcell;
        hssfcell.setCellValue(new HSSFRichTextString(msg));
    }

	/**
	 * 往Excel03中写入数据  本方法是写入第一列
	 * @param list 要写入的数据
	 * @param startline 开始行号
	 * @param sheetNum 第几Sheet页
	 */
	public static boolean writeDataToExcel03(File file, List<String> list, int startline, int sheetNum) {
		boolean result = false;
		FileOutputStream fileoutputstream = null;
		try {
			HSSFWorkbook hssfworkbook = new HSSFWorkbook();
			HSSFSheet hssfsheet = hssfworkbook.createSheet();
			for (int i = 0; i < list.size(); i++) {
				HSSFRow hssfrow = hssfsheet.createRow(startline + i);
				String[] rowData =  list.get(i).split(",");
				for(int j=0;j<rowData.length;j++){
					HSSFCell hssfcell = hssfrow.createCell(j);
					hssfcell.setCellValue(new HSSFRichTextString(rowData[j]));
				}
			}
			fileoutputstream = new FileOutputStream(file.getAbsolutePath());
			hssfworkbook.write(fileoutputstream);
			result = true;
		} catch (FileNotFoundException e) {
			LOG.error("",e);
		} catch (IOException e) {
			LOG.error("",e);
		} finally {
			IOUtils.closeQuietly(fileoutputstream);
		}
		return result;
	}

    public static String getCellValueString(HSSFSheet sheet,int rowNum,int cellNum) throws Exception{
        HSSFRow row = sheet.getRow(rowNum);
        if (row == null){
            return "";
        }
        HSSFCell cell = row.getCell(cellNum);
        if(cell == null){
            return "";
        }
        return cell.getRichStringCellValue().toString();
    }

	//添加标题
	public static File addTitle(File file, String name, FileDownAjax fileDownAjax, int size, String oDateStart, String oDateEnd) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		File finalFile =null;
		FileOutputStream outputStream=null;
		String time= null;
		try{
			if(StringUtils.equals(oDateEnd,"")){
				time = StringCommonUtils.insertStr(oDateStart,"-",4,7);
			}else{
				time = StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
						StringCommonUtils.insertStr(oDateEnd,"-",4,7);
			}
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			CellStyle style = wb.createCellStyle();
			CellStyle style1 = wb.createCellStyle();
			//设置居中样式
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
			//设置字体
			Font font = wb.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
			style.setFont(font);
			//插入2行
			sheet.shiftRows(0, sheet.getLastRowNum(), 2,true,false);
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue(name);
			cell.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,size-1));
			//第二行
			style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//右侧
			Row row1 = sheet.createRow(1);
			Cell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("日期范围："+time);
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,size-1));
			finalFile = fileDownAjax.touch(name+"-" + DateUtils.now() + ".xlsx");
			outputStream = new FileOutputStream(finalFile);
			wb.write(outputStream);

		}catch (Exception e){
			log.error("系统异常", e);
		}finally {
			if(inputStream!=null){
				inputStream.close();
			}
			if(outputStream!=null){
				outputStream.close();
			}
		}
		return finalFile;
	}


}
