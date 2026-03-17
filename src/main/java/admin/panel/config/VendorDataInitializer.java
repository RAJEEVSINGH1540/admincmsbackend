package admin.panel.config;

import admin.panel.entity.vendor.VendorFormField;
import admin.panel.repository.vendor.VendorFormFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VendorDataInitializer implements CommandLineRunner {

    private final VendorFormFieldRepository formFieldRepository;

    @Override
    public void run(String... args) {
        initializeVendorFormFields();
    }

    private void initializeVendorFormFields() {
        if (formFieldRepository.count() > 0) {
            log.info("Vendor form fields already initialized. Skipping.");
            return;
        }

        List<VendorFormField> defaultFields = List.of(
                VendorFormField.builder()
                        .fieldKey("gst_number")
                        .fieldLabel("GST Number")
                        .fieldType("text")
                        .placeholder("Enter GST Number")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(1)
                        .validationPattern("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
                        .validationMessage("Please enter a valid 15-character GST number")
                        .minLength(15)
                        .maxLength(15)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("pan_number")
                        .fieldLabel("PAN Number")
                        .fieldType("text")
                        .placeholder("Enter PAN Number")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(2)
                        .validationPattern("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
                        .validationMessage("Please enter a valid 10-character PAN number")
                        .minLength(10)
                        .maxLength(10)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("company_address")
                        .fieldLabel("Company Address")
                        .fieldType("textarea")
                        .placeholder("Enter complete company address")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(3)
                        .minLength(10)
                        .maxLength(500)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("city")
                        .fieldLabel("City")
                        .fieldType("text")
                        .placeholder("Enter City")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(4)
                        .minLength(2)
                        .maxLength(100)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("state")
                        .fieldLabel("State")
                        .fieldType("select")
                        .placeholder("Select State")
                        .options("[\"Andhra Pradesh\",\"Arunachal Pradesh\",\"Assam\",\"Bihar\",\"Chhattisgarh\",\"Goa\",\"Gujarat\",\"Haryana\",\"Himachal Pradesh\",\"Jharkhand\",\"Karnataka\",\"Kerala\",\"Madhya Pradesh\",\"Maharashtra\",\"Manipur\",\"Meghalaya\",\"Mizoram\",\"Nagaland\",\"Odisha\",\"Punjab\",\"Rajasthan\",\"Sikkim\",\"Tamil Nadu\",\"Telangana\",\"Tripura\",\"Uttar Pradesh\",\"Uttarakhand\",\"West Bengal\",\"Delhi\",\"Jammu and Kashmir\",\"Ladakh\"]")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(5)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("pincode")
                        .fieldLabel("Pincode")
                        .fieldType("text")
                        .placeholder("Enter Pincode")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(6)
                        .validationPattern("^[1-9][0-9]{5}$")
                        .validationMessage("Please enter a valid 6-digit pincode")
                        .minLength(6)
                        .maxLength(6)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("business_type")
                        .fieldLabel("Business Type")
                        .fieldType("select")
                        .placeholder("Select Business Type")
                        .options("[\"Manufacturer\",\"Distributor\",\"Wholesaler\",\"Retailer\",\"Service Provider\",\"Contractor\",\"Consultant\",\"Other\"]")
                        .isRequired(true)
                        .isActive(true)
                        .displayOrder(7)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("annual_turnover")
                        .fieldLabel("Annual Turnover (INR)")
                        .fieldType("select")
                        .placeholder("Select Annual Turnover")
                        .options("[\"Below 10 Lakhs\",\"10 Lakhs - 50 Lakhs\",\"50 Lakhs - 1 Crore\",\"1 Crore - 5 Crore\",\"5 Crore - 10 Crore\",\"Above 10 Crore\"]")
                        .isRequired(false)
                        .isActive(true)
                        .displayOrder(8)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("website")
                        .fieldLabel("Website URL")
                        .fieldType("text")
                        .placeholder("Enter website URL (optional)")
                        .isRequired(false)
                        .isActive(false)
                        .displayOrder(9)
                        .build(),

                VendorFormField.builder()
                        .fieldKey("years_in_business")
                        .fieldLabel("Years in Business")
                        .fieldType("number")
                        .placeholder("Enter years")
                        .isRequired(false)
                        .isActive(true)
                        .displayOrder(10)
                        .minLength(1)
                        .maxLength(3)
                        .build()
        );

        formFieldRepository.saveAll(defaultFields);
        log.info("✅ Initialized {} default vendor form fields", defaultFields.size());
    }
}