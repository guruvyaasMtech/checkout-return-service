package com.libManager.checkoutreturnservice.service;


import com.libManager.checkoutreturnservice.model.Checkout;
import com.libManager.checkoutreturnservice.repository.CheckoutRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutReturnService {

    @Autowired
    private CheckoutRepository checkoutRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final String BOOK_EXCHANGE = "book.exchange";

    private final String BOOK_UPDATED_ROUTING_KEY = "book.updated";
    
    @Transactional
    public void handleBookAction(Long bookId, Long memberId, String action) {
        // Basic validation
        if (bookId == null || memberId == null || action == null || (!action.equals("checkout") && !action.equals("return"))) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        LocalDate checkoutDate = LocalDate.now();
        Checkout checkout = new Checkout(bookId, memberId, action, checkoutDate);
        checkoutRepository.save(checkout);

        String message = bookId + "/" + action + "/" + memberId + "/" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        rabbitTemplate.convertAndSend(BOOK_EXCHANGE,BOOK_UPDATED_ROUTING_KEY , message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }

	public List<Checkout> getAllCheckouts() {
		// TODO Auto-generated method stub
		return checkoutRepository.findAll();
	}
}