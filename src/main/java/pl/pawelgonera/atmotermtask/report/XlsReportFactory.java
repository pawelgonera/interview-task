package pl.pawelgonera.atmotermtask.report;

public class XlsReportFactory extends ReportFactory {

    @Override
    public ReportGenerator newReportGenerator() {
        return new XlsReportGenerator();
    }
}
