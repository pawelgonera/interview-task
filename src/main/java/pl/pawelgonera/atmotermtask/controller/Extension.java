package pl.pawelgonera.atmotermtask.controller;

public enum Extension {
    PDF("PDF"),
    XLS("XLS");

    private String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
