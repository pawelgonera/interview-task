package pl.pawelgonera.atmotermtask.report;

import pl.pawelgonera.atmotermtask.entity.EmployeeXml;

import java.io.File;
import java.io.IOException;

public interface ReportGenerator {

    String generateReport(EmployeeXml employeeData);

    default File createDir(File directory, String fileName) throws IOException {

        if(!directory.exists()){
            boolean isFileLocationCreated = directory.mkdirs();
            if(!isFileLocationCreated){
                throw new IOException("File " + fileName + " could not be created");
            }
        }

        return directory;
    }

}
