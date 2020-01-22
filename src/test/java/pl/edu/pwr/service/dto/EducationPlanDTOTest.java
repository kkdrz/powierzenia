package pl.edu.pwr.service.dto;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class EducationPlanDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationPlanDTO.class);
        EducationPlanDTO educationPlanDTO1 = new EducationPlanDTO();
        educationPlanDTO1.setId(1L);
        EducationPlanDTO educationPlanDTO2 = new EducationPlanDTO();
        assertThat(educationPlanDTO1).isNotEqualTo(educationPlanDTO2);
        educationPlanDTO2.setId(educationPlanDTO1.getId());
        assertThat(educationPlanDTO1).isEqualTo(educationPlanDTO2);
        educationPlanDTO2.setId(2L);
        assertThat(educationPlanDTO1).isNotEqualTo(educationPlanDTO2);
        educationPlanDTO1.setId(null);
        assertThat(educationPlanDTO1).isNotEqualTo(educationPlanDTO2);
    }
}
