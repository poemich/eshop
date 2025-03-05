package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentByVoucherCodeSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d7ab", "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeRejected() {
        paymentData.put("voucherCode", "INVALIDCODE12345");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d7ac", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByCashOnDeliverySuccess() {
        paymentData.put("address", "Jl. Margonda Raya No. 100");
        paymentData.put("deliveryFee", "20000");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d7ad", "CASH_ON_DELIVERY", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentByCashOnDeliveryRejected() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "20000");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d7ae", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}