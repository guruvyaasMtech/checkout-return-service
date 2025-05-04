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

	@PostMapping
	public String handleBookAction(@RequestParam("bookId") Long bookId, @RequestParam("memberId") Long memberId,
			@RequestParam("action") String action) {
		try {
			checkoutReturnService.handleBookAction(bookId, memberId, action);
			return "Book action (" + action + ") processed for bookId: " + bookId + ", memberId: " + memberId
					+ ".  Check RabbitMQ Queue.";
		} catch (IllegalArgumentException e) {
			return "Error: " + e.getMessage();
		}
	}
}