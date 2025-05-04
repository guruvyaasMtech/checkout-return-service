package com.libManager.checkoutreturnservice.repository;

import com.libManager.checkoutreturnservice.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

}