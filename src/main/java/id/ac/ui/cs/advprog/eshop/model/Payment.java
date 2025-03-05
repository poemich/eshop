package id.ac.ui.cs.advprog.eshop.model;

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
    @Setter
    private String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        switch (method) {
            case "VOUCHER_CODE":
                String voucher = paymentData.get("voucherCode");
                if (voucher != null && voucher.length() == 16 
                        && voucher.startsWith("ESHOP") 
                        && countDigits(voucher) == 8) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }
                break;
            case "CASH_ON_DELIVERY":
                String address = paymentData.get("address");
                String deliveryFee = paymentData.get("deliveryFee");
                if (address != null && !address.isEmpty() 
                        && deliveryFee != null && !deliveryFee.isEmpty()) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }
                break;
            default:
                this.status = "PENDING";
                break;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper method: counts digit characters in the given string.
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
