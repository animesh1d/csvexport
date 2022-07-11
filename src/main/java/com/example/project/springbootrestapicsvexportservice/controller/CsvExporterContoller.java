package com.example.project.springbootrestapicsvexportservice.controller;



import com.example.project.springbootrestapicsvexportservice.service.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class CsvExporterContoller {

    @Autowired
    private CsvExportService csvExportService;


    @GetMapping(value = "/transactions/export", produces = "text/csv")
    public void writeToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"transactions.csv\"");
        csvExportService.writeTransactionsToCsv(response.getWriter());
    }

}
