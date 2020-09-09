package au.com.polonious.integration.controller;

import lombok.Data;

import java.util.List;

@Data
public class UserData {
    List<XmlInput> inputList;
    private String name;
    private String email;
    private String gender;
    private String password;
    private String profession;
    private String note;
    private boolean married;
}
