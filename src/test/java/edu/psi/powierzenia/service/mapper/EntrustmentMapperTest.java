package edu.psi.powierzenia.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EntrustmentMapperTest {

    private EntrustmentMapper entrustmentMapper;

    @BeforeEach
    public void setUp() {
        entrustmentMapper = new EntrustmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(entrustmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(entrustmentMapper.fromId(null)).isNull();
    }
}
