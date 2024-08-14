package org.example.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.account.dtos.AccountRequest;
import org.example.account.dtos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Account API", description = "")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Integer id) {
        AccountResponse event = null;
        try {
            event = accountService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<AccountResponse>> all() {
        return ResponseEntity.ok(accountService.all());
    }

    @GetMapping
    public ResponseEntity<Set<AccountResponse>> allForEvent(@RequestParam Integer eventId) {
        return ResponseEntity.ok(accountService.allForEvent(eventId));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestParam Integer eventId, @RequestBody AccountRequest accountRequest, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(accountService.create(eventId, accountRequest, headers));
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
