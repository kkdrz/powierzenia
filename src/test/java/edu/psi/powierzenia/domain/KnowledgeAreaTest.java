package edu.psi.powierzenia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import edu.psi.powierzenia.web.rest.TestUtil;

public class KnowledgeAreaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KnowledgeArea.class);
        KnowledgeArea knowledgeArea1 = new KnowledgeArea();
        knowledgeArea1.setId(1L);
        KnowledgeArea knowledgeArea2 = new KnowledgeArea();
        knowledgeArea2.setId(knowledgeArea1.getId());
        assertThat(knowledgeArea1).isEqualTo(knowledgeArea2);
        knowledgeArea2.setId(2L);
        assertThat(knowledgeArea1).isNotEqualTo(knowledgeArea2);
        knowledgeArea1.setId(null);
        assertThat(knowledgeArea1).isNotEqualTo(knowledgeArea2);
    }
}
