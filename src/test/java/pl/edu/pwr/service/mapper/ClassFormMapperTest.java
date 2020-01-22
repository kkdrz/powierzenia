package pl.edu.pwr.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ClassFormMapperTest {

    private ClassFormMapper classFormMapper;

    @BeforeEach
    public void setUp() {
        classFormMapper = new ClassFormMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(classFormMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classFormMapper.fromId(null)).isNull();
    }
}
