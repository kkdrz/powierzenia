package pl.edu.pwr.service.dto;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class EntrustmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntrustmentDTO.class);
        EntrustmentDTO entrustmentDTO1 = new EntrustmentDTO();
        entrustmentDTO1.setId(1L);
        EntrustmentDTO entrustmentDTO2 = new EntrustmentDTO();
        assertThat(entrustmentDTO1).isNotEqualTo(entrustmentDTO2);
        entrustmentDTO2.setId(entrustmentDTO1.getId());
        assertThat(entrustmentDTO1).isEqualTo(entrustmentDTO2);
        entrustmentDTO2.setId(2L);
        assertThat(entrustmentDTO1).isNotEqualTo(entrustmentDTO2);
        entrustmentDTO1.setId(null);
        assertThat(entrustmentDTO1).isNotEqualTo(entrustmentDTO2);
    }
}
