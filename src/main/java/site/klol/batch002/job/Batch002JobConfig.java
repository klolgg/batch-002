package site.klol.batch002.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Batch002JobConfig {
    public static final String JOB_NAME = "batch002Job";
    private final JobRepository jobRepository;
    @Bean
    public Job batch002Job(
        Step renewSummonerStep) {
        log.info("Initializing {} job", JOB_NAME);

        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(renewSummonerStep)
            .build();
    }
    /*
        소환사 정보 업데이트 배치

        UpdateSummonerJob
        step01

        read summoner
        processor request riot api(N개) and update summoner info
        write summoner

        1) puuid로 소환사 id, tag 변경 업데이트
        2) 각종 정보 업데이트
            - LP, tier
            - 소환사 이미지 아이콘 url
            - 소환사 레벨

     */
}
