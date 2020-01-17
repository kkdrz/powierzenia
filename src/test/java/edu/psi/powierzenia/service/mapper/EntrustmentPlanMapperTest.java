package edu.psi.powierzenia.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EntrustmentPlanMapperTest {

    private EntrustmentPlanMapper entrustmentPlanMapper;

    @BeforeEach
    public void setUp() {
        entrustmentPlanMapper = new EntrustmentPlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(entrustmentPlanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(entrustmentPlanMapper.fromId(null)).isNull();
    }
}
