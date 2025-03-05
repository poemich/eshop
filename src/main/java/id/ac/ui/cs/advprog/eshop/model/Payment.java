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
    }

    public void setStatus(String status) {
    }
}
