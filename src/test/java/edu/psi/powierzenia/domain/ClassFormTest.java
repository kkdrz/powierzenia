package edu.psi.powierzenia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import edu.psi.powierzenia.web.rest.TestUtil;

public class ClassFormTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassForm.class);
        ClassForm classForm1 = new ClassForm();
        classForm1.setId(1L);
        ClassForm classForm2 = new ClassForm();
        classForm2.setId(classForm1.getId());
        assertThat(classForm1).isEqualTo(classForm2);
        classForm2.setId(2L);
        assertThat(classForm1).isNotEqualTo(classForm2);
        classForm1.setId(null);
        assertThat(classForm1).isNotEqualTo(classForm2);
    }
}
