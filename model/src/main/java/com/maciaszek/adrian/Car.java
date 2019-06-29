package com.maciaszek.adrian;

import com.maciaszek.adrian.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private String model;
    private BigDecimal price;
    private Color color;
    private double mileage;
    private List<String> components;
}

