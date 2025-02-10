DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS summoner;

CREATE TABLE `user` (
    seq_no BIGINT NOT NULL AUTO_INCREMENT,
    kakao_id VARCHAR(256),
    nickname VARCHAR(50) NOT NULL,
    is_school_verified CHAR(1) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (seq_no),
    CONSTRAINT chk_is_school_verified CHECK (is_school_verified IN ('Y', 'N')),
    CONSTRAINT uq_kakao_id UNIQUE (kakao_id)
);

CREATE TABLE summoner (
    seq_no BIGINT NOT NULL AUTO_INCREMENT,
    user_seq_no BIGINT NOT NULL,
    is_main_summonner CHAR(1) NOT NULL,
    puuid varchar(256) NOT NULL,
    smnr_id VARCHAR(50) NOT NULL,
    smnr_tag VARCHAR(50) NOT NULL,
    smnr_icon VARCHAR(255) DEFAULT 'default_icon_url',
    smnr_level INT DEFAULT 1,
    smnr_tier VARCHAR(20) DEFAULT 'UNRANK',
    smnr_lp INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (seq_no),
    CONSTRAINT fk_user_seq_no
        FOREIGN KEY (user_seq_no)
            REFERENCES `user` (seq_no)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT chk_is_main_summonner
        CHECK (is_main_summonner IN ('Y', 'N')),
    CONSTRAINT uq_puuid UNIQUE (puuid)
);