package au.com.polonious.integration.dtos.frissDto.objectDto;

public enum ObjectType {
    Unknown (0),
    Address (1),
    BankAccount(2),
    Contact (3),
    IdentityCard (4),
    LegalPerson (5),
    NaturalPerson (6),
    Vehicle (7),
    Screening (8),
    Claim (9),
    Policy (10),
    Device (11),
    LegalEntity (12),
    Realty (13),
    Note (14),
    RentalTicket (15);

    private int objectTypeId;
    ObjectType(int i){
        this.objectTypeId = i;
    }

    public static ObjectType fromInteger(int x) {
        switch(x) {
            case 0:
                return Unknown;
            case 1:
                return Address;
            case 2:
                return BankAccount;
            case 3:
                return Contact;
            case 4:
                return IdentityCard;
            case 5:
                return LegalPerson;
            case 6:
                return NaturalPerson;
            case 7:
                return Vehicle;
            case 8:
                return Screening;
            case 9:
                return Claim;
            case 10:
                return Policy;
            case 11:
                return Device;
            case 12:
                return LegalEntity;
            case 13:
                return Realty;
            case 14:
                return Note;
            case 15:
                return RentalTicket;
        }
        return null;
    }
}
