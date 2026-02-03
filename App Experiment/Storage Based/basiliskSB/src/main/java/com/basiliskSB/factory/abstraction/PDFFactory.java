package com.basiliskSB.factory.abstraction;

public interface PDFFactory extends SingleFactory{
    public byte[] getPdfByte() throws Exception;
    public void setFileName(String fileName);
    public String getFileNameDisposition();
}
