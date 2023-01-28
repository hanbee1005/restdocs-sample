package com.example.restdocssample.api.controller;

import com.example.restdocssample.common.EnumType;
import com.example.restdocssample.members.constants.Address;
import com.example.restdocssample.members.constants.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/codes")
public class EnumViewController {
    @GetMapping
    public ResponseEntity<Map<String, Map<String, String>>> findAll() {
        Map<String, String> gender = getDocs(Gender.values());
        Map<String, String> address = getDocs(Address.values());

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("genders", gender);
        result.put("address", address);

        return ResponseEntity.ok(result);
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getCode, EnumType::getDesc));
    }
}
