DROP TABLE memberships;
DROP TABLE notes;
DROP TABLE tasks;
DROP TABLE sessions;
DROP TABLE users;
DROP TABLE executives;
DROP TABLE categories;

CREATE TABLE executives (
  id serial4 primary key,
  name varchar(255)
);

CREATE TABLE users (
  id serial4 primary key,
  name varchar(255),
  userid varchar(255),
  password varchar(255),
  my_executive_id int4 references executives(id)
);

CREATE TABLE sessions (
  id serial4 primary key,
  sessionid varchar(255),
  last_used timestamp,
  user_id int4 references users(id) on delete cascade
);

CREATE TABLE memberships (
  id serial4 primary key,
  user_id int4 references users(id) on delete cascade,
  executive_id int4 references executives(id) on delete cascade
);

CREATE TABLE categories (
  id serial4 primary key,
  name varchar(255),
  colour varchar(255),
  created_by_user_id int4 references users(id)
);

CREATE TABLE tasks (
  id serial4 primary key,
  headline varchar(255),
  description varchar(255),
  create_date date,
  target_date date,
  priority integer,
  status integer,
  created_by_user_id int4 references users(id),
  category_id int4 references categories(id),
  allocated_executive_id int4 references executives(id),
  allocated_user_id int4 references users(id) on delete cascade,
  is_deleted boolean
);

CREATE TABLE notes (
  id serial4 primary key,
  create_date date,
  text varchar(255),
  task_id int4 references tasks(id) on delete cascade
);
