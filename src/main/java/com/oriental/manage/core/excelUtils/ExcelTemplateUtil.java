package com.oriental.manage.core.excelUtils;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.TemplateExcelWriterCallBack;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.webbuilder.office.excel.api.poi.callback.POIExcelWriterProcessor;
import org.webbuilder.office.excel.config.ExcelWriterConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

/**
 * Created by lupf on 2016/7/21.
 */
@Slf4j
public class ExcelTemplateUtil {

    public static void write(InputStream inputStream, OutputStream outputStream, ExcelWriterConfig config) throws IOException, InvalidFormatException {

        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }

        Workbook wb;
        if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            try {
                wb = new HSSFWorkbook(inputStream);
            }catch (Exception e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
            try {
                wb = WorkbookFactory.create(inputStream);
            }catch (InvalidFormatException e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else {
            throw new BusiException("不支持的EXCEL类型");
        }

        POIExcelWriterProcessor processor = new POIExcelWriterProcessor(outputStream, wb);

        TemplateExcelWriterCallBack callBack = new TemplateExcelWriterCallBack(config);

        callBack.getConfig().setSheetName(wb.getSheetName(0));
        callBack.render(processor);
        outputStream.flush();

    }

    //对标题添加时间
    public static void write(InputStream inputStream, OutputStream outputStream, ExcelWriterConfig config,String time) throws IOException, InvalidFormatException {

        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }

        Workbook wb;
        if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            try {
                wb = new HSSFWorkbook(inputStream);
                CellStyle style = wb.createCellStyle();
                Sheet sheetAt = wb.getSheetAt(0);
                Row row = sheetAt.getRow(1);
                Cell cell = row.getCell(0);
                cell.setCellStyle(style);
                cell.setCellValue("日期范围："+time);
            }catch (Exception e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
            try {
                wb = new XSSFWorkbook(inputStream);
                CellStyle style = wb.createCellStyle();
                style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//右侧
                Sheet sheetAt = wb.getSheetAt(0);
                Row row = sheetAt.getRow(1);
                Cell cell = row.getCell(0);
                cell.setCellStyle(style);
                cell.setCellValue("日期范围："+time);
            }catch (Exception e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else {
            throw new BusiException("不支持的EXCEL类型");
        }

        POIExcelWriterProcessor processor = new POIExcelWriterProcessor(outputStream, wb);

        TemplateExcelWriterCallBack callBack = new TemplateExcelWriterCallBack(config);

        callBack.getConfig().setSheetName(wb.getSheetName(0));
        callBack.render(processor);
        outputStream.flush();

    }

    public static void writeTitle(InputStream inputStream, OutputStream outputStream, ExcelWriterConfig config,String time) throws IOException, InvalidFormatException {

        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }

        Workbook wb;
        if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            try {
                wb = new HSSFWorkbook(inputStream);
                CellStyle style = wb.createCellStyle();
                Sheet sheetAt = wb.getSheetAt(0);
                Row row = sheetAt.getRow(1);
                Cell cell = row.getCell(0);
                cell.setCellStyle(style);
                cell.setCellValue("日期范围："+time);
            }catch (Exception e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
            try {
                wb = new XSSFWorkbook(inputStream);
                CellStyle style = wb.createCellStyle();
                style.setAlignment(XSSFCellStyle.ALIGN_CENTER);//中間
                Sheet sheetAt = wb.getSheetAt(0);
                Row row = sheetAt.getRow(2);
                Cell cell = row.getCell(0);
                cell.setCellStyle(style);
                cell.setCellValue(time+"编制");
            }catch (Exception e){
                log.error("系统异常", e);
                wb = new XSSFWorkbook(inputStream);
            }
        } else {
            throw new BusiException("不支持的EXCEL类型");
        }

        POIExcelWriterProcessor processor = new POIExcelWriterProcessor(outputStream, wb);

        TemplateExcelWriterCallBack callBack = new TemplateExcelWriterCallBack(config);

        callBack.getConfig().setSheetName(wb.getSheetName(0));
        callBack.render(processor);
        outputStream.flush();

    }
}
