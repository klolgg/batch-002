package site.klol.batch002.summoner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch002.summoner.entity.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
}
