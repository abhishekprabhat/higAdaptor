package au.com.polonious.integration.dtos.referralDto.ecosUpdate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferralDecision{
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String decision;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String decision_date;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String decision_operator;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String siu_case_number;
    @JacksonXmlProperty(namespace = "urn:com.thehartford.claims.siu.referralstatus.types.2007.12")
    String investigator_assigned;
}
