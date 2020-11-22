package au.com.polonious.integration.dtos.referralDto;

import lombok.Data;

import java.util.List;

@Data
public class ReferralInquiryRequest {
    private Credential credential;
    private List<ReferralInquiry> arrayList;
    private String request_id;

    public static class Credential{
        String username;
    }
    @Data
    public static class ReferralInquiry{
        String exposure_number;
        String special_program;
    }
}
