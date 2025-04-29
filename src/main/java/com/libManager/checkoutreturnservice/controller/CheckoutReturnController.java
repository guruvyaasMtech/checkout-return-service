package com.libManager.checkoutreturnservice.controller;

import com.libManager.checkoutreturnservice.model.Checkout;
import com.libManager.checkoutreturnservice.service.CheckoutReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutReturnController {

    private final CheckoutReturnService checkoutReturnService;

    @Autowired
    public CheckoutReturnController(CheckoutReturnService checkoutReturnService) {
        this.checkoutReturnService = checkoutReturnService;
    }

    @GetMapping
    public ResponseEntity<List<Checkout>> getAllCheckouts() {
        List<Checkout> checkouts = checkoutReturnService.getAllCheckouts();
        return new ResponseEntity<>(checkouts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checkout> getCheckoutById(@PathVariable Long id) {
        Optional<Checkout> checkout = checkoutReturnService.getCheckoutById(id);
        return checkout.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<List<Checkout>> getActiveCheckoutsForMember(@PathVariable Long memberId) {
        List<Checkout> activeCheckouts = checkoutReturnService.getActiveCheckoutsForMember(memberId);
        return new ResponseEntity<>(activeCheckouts, HttpStatus.OK);
    }

    @GetMapping("/item/{itemCode}/active")
    public ResponseEntity<Checkout> getActiveCheckoutByItemCode(@PathVariable String itemCode) {
        Optional<Checkout> activeCheckout = checkoutReturnService.getActiveCheckoutByItemCode(itemCode);
        return activeCheckout.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutItem(@RequestParam Long memberId, @RequestParam String itemCode) {
        try {
            Checkout checkout = checkoutReturnService.checkoutItem(memberId, itemCode);
            return new ResponseEntity<>(checkout, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return/{itemCode}")
    public ResponseEntity<?> returnItem(@PathVariable String itemCode) {
        try {
            Checkout returnedCheckout = checkoutReturnService.returnItem(itemCode);
            return new ResponseEntity<>(returnedCheckout, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}