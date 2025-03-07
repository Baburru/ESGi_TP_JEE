CREATE TABLE matches (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         equipe1 VARCHAR(255) NOT NULL,
                         equipe2 VARCHAR(255) NOT NULL,
                         scoreEquipe1 INT NOT NULL DEFAULT 0,
                         scoreEquipe2 INT NOT NULL DEFAULT 0,
                         estTermine BOOLEAN NOT NULL DEFAULT false,
                         date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         photoUrl VARCHAR(255) NOT NULL DEFAULT ''
);
