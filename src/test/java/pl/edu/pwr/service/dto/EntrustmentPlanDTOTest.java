package pl.edu.pwr.service.dto;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class EntrustmentPlanDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntrustmentPlanDTO.class);
        EntrustmentPlanDTO entrustmentPlanDTO1 = new EntrustmentPlanDTO();
        entrustmentPlanDTO1.setId(1L);
        EntrustmentPlanDTO entrustmentPlanDTO2 = new EntrustmentPlanDTO();
        assertThat(entrustmentPlanDTO1).isNotEqualTo(entrustmentPlanDTO2);
        entrustmentPlanDTO2.setId(entrustmentPlanDTO1.getId());
        assertThat(entrustmentPlanDTO1).isEqualTo(entrustmentPlanDTO2);
        entrustmentPlanDTO2.setId(2L);
        assertThat(entrustmentPlanDTO1).isNotEqualTo(entrustmentPlanDTO2);
        entrustmentPlanDTO1.setId(null);
        assertThat(entrustmentPlanDTO1).isNotEqualTo(entrustmentPlanDTO2);
    }
}
