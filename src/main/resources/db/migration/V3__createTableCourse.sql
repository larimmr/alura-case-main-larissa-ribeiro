CREATE TABLE Course (
    id             BIGINT(20) NOT NULL AUTO_INCREMENT,
    createdAt      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name           VARCHAR(100) NOT NULL,
    code           VARCHAR(50) NOT NULL,
    instructor     VARCHAR(100),
    description    TEXT,
    status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    inactivatedAt  DATETIME DEFAULT NULL,
    category_id    BIGINT(20), 
    PRIMARY KEY (id),
    CONSTRAINT uq_course_code UNIQUE (code),
    CONSTRAINT chk_course_code_length CHECK (CHAR_LENGTH(code) BETWEEN 4 AND 10),
    CONSTRAINT fk_course_category FOREIGN KEY (category_id)
        REFERENCES Category(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
