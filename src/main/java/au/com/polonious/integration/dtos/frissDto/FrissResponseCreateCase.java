package au.com.polonious.integration.dtos.frissDto;

import lombok.Data;

@Data public class FrissResponseCreateCase {
    String responseText;
    String referenceNumber;
    Long taskId;
}
