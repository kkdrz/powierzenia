package edu.psi.powierzenia.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CourseClassMapperTest {

    private CourseClassMapper courseClassMapper;

    @BeforeEach
    public void setUp() {
        courseClassMapper = new CourseClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(courseClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(courseClassMapper.fromId(null)).isNull();
    }
}
