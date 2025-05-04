package com.libManager.checkoutreturnservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Checkout {   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long memberId;
    private String action;
    private LocalDate checkoutDate;

    public Checkout() {
    }

    public Checkout(Long bookId, Long memberId, String action, LocalDate checkoutDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.action = action;
        this.checkoutDate = checkoutDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}