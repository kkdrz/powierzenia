package edu.psi.powierzenia.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EducationPlanMapperTest {

    private EducationPlanMapper educationPlanMapper;

    @BeforeEach
    public void setUp() {
        educationPlanMapper = new EducationPlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(educationPlanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(educationPlanMapper.fromId(null)).isNull();
    }
}
