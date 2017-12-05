DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2017-11-29 13:25:27','обед', 1000, 100000),
  ('2017-11-28 13:25:30','обед', 1000, 100000),
  ('2017-11-29 13:25:29','обед', 1000, 100001),
  ('2017-11-28 08:26:27','завтрак', 1000, 100000),
  ('2017-11-29 08:26:27','завтрак', 500, 100001),
  ('2017-11-29 08:25:27','завтрак', 200, 100000),
  ('2017-11-28 20:26:27','ужин', 700, 100000),
  ('2017-11-28 20:27:27','ужин', 1000, 100001),
  ('2017-11-29 20:28:27','ужин', 900, 100000),
  ('2017-11-29 20:25:27','ужин', 500, 100001);

