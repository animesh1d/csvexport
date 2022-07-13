package com.example.project.springbootrestapicsvexportservice.controller;



import com.example.project.springbootrestapicsvexportservice.model.TransactionDetailsDTO;
import com.example.project.springbootrestapicsvexportservice.service.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class CsvExporterController {

    @Autowired
    private CsvExportService csvExportService;

    @GetMapping(value = "/export/csv", produces = "text/csv")
    public void writeToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"transactions.csv\"");
        csvExportService.writeTransactionsToCsv(response.getWriter());
    }

    @GetMapping(value = "/produce/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDetailsDTO>> produceTransactionsJson() throws IOException {
        return new ResponseEntity<>(csvExportService.produceTransactionsJson(), HttpStatus.OK);
    }
}
