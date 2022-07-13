package com.example.project.springbootrestapicsvexportservice.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionDetailsDTO implements Serializable {

    private String Client_Information;
    private String Product_Information;
    private String Total_Transaction_Amount;
}
