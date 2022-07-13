package com.example.project.springbootrestapicsvexportservice.service;

import com.example.project.springbootrestapicsvexportservice.model.TransactionDetailsDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvExportService {

    public List<TransactionDetailsDTO> produceTransactionsJson() throws IOException {
        List<TransactionDetailsDTO> transactionDetailsDTOs = new ArrayList<>();
        for(Object[] obj : readInputFile()){
            TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();
            transactionDetailsDTO.setClient_Information(obj[0].toString());
            transactionDetailsDTO.setProduct_Information(obj[1].toString());
            transactionDetailsDTO.setTotal_Transaction_Amount(obj[2].toString());
            transactionDetailsDTOs.add(transactionDetailsDTO);
        }
        return transactionDetailsDTOs;
    }

    public void writeTransactionsToCsv(final Writer writer) throws IOException {
        writeToCsv(writer, readInputFile());
    }

    private List<Object[]> readInputFile() throws IOException {
        File resource = new ClassPathResource("Input.txt").getFile();
        BufferedReader fileBufferReader = new BufferedReader(new FileReader(resource));
        String fileLineContent;
        List<Object[]> transformedData = new ArrayList<>();
        try {
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                String clientInformation = fileLineContent.substring(3, 7) + fileLineContent.substring(7, 11) + fileLineContent.substring(11, 15) + fileLineContent.substring(15, 19);
                String productInformation = fileLineContent.substring(27, 31) + fileLineContent.substring(25, 27) + fileLineContent.substring(31, 37) + fileLineContent.substring(37, 45);
                int totalTransactionInformation = Integer.valueOf(fileLineContent.substring(52, 62)) - Integer.valueOf(fileLineContent.substring(63, 73));
                transformedData.add(new Object[]{clientInformation, productInformation, totalTransactionInformation});
            }
        } catch (IOException e) {
            throw new IOException("File is either empty or invalid", e);
        }
        return transformedData;
    }

    private void writeToCsv(final Writer writer, List<Object[]> data) throws IOException {
        String[] HEADERS = {"Client_Information", "Product_Information", "Total_Transaction_Amount"};
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            csvPrinter.printRecords(data);
            csvPrinter.flush();
            writer.close();
        } catch (IOException e) {
            throw new IOException("CSV Processing Exception", e);
        }
    }
}
