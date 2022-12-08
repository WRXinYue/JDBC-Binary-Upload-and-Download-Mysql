package com.wyg.model;

public class Brand {

    // 定义sql id, file_name, file_date, file_path

    private String fileName;
    private String fileDate;
    private String filePath;

    public long getFileData() {
        return fileData;
    }

    public void setFileData(long fileData) {
        this.fileData = fileData;
    }

    private long fileData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
