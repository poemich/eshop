package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveAndGetPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d7ab", "VOUCHER_CODE", paymentData);
        
        paymentRepository.save(payment);
        Payment retrieved = paymentRepository.getPayment(payment.getId());
        assertNotNull(retrieved);
        assertEquals(payment.getId(), retrieved.getId());
        assertEquals(payment.getStatus(), retrieved.getStatus());
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d7ab", "VOUCHER_CODE", paymentData1);
        
        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jl. Margonda Raya No. 100");
        paymentData2.put("deliveryFee", "20000");
        Payment payment2 = new Payment("13652556-012a-4c07-b546-54eb1396d7ac", "CASH_ON_DELIVERY", paymentData2);
        
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        
        List<Payment> payments = paymentRepository.getAllPayments();
        assertEquals(2, payments.size());
    }
}