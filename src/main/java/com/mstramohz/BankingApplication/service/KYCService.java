package com.mstramohz.BankingApplication.service;

import com.mstramohz.BankingApplication.dto.KycDto;
import com.mstramohz.BankingApplication.dto.Response;
import com.mstramohz.BankingApplication.entity.KnowYourCustomer;
import com.mstramohz.BankingApplication.repository.KYCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KYCService {
    private final KYCRepository kycRepository;

    public ResponseEntity<Response> getAllKYC () {
        List<KnowYourCustomer> kyc = kycRepository.findAll();

        Map<String, Object> map = new HashMap<>();
        map.put("kyc", kyc);
        Response response = new Response(true, "Success.", map);
        return ResponseEntity.ok(response);
    }

    public  ResponseEntity<Response> getById (int id) {
        KnowYourCustomer kyc = kycRepository.findById(id).get();

        Map<String, Object> map = new HashMap<>();
        map.put("kyc", kyc);
        Response response = new Response(true, "Success.", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> createKYC (KycDto dto) {
        KnowYourCustomer kyc = new KnowYourCustomer(
                dto.getAddress(),
                dto.getBvn(),
                dto.getNin(),
                dto.getResidentLga(),
                dto.getResidentState(),
                dto.getDob(),
                dto.getNextOfKin(),
                dto.getUser()
        );
        kycRepository.save(kyc);

        Map<String, Object> map = new HashMap<>();
        map.put("kyc", kyc);
        Response response = new Response(true, "Success.", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> updateKYC (int id, KycDto dto) {
        KnowYourCustomer kyc = kycRepository.findById(id).get();
        kyc.setAddress(dto.getAddress());
        kyc.setDob(dto.getDob());
        kyc.setNextOfKin(dto.getNextOfKin());
        kyc.setResidentLga(dto.getResidentLga());
        kyc.setResidentState(dto.getResidentState());
        kycRepository.save(kyc);

        Map<String, Object> map = new HashMap<>();
        map.put("kyc", kyc);
        Response response = new Response(true, "Success.", map);
        return ResponseEntity.ok(response);
    }
}
