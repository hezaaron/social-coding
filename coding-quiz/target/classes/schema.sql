CREATE TABLE quiz (
  id          int          not null auto_increment primary key,
  name        varchar(50)  not null,
  description varchar(250) not null
);

CREATE TABLE question (
  id           int          not null auto_increment primary key,
  quiz_id      int          not null,
  name         varchar(250) not null,
  code		   mediumtext	not null,
  multi_answer bit          not null default 0
);

ALTER TABLE question add foreign key (quiz_id) references quiz (id);

CREATE TABLE answer (
  id          int          not null auto_increment primary key,
  question_id int          not null,
  name        varchar(250) not null,
  is_correct  bit          not null default 0
);
ALTER TABLE answer add foreign key (question_id) references question (id);

CREATE TABLE protocol (
  id              int      not null auto_increment primary key,
  quiz_id         int      not null,
  start_time           datetime not null,
  finish_time          datetime,
  question_count  int      not null,
  correct_answers int,
  grade           int
);
ALTER TABLE protocol add foreign key (quiz_id) references quiz (id);
