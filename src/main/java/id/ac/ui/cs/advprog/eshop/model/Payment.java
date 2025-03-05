package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private Map<String, String> paymentData;
    private String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = evaluateStatus(method, paymentData);
    }

    private String evaluateStatus(String method, Map<String, String> data) {
        switch (method) {
            case "VOUCHER_CODE":
                return isValidVoucher(data.get("voucherCode")) ? 
                        PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
            case "CASH_ON_DELIVERY":
                return isValidCashOnDelivery(data) ? 
                        PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
            default:
                throw new IllegalArgumentException("Invalid payment method");
        }
    }

    private boolean isValidVoucher(String voucher) {
        return voucher != null 
                && voucher.length() == 16 
                && voucher.startsWith("ESHOP") 
                && countDigits(voucher) == 8;
    }

    private boolean isValidCashOnDelivery(Map<String, String> data) {
        String address = data.get("address");
        String deliveryFee = data.get("deliveryFee");
        return address != null && !address.isEmpty() 
                && deliveryFee != null && !deliveryFee.isEmpty();
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // Helper method: counts digit characters in a string.
    private int countDigits(String str) {
        int count = 0;
        for (char ch : str.toCharArray()) {
            if (Character.isDigit(ch)) {
                count++;
            }
        }
        return count;
    }
}
