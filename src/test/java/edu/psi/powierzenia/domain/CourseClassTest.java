package edu.psi.powierzenia.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import edu.psi.powierzenia.web.rest.TestUtil;

public class CourseClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseClass.class);
        CourseClass courseClass1 = new CourseClass();
        courseClass1.setId(1L);
        CourseClass courseClass2 = new CourseClass();
        courseClass2.setId(courseClass1.getId());
        assertThat(courseClass1).isEqualTo(courseClass2);
        courseClass2.setId(2L);
        assertThat(courseClass1).isNotEqualTo(courseClass2);
        courseClass1.setId(null);
        assertThat(courseClass1).isNotEqualTo(courseClass2);
    }
}
