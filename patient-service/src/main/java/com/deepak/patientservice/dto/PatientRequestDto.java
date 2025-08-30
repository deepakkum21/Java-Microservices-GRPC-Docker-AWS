package com.deepak.patientservice.dto;

import com.deepak.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDto {
    @NotBlank(message = "Name is required")// belongs to Default group automatically
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")// belongs to Default group automatically
    @Email(message = "Email should be valid")// belongs to Default group automatically
    private String email;

    @NotBlank(message = "Address is required")// belongs to Default group automatically
    private String address;

    @NotBlank(message = "Date of birth is required")// belongs to Default group automatically
    private String dateOfBirth;

    @NotBlank(message = "Registered date is required", groups = {CreatePatientValidationGroup.class})
    private String registeredDate;
}
