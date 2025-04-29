package com.libManager.checkoutreturnservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String itemCode;

    @Column(nullable = false)
    private LocalDate checkoutDate;

    private LocalDate returnDueDate;

    private LocalDate returnDate;

    // Default constructor
    public Checkout() {
    }

    public Checkout(Long memberId, String itemCode, LocalDate checkoutDate, LocalDate returnDueDate) {
        this.memberId = memberId;
        this.itemCode = itemCode;
        this.checkoutDate = checkoutDate;
        this.returnDueDate = returnDueDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getReturnDueDate() {
        return returnDueDate;
    }

    public void setReturnDueDate(LocalDate returnDueDate) {
        this.returnDueDate = returnDueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}