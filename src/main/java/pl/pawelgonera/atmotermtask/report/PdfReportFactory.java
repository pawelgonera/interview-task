package pl.pawelgonera.atmotermtask.report;

public class PdfReportFactory extends ReportFactory {

    @Override
    public ReportGenerator newReportGenerator() {
        return new PdfReportGenerator();
    }
}
