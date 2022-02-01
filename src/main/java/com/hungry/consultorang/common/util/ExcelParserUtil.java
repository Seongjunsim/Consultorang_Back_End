package com.hungry.consultorang.common.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelParserUtil {

    private String fileNm;
    private FileInputStream fis;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private int rowSize;

    public ExcelParserUtil(String fileNm, int SheetNum) throws Exception{
        this.fileNm = fileNm;
        fis = new FileInputStream(fileNm);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheetAt(SheetNum);
        rowSize = sheet.getPhysicalNumberOfRows();
    }

    public int getRowSize(){return rowSize;}
    public int getColSize(int row){return sheet.getRow(row).getPhysicalNumberOfCells()+1;}

    public String getCellData(int row, int col){
        if(row < 0 || col < 0 || row > rowSize || col >getColSize(row)) return null;
        String val="";
        XSSFCell cell = sheet.getRow(row).getCell(col);
        if(cell==null) return null;
        CellType type = cell.getCellType();
        switch (type.name()){
            case "FORMULA":
            case "NUMERIC":
                val=cell.getNumericCellValue()+"";
                break;
            case "STRING":
                val=cell.getStringCellValue()+"";
                break;
            case "BOOLEAN":
                val=cell.getBooleanCellValue()+"";
                break;
        }
        return val;
    }

    public void close() throws Exception{
        workbook.close();
        fis.close();;
    }



}
