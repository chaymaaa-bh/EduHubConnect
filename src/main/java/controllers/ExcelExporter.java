package controllers;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelExporter {

    public static void exportToExcel(TableView<?> tableView, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Results");
            int rowCount = 0;
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Row row = sheet.createRow(rowCount++);
                for (int j = 0; j < tableView.getColumns().size(); j++) {
                    TableColumn<?, ?> column = (TableColumn<?, ?>) tableView.getColumns().get(j);
                    Object cellValue = column.getCellData(i);
                    Cell cell = row.createCell(j);
                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
