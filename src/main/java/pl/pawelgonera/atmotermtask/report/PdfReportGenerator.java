package pl.pawelgonera.atmotermtask.report;

import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;
import pl.pawelgonera.atmotermtask.entity.EmployeeXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class PdfReportGenerator implements ReportGenerator {

    private static final String FILE_NAME = "employee-report.pdf";
    private static final String TEMPLATE_URL = "src/main/resources/templates/pdf-template.xsl";
    private static final String CONFIG_URL = "src/main/resources/templates/fop.xconf.xml";

    @Override
    public String generateReport(EmployeeXml employeeData) {

        String message = "The report file: '" + FILE_NAME + "' was successfully generated";

        ByteArrayOutputStream xmlSource = getXmlData(employeeData);



        try {
            File xsltTemplate = new File(TEMPLATE_URL);
            File directory = new File("./report");

            try {
                directory = createDir(directory, FILE_NAME);
            }catch (IOException e){
                message = e.getMessage();
                e.printStackTrace();
            }

            File pdfFile = new File(directory, FILE_NAME);

            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pdfFile))){

                FopFactory fopFactory = FopFactory.newInstance(new File(CONFIG_URL));
                FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);

                TransformerFactory factory = new TransformerFactoryImpl();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltTemplate));

                StreamSource inputSource = new StreamSource(new ByteArrayInputStream(xmlSource.toByteArray()));

                Result outputResult = new SAXResult(fop.getDefaultHandler());

                transformer.transform(inputSource, outputResult);

            } catch (FOPException | TransformerException e) {
                message = e.getMessage();
                e.printStackTrace();
            }
        }catch(IOException | SAXException ex){
            message = ex.getMessage();
            ex.printStackTrace();
        }

        return message;
    }

    private ByteArrayOutputStream getXmlData(EmployeeXml data) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeXml.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, outStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return outStream;

    }
}
