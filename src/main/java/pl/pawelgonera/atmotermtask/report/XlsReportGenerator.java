package pl.pawelgonera.atmotermtask.report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import pl.pawelgonera.atmotermtask.entity.Employee;
import pl.pawelgonera.atmotermtask.entity.EmployeeXml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsReportGenerator implements ReportGenerator {

    private static final String[] HEADERS = {"Id", "Name",  "Salary", "Employment Date"};
    private static final String FILE_NAME = "employee-report.xls";

    @Override
    public String generateReport(EmployeeXml employeeData) {

        String message = "The report file: '" + FILE_NAME + "' was successfully generated";

        List<Employee> employees = employeeData.getEmployees();

        Workbook workbook = new HSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Employee's Report");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLUE.getIndex());
        font.setFontHeightInPoints((short) 14);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        Row topRow = sheet.createRow(0);
        for(int i = 0; i < HEADERS.length; i++){
            Cell cell = topRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(cellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-YYYY"));

        int rowNum = 1;
        for(Employee employee : employees){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getActiveEmployee().getSalary());
            Cell employmentDateCell = row.createCell(3);
            employmentDateCell.setCellValue(employee.getActiveEmployee().getEmploymentDate());
            employmentDateCell.setCellStyle(dateCellStyle);
        }

        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        File directory = new File("./report");

        try {
            directory = createDir(directory, FILE_NAME);
        }catch (IOException e){
            message = e.getMessage();
            e.printStackTrace();
        }

        File xlsFile = new File(directory, FILE_NAME);

        try(FileOutputStream fileOutputStream = new FileOutputStream(xlsFile))
        {
            workbook.write(fileOutputStream);

        }catch (IOException e){
            message = e.getMessage();
            e.printStackTrace();
        }

        return message;
    }
}
