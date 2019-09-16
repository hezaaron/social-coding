CREATE TABLE exam (
  id          int          not null auto_increment primary key,
  name        varchar(50)  not null,
  description varchar(250) not null
);

CREATE TABLE question (
  id           int          not null auto_increment primary key,
  exam_id      int          not null,
  name         varchar(250) not null,
  code		   mediumtext	not null,
  multi_answer bit          not null default 0
);

ALTER TABLE question add foreign key (exam_id) references exam (id);

CREATE TABLE answer (
  id          int          not null auto_increment primary key,
  question_id int          not null,
  name        varchar(250) not null,
  is_correct  bit          not null default 0
);
ALTER TABLE answer add foreign key (question_id) references question (id);

CREATE TABLE exam_result (
  id              int      not null auto_increment primary key,
  exam_id         int      not null,
  start           datetime not null,
  finish          datetime,
  question_count  int,
  correct_answers int,
  grade           int
);
ALTER TABLE exam_result add foreign key (exam_id) references exam (id);
