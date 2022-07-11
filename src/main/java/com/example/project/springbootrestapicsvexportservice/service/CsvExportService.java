package com.example.project.springbootrestapicsvexportservice.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvExportService {

    public void writeTransactionsToCsv(Writer writer) throws IOException {

        //String fileName = "D:\\Work\\Code\\technical-test -container revised\\technical-test -container revised\\input.txt"; //this path is on my local
        File resource = new ClassPathResource("Input.txt").getFile();
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(resource))) {
            String fileLineContent;
            List<Object[]> data = new ArrayList<>();
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                String clientInformation = fileLineContent.substring(3,7) + fileLineContent.substring(7,11) + fileLineContent.substring(11,15) + fileLineContent.substring(15,19);
                String productInformation = fileLineContent.substring(27,31) + fileLineContent.substring(25,27) + fileLineContent.substring(31,37) + fileLineContent.substring(37,45);
                int totalTransactionInformation = Integer.valueOf(fileLineContent.substring(52,62)) - Integer.valueOf(fileLineContent.substring(63,73));
                data.add(new Object[] {clientInformation, productInformation, totalTransactionInformation});
            }
            writeToCsv(writer, data);
        }
    }

    private void writeToCsv( Writer writer, List<Object[]> data) throws IOException {
        String[] HEADERS = { "Client_Information", "Product_Information", "Total_Transaction_Amount"};
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            csvPrinter.printRecords(data);
            csvPrinter.flush();
            writer.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
