package edu.psi.powierzenia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import edu.psi.powierzenia.web.rest.TestUtil;

public class EntrustmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrustment.class);
        Entrustment entrustment1 = new Entrustment();
        entrustment1.setId(1L);
        Entrustment entrustment2 = new Entrustment();
        entrustment2.setId(entrustment1.getId());
        assertThat(entrustment1).isEqualTo(entrustment2);
        entrustment2.setId(2L);
        assertThat(entrustment1).isNotEqualTo(entrustment2);
        entrustment1.setId(null);
        assertThat(entrustment1).isNotEqualTo(entrustment2);
    }
}
