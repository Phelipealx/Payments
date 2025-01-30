package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public Page<PaymentDto> getAllPayments(@PageableDefault(size = 10) Pageable pageable) {
        return paymentService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable @NotNull Long id) {
        PaymentDto paymentDto = paymentService.getById(id);

        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        URI uri = uriBuilder.path("/payments/{id}").buildAndExpand(createdPayment.getId()).toUri();

        return ResponseEntity.created(uri).body(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> editPayment(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto updatedPayment = paymentService.editPayment(id, paymentDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}