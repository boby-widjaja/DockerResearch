package com.basiliskSB.documentation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderHeaderResponse {
    private String invoiceNumber;
    private String customer;
    private String salesman;
    private LocalDate orderDate;
}
