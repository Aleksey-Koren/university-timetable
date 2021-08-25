DROP TABLE IF EXISTS group_course;
DROP TABLE IF EXISTS lecture_group;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS group_table;
DROP TABLE IF EXISTS audience;

CREATE TABLE audience
(
    id SERIAL PRIMARY KEY,
    room_number INT UNIQUE NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE group_table
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL,
    year VARCHAR(15) NOT NULL
);

CREATE TABLE course
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL,
    description VARCHAR(1000) NOT NULL
);

CREATE TABLE student
(
    id SERIAL PRIMARY KEY,
    group_id INT REFERENCES group_table(id) ON DELETE SET NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL
);

CREATE TABLE teacher
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL
);

CREATE TABLE lecture
(
    id SERIAL PRIMARY KEY,
    course_id INT REFERENCES course(id) ON DELETE CASCADE,
    teacher_id INT REFERENCES teacher(id) ON DELETE SET NULL,
    audience_id INT REFERENCES audience(id) ON DELETE SET NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CHECK (start_time < end_time)
);

CREATE TABLE lecture_group
(
    lecture_id INT REFERENCES lecture(id) ON DELETE CASCADE,
    group_id INT REFERENCES group_table(id) ON DELETE CASCADE,
    UNIQUE (lecture_id, group_id)
);

CREATE TABLE group_course
(
    group_id INT REFERENCES group_table(id) ON DELETE CASCADE,
    course_id INT REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE (group_id, course_id)
);

INSERT INTO audience (room_number, capacity)
VALUES
    (10, 30),
    (12, 30),
    (102, 150);

INSERT INTO group_table (name, year)
VALUES
    ('group_name1', 'FIRST'),
    ('group_name2', 'SECOND'),
    ('group_name3', 'THIRD'),
    ('group_name4', 'FOURTH');

INSERT INTO course (name, description)
VALUES
    ('course_name1', 'description1'),
    ('course_name2', 'description2'),
    ('course_name3', 'description3'),
    ('course_name4', 'description4');

INSERT INTO student (group_id, first_name, last_name)
VALUES
    (1, 'first name 1', 'last name 1'),
    (2, 'first name 2', 'last name 2'),
    (3, 'first name 3', 'last name 3'),
    (4, 'first name 4', 'last name 4'),
    (1, 'first name 5', 'last name 5');

INSERT INTO teacher (first_name, last_name)
VALUES
    ('first name 1', 'last name 1'),
    ('first name 2', 'last name 2'),
    ('first name 3', 'last name 3'),
    ('first name 4', 'last name 4'),
    ('first name 5', 'last name 5');

INSERT INTO lecture (course_id, teacher_id, audience_id, start_time, end_time)
VALUES
    (1, 1, 2, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),
    (3, 4, 1, '2021-05-02 16:00:00', '2021-05-02 17:00:00'),
    (2, 3, 3, '2021-05-02 10:00:00', '2021-05-02 11:00:00'),
    (1, 1, 2, '2021-05-03 16:00:00', '2021-05-03 17:00:00'),
    (3, 4, 1, '2021-05-03 17:00:00', '2021-05-03 18:00:00'),
    (2, 3, 3, '2021-05-03 08:00:00', '2021-05-03 09:00:00'),
    (1, 1, 2, '2021-05-04 14:00:00', '2021-05-04 15:00:00');

INSERT INTO lecture_group(lecture_id, group_id)
VALUES
    (1,2),
    (2,3),
    (4,2),
    (3,2),
    (3,3);




