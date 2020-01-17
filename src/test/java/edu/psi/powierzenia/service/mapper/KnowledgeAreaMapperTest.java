package edu.psi.powierzenia.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class KnowledgeAreaMapperTest {

    private KnowledgeAreaMapper knowledgeAreaMapper;

    @BeforeEach
    public void setUp() {
        knowledgeAreaMapper = new KnowledgeAreaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(knowledgeAreaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(knowledgeAreaMapper.fromId(null)).isNull();
    }
}
