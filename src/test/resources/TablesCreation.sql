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
    room_number INT NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE group_table
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE course
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(1000) NOT NULL
);

CREATE TABLE student
(
    id SERIAL PRIMARY KEY,
    group_id INT REFERENCES group_table(id) ON DELETE SET NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    student_year VARCHAR(15) NOT NULL
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
    teacher_id INT REFERENCES teacher(id) ON DELETE CASCADE,
    audience_id INT REFERENCES audience(id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
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