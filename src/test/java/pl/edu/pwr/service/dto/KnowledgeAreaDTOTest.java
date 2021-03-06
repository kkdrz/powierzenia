package pl.edu.pwr.service.dto;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class KnowledgeAreaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KnowledgeAreaDTO.class);
        KnowledgeAreaDTO knowledgeAreaDTO1 = new KnowledgeAreaDTO();
        knowledgeAreaDTO1.setId(1L);
        KnowledgeAreaDTO knowledgeAreaDTO2 = new KnowledgeAreaDTO();
        assertThat(knowledgeAreaDTO1).isNotEqualTo(knowledgeAreaDTO2);
        knowledgeAreaDTO2.setId(knowledgeAreaDTO1.getId());
        assertThat(knowledgeAreaDTO1).isEqualTo(knowledgeAreaDTO2);
        knowledgeAreaDTO2.setId(2L);
        assertThat(knowledgeAreaDTO1).isNotEqualTo(knowledgeAreaDTO2);
        knowledgeAreaDTO1.setId(null);
        assertThat(knowledgeAreaDTO1).isNotEqualTo(knowledgeAreaDTO2);
    }
}
