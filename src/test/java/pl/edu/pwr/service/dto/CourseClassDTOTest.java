package pl.edu.pwr.service.dto;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseClassDTO.class);
        CourseClassDTO courseClassDTO1 = new CourseClassDTO();
        courseClassDTO1.setId(1L);
        CourseClassDTO courseClassDTO2 = new CourseClassDTO();
        assertThat(courseClassDTO1).isNotEqualTo(courseClassDTO2);
        courseClassDTO2.setId(courseClassDTO1.getId());
        assertThat(courseClassDTO1).isEqualTo(courseClassDTO2);
        courseClassDTO2.setId(2L);
        assertThat(courseClassDTO1).isNotEqualTo(courseClassDTO2);
        courseClassDTO1.setId(null);
        assertThat(courseClassDTO1).isNotEqualTo(courseClassDTO2);
    }
}
