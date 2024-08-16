package org.example.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.account.dtos.AccountRequest;
import org.example.account.dtos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Account API", description = "")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestParam(required = false) Integer eventId,
            @RequestParam(required = false) Integer id
    ) {
        Set<AccountResponse> accounts = null;

        try {
            if (eventId != null) {
                accounts = accountService.allForEvent(eventId);
            } else if (id != null) {
                return ResponseEntity.ok(accountService.getById(id));
            } else {
                accounts = accountService.all();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<?> createAccounts(@RequestParam(defaultValue = "1") Integer amount, @RequestParam Integer eventId, @RequestBody AccountRequest accountRequest, @RequestHeader HttpHeaders headers) {
        Set<AccountResponse> responses = new HashSet<>();
        try {
            if (amount != 1) {
                responses = accountService.createMany(amount, eventId, accountRequest, headers);
            } else {
                return ResponseEntity.ok(accountService.create(eventId, accountRequest, headers));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        AccountResponse event = null;
        try {
            event = accountService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        try {
            accountService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
