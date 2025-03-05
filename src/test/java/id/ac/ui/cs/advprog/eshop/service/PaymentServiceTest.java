package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;

    @BeforeEach
    void setUp() {
        // Create a dummy Order with one Product.
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-123");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        // Set order status to WAITING_PAYMENT by default.
        order = new Order("order-123", products, System.currentTimeMillis(), "Test Author");
        
        // Stub paymentRepository.save() to simply return the Payment instance.
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAddPayment() {
        Map<String, String> paymentData = new HashMap<>();
        // Valid voucher meets required conditions.
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        
        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        assertNotNull(payment);
        // Since voucher is valid, payment status must be SUCCESS.
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        // Also, order status must be updated instantly to SUCCESS.
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testGetPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment addedPayment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        
        // Stub repository.getPayment() to return the same payment.
        when(paymentRepository.getPayment(addedPayment.getId())).thenReturn(addedPayment);
        
        Payment retrieved = paymentService.getPayment(addedPayment.getId());
        assertNotNull(retrieved);
        assertEquals(addedPayment.getId(), retrieved.getId());
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = paymentService.addPayment(order, "VOUCHER_CODE", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jl. Test No. 1");
        paymentData2.put("deliveryFee", "10000");
        Payment payment2 = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData2);

        List<Payment> allPayments = new ArrayList<>(Arrays.asList(payment1, payment2));
        when(paymentRepository.getAllPayments()).thenReturn(allPayments);
        
        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(2, payments.size());
    }

    @Test
    void testSetStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        
        // Change payment status to REJECTED.
        Payment updated = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), updated.getStatus());
    }

    @Test
    void testOrderStatusUpdateOnPaymentStatusChange() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);
        
        // Initially, order status is SUCCESS.
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        
        // Change payment status to REJECTED; order status should update to FAILED.
        paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }
}
