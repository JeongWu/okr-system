package com.eximbay.okr.model.company;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CompanyUpdateFormModel {
    @NotBlank
    private String name;
    @NotBlank
    private String localName;
    private String domain;
    private String logo;
    private MultipartFile imageFile;
    @NotNull
    private boolean googleSignIn;
}
