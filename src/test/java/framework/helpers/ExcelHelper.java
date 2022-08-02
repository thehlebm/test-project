package framework.helpers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ExcelHelper {

    private XSSFWorkbook workbook;

    public ExcelHelper(File excelFile) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(OPCPackage.open(excelFile));
    }

    public Map<String, String> getValuesBySheetName(String sheetName, int headerRowIndex, int dataRowIndex) {
        Map<String, String> values = new HashMap<>();
        XSSFSheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(headerRowIndex);
        Iterator<Cell> headerRowIterator = headerRow.iterator();
        headerRowIterator.forEachRemaining(cell -> {
            int currentHeaderColumnIndex = cell.getColumnIndex();
            String headerCellValue = cell.getStringCellValue();
            String dataCellValue = sheet.getRow(dataRowIndex)
                    .getCell(currentHeaderColumnIndex)
                    .getStringCellValue();
            values.put(headerCellValue, dataCellValue);
        });
        return values;
    }

    public List<Map<String, String>> getListOfValuesBySheetName(String sheetName, int headerRowIndex) {
        List<Map<String, String>> listOfValues = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(headerRowIndex);
        sheet.rowIterator()
                .forEachRemaining(row -> {
                    Map<String, String> values = new HashMap<>();
                    if (row.getRowNum() != headerRowIndex) {
                        headerRow.iterator()
                                .forEachRemaining(cell -> {
                                    int currentHeaderColumnIndex = cell.getColumnIndex();
                                    String headerCellValue = cell.getStringCellValue();
                                    String dataCellValue = row.getCell(currentHeaderColumnIndex)
                                            .getStringCellValue();
                                    values.put(headerCellValue, dataCellValue);
                                });
                    }
                    if (!values.isEmpty()) {
                        listOfValues.add(values);
                    }
                });

        return listOfValues;
    }

    public long getLastRowNumBySheetName(String sheetName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    public List<String> getHeaderNamesBySheetName(String sheetName, int headerRowIndex) {
        List<String> headerNames = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(headerRowIndex);
        Iterator<Cell> headerRowIterator = headerRow.iterator();
        headerRowIterator.forEachRemaining(cell -> {
            String headerCellValue = cell.getStringCellValue();
            headerNames.add(headerCellValue);
        });
        return headerNames;
    }

    public int getColumnIndexByHeaderName(String sheetName, int headerRowIndex, String headerName) {
        AtomicInteger columnIndex = new AtomicInteger(-1);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(headerRowIndex);
        Iterator<Cell> headerRowIterator = headerRow.iterator();
        headerRowIterator.forEachRemaining(cell -> {
            if (cell.getStringCellValue().equals(headerName)) {
                columnIndex.set(cell.getColumnIndex());
            }
        });
        if (columnIndex.get() == -1) {
            throw new AssertionError(String.format("Column name '%s' on sheet '%s' not found!", headerName, sheetName));
        }
        return columnIndex.get();
    }

    public List<String> getSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames.add(workbook.getSheetName(i));
        }
        return sheetNames;
    }
}