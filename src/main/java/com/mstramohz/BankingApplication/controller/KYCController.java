package com.mstramohz.BankingApplication.controller;

import com.mstramohz.BankingApplication.dto.KycDto;
import com.mstramohz.BankingApplication.dto.Response;
import com.mstramohz.BankingApplication.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kyc")
@RequiredArgsConstructor
public class KYCController {
    private final KYCService kycService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllKYC () {
        return kycService.getAllKYC();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Response> getById (@PathVariable int id) {
        return kycService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createKYC (@RequestBody KycDto dto) {
        return kycService.createKYC(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateKYC (@PathVariable int id, @RequestBody KycDto dto) {
        return kycService.updateKYC(id, dto);
    }
}
