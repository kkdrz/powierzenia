package edu.psi.powierzenia.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.hibernate.cache.jcache.ConfigSettings;

import java.util.concurrent.TimeUnit;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();
        Config config = new Config();
        config.useSingleServer().setAddress(jHipsterProperties.getCache().getRedis().getServer());
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration())));
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, edu.psi.powierzenia.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.User.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Teacher.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Teacher.class.getName() + ".entrustments", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Teacher.class.getName() + ".allowedClassForms", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Teacher.class.getName() + ".knowledgeAreas", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Teacher.class.getName() + ".preferedCourses", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Entrustment.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.CourseClass.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.CourseClass.class.getName() + ".entrustments", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Course.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Course.class.getName() + ".classes", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Course.class.getName() + ".tags", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.Course.class.getName() + ".teachersThatPreferThisCourses", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.EducationPlan.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.EducationPlan.class.getName() + ".entrustmentPlans", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.EducationPlan.class.getName() + ".courses", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.EntrustmentPlan.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.EntrustmentPlan.class.getName() + ".entrustments", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.FieldOfStudy.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.FieldOfStudy.class.getName() + ".educationPlans", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.KnowledgeArea.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.KnowledgeArea.class.getName() + ".teachersWithThisKnowledgeAreas", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.KnowledgeArea.class.getName() + ".coursesWithThisKnowledgeAreas", jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.ClassForm.class.getName(), jcacheConfiguration);
            createCache(cm, edu.psi.powierzenia.domain.ClassForm.class.getName() + ".teachersAllowedToTeachThisForms", jcacheConfiguration);
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName, javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
