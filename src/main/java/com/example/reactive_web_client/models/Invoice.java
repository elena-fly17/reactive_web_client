package com.example.reactive_web_client.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Invoice {

    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String number;

    @NonNull
    private Double amount;

}
