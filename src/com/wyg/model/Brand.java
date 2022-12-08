package com.wyg.model;

public class Brand {

    // 定义sql id, file_name, file_date, file_path

    private int fileId;
    private String fileName;
    private String fileDate;
    private String filePath;
    private long fileData;

    public long getFileData() {
        return fileData;
    }

    public void setFileData(long fileData) {
        this.fileData = fileData;
    }

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

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
