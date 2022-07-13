package com.example.project.springbootrestapicsvexportservice.service;

import com.example.project.springbootrestapicsvexportservice.controller.CsvExporterController;
import com.example.project.springbootrestapicsvexportservice.model.TransactionDetailsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CsvExportServiceTest {

    @Mock
    private CsvExportService csvExportService;

    @InjectMocks
    private CsvExporterController csvExporterController;

    private Path workingDir;

    private static List<TransactionDetailsDTO> transactionDetailsDTOList;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(CsvExportServiceTest.class);
        this.workingDir = Path.of("", "src/test/resource");
    }

    @Test
    public void testInputFile() throws IOException {
        Path file = this.workingDir.resolve("Input.txt");
        String content = Files.readString(file);
        Assert.assertNotNull(content);
    }

    @Test
    public void testProduceTransactionsJson() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(csvExportService.produceTransactionsJson()).thenReturn(transactionDetailsDTOList);
        ResponseEntity<List<TransactionDetailsDTO>> responseEntity = csvExporterController.produceTransactionsJson();

        Assert.assertEquals(responseEntity.getStatusCode().value(),200);
        Assert.assertEquals(responseEntity.getBody(),transactionDetailsDTOList);
    }

    @Test(expected = IOException.class)
    public void testWriteTransactionsToCsv() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(csvExportService.produceTransactionsJson()).thenThrow(IOException.class);
        ResponseEntity<List<TransactionDetailsDTO>> responseEntity = csvExporterController.produceTransactionsJson();
        Assert.assertEquals(responseEntity.getStatusCode().value(),500);
    }

    private static void transactionsStub(){
        transactionDetailsDTOList = new ArrayList<>();
        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();
        transactionDetailsDTO.setClient_Information("CL  432100020001");
        transactionDetailsDTO.setProduct_Information("SGX FUNK    20100910");
        transactionDetailsDTO.setTotal_Transaction_Amount("1");
        transactionDetailsDTOList.add(transactionDetailsDTO);
    }

}
