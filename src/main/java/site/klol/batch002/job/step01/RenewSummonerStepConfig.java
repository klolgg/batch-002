package site.klol.batch002.job.step01;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch002.common.exception.NoSkipException;
import site.klol.batch002.riot.service.V1RiotAPIService;
import site.klol.batch002.summoner.entity.Summoner;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RenewSummonerStepConfig {
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final V1RiotAPIService v1RiotAPIService;

    private static final int CHUNK_SIZE = 10;
    private static final String QUERY_STRING = "SELECT s FROM Summoner s";
    public static final String STEP_NAME = "renewSummonerStep";
    public static final String READER_NAME = "summonerJpaPagingItemReader";
    @Bean
    @JobScope
    public Step renewSummonerStep(PlatformTransactionManager transactionManager, StepExecutionListener batchTerminationStepListener) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Summoner, Summoner>chunk(CHUNK_SIZE, transactionManager)
            .reader(summonerJpaPagingItemReader())
            .processor(fetchAndUpdateSummonerProcessor())
            .writer(summonerWriter())
            .faultTolerant()
            .noSkip(NoSkipException.class)
            .listener(batchTerminationStepListener)
            .build();
    }


    @Bean
    @StepScope
    public JpaPagingItemReader<Summoner> summonerJpaPagingItemReader() {
        log.debug("Initializing summoner paging item reader");
        return new JpaPagingItemReaderBuilder<Summoner>()
            .name(READER_NAME)
            .entityManagerFactory(emf)
            .pageSize(CHUNK_SIZE)
            .queryString(QUERY_STRING)
            .build();
    }


    @Bean
    @StepScope
    public ItemProcessor<? super Summoner, ? extends Summoner> fetchAndUpdateSummonerProcessor() {
        log.debug("Initializing fetch and update summoner processor");
        return summoner -> {
            // api 1. 소환사 티어, 레벨, LP 정보 업데이트
            // api 2. 소환사 이미지 아이콘 등 업데이트?
            return null;
        };
    }

    @Bean
    @StepScope
    public ItemWriter<? super Summoner> summonerWriter() {
        log.debug("Initializing summoner writer");
        JpaItemWriter<Summoner> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }
}
