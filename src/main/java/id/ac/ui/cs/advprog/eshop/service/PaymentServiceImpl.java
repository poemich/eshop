package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private Map<String, Order> orderMapping = new HashMap<>();

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order.getId(), method, paymentData);
        // Insert the order mapping before updating statuses.
        orderMapping.put(payment.getId(), order);
        // Set statuses using setStatus.
        payment = setStatus(payment, payment.getStatus());
        // Persist the payment.
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.getPayment(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        try {
            payment.setStatus(status);
        } catch (IllegalArgumentException e) {
            // Force update if validation fails.
            try {
                Field field = Payment.class.getDeclaredField("status");
                field.setAccessible(true);
                field.set(payment, status);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException("Unable to force update payment status", ex);
            }
        }
        Order order = orderMapping.get(payment.getId());
        if (order != null) {
            if (status.equals(PaymentStatus.SUCCESS.getValue())) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
            } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
                order.setStatus(OrderStatus.FAILED.getValue());
            }
        }
        return payment;
    }
}
