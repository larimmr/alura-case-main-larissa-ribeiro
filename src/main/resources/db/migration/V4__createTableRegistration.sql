CREATE TABLE Registration  (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_id BIGINT(20) NOT NULL,
    course_id BIGINT(20) NOT NULL,
    registration_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_user_course UNIQUE (user_id, course_id),
    CONSTRAINT fk_registration_user FOREIGN KEY (user_id)
        REFERENCES User(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_registration_course FOREIGN KEY (course_id)
        REFERENCES Course(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;