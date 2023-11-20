package com.csv.clientutility.parser.format;

public interface DataFormatHandler {
    void readData(String filePath);  // Method to read data from a file
    void writeData(String filePath); // Method to write data to a file
}
