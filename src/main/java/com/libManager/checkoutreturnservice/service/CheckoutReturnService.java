package com.libManager.checkoutreturnservice.service;

import com.libManager.checkoutreturnservice.integration.InventoryItemInfo;
import com.libManager.checkoutreturnservice.integration.InventoryServiceClient;
import com.libManager.checkoutreturnservice.integration.MemberInfo;
import com.libManager.checkoutreturnservice.integration.MemberServiceClient;
import com.libManager.checkoutreturnservice.model.Checkout;
import com.libManager.checkoutreturnservice.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutReturnService {

    private final CheckoutRepository checkoutRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final MemberServiceClient memberServiceClient;

    @Autowired
    public CheckoutReturnService(CheckoutRepository checkoutRepository,
                                 InventoryServiceClient inventoryServiceClient,
                                 MemberServiceClient memberServiceClient) {
        this.checkoutRepository = checkoutRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.memberServiceClient = memberServiceClient;
    }

    public List<Checkout> getAllCheckouts() {
        return checkoutRepository.findAll();
    }

    public Optional<Checkout> getCheckoutById(Long id) {
        return checkoutRepository.findById(id);
    }

    public List<Checkout> getActiveCheckoutsForMember(Long memberId) {
        return checkoutRepository.findByMemberIdAndReturnDateIsNull(memberId);
    }

    public Optional<Checkout> getActiveCheckoutByItemCode(String itemCode) {
        return checkoutRepository.findByItemCodeAndReturnDateIsNull(itemCode);
    }

    @Transactional
    public Checkout checkoutItem(Long memberId, String itemCode) {
        MemberInfo member = memberServiceClient.getMemberById(memberId);
        InventoryItemInfo item = inventoryServiceClient.getItemByItemCode(itemCode);

        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + memberId + " not found.");
        }
        if (item == null) {
            throw new IllegalArgumentException("Inventory item with code " + itemCode + " not found.");
        }
        if (!"AVAILABLE".equals(item.getStatus())) {
            throw new IllegalStateException("Inventory item with code " + itemCode + " is not available.");
        }
        if (checkoutRepository.findByItemCodeAndReturnDateIsNull(itemCode).isPresent()) {
            throw new IllegalStateException("Item " + itemCode + " is already checked out.");
        }

        LocalDate checkoutDate = LocalDate.now();
        LocalDate returnDueDate = checkoutDate.plusWeeks(2); // Example: 2-week loan period

        Checkout checkout = new Checkout(memberId, itemCode, checkoutDate, returnDueDate);
        checkoutRepository.save(checkout);

        inventoryServiceClient.updateItemStatus(itemCode, "CHECKED_OUT");
        return checkout;
    }

    @Transactional
    public Checkout returnItem(String itemCode) {
        Optional<Checkout> activeCheckoutOptional = checkoutRepository.findByItemCodeAndReturnDateIsNull(itemCode);
        if (activeCheckoutOptional.isEmpty()) {
            throw new IllegalArgumentException("Item " + itemCode + " is not currently checked out.");
        }

        Checkout checkout = activeCheckoutOptional.get();
        checkout.setReturnDate(LocalDate.now());
        checkoutRepository.save(checkout);

        inventoryServiceClient.updateItemStatus(itemCode, "AVAILABLE");
        return checkout;
    }
}