package org.example.finance;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import org.example.finance.dtos.FinanceRequest;
import org.example.finance.dtos.FinanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/finance")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Finance API", description = "")
public class FinanceController {
    @Autowired
    FinanceService financeService;

    @GetMapping("/{id}")
    public ResponseEntity<FinanceResponse> getById(@PathVariable Integer id) {
        FinanceResponse event = null;
        try {
            event = financeService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<FinanceResponse>> all() {
        return ResponseEntity.ok(financeService.all());
    }

    @PostMapping
    public ResponseEntity<FinanceResponse> createFinance(@RequestParam Integer eventId, @RequestBody FinanceRequest financeRequest, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(financeService.create(eventId, financeRequest, headers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceResponse> updateFinance(@RequestBody FinanceRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        FinanceResponse event = null;
        try {
            event = financeService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinance(@PathVariable Integer id) {
        try {
            financeService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}