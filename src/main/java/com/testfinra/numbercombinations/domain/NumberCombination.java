package com.testfinra.numbercombinations.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberCombination {
    public String value;

    public List<String> combinations;
}
