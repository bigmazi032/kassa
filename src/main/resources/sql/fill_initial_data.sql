INSERT theatre (name)
VALUES ('Большой театр');

INSERT theatre (name)
VALUES ('Малый театр');

INSERT theatre (name)
VALUES ('Театр на Таганке');

INSERT theatre (name)
VALUES ('ТЮЗ');

-- ADD PERFORMANCES
INSERT performance(name)
VALUES ('Преступление и наказание');

INSERT performance(name)
VALUES ('Мастер и Маргаритта');

INSERT performance(name)
VALUES ('Анна Каренина');

INSERT performance(name)
VALUES ('Гордость и Предубеждение');

-- Add schedule

INSERT schedule(theatre_id, performance_id, date)
VALUES ((SELECT id FROM theatre WHERE name = 'Большой театр'),
        ( SELECT id FROM performance WHERE name = 'Преступление и наказание'),
        '2021-10-13'
);

INSERT schedule(theatre_id, performance_id, date)
VALUES ((SELECT id FROM theatre WHERE name = 'Малый театр'),
        ( SELECT id FROM performance WHERE name = 'Преступление и наказание'),
        '2021-10-11'
       );

INSERT schedule(theatre_id, performance_id, date)
VALUES ((SELECT id FROM theatre WHERE name = 'Большой театр'),
        ( SELECT id FROM performance WHERE name = 'Гордость и Предубеждение'),
        '2021-10-05'
       );

INSERT schedule(theatre_id, performance_id, date)
VALUES ((SELECT id FROM theatre WHERE name = 'Театр на Таганке'),
        ( SELECT id FROM performance WHERE name = 'Мастер и Маргаритта'),
        '2021-10-22'
       );


-- Add categories

INSERT seat_category (name)
VALUES ('Обычная');

INSERT seat_category (name)
VALUES ('VIP');

-- Add seat status

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Большой театр'
            AND p.name = 'Преступление и наказание'
            And s.date = '2021-10-13' ),
        (SELECT id
         FROM seat_category
         WHERE name = 'Обычная'
        ),
        100, 20, 20);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Большой театр'
            AND p.name = 'Преступление и наказание'
            And s.date = '2021-10-13' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'VIP'
         ),
         200, 10, 10);


INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Малый театр'
            AND p.name = 'Преступление и наказание'
            And s.date = '2021-10-11' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'Обычная'
         ),
         150, 10, 10);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Малый театр'
            AND p.name = 'Преступление и наказание'
            And s.date = '2021-10-11' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'VIP'
         ),
         200, 5, 5);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Театр на Таганке'
            AND p.name = 'Мастер и Маргаритта'
            And s.date = '2021-10-22' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'Обычная'
         ),
         125, 25, 25);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Театр на Таганке'
            AND p.name = 'Мастер и Маргаритта'
            And s.date = '2021-10-22' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'vip'
         ),
         175, 10, 10);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Большой театр'
            AND p.name = 'Гордость и Предубеждение'
            And s.date = '2021-10-05' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'Обычная'
         ),
         250, 12, 12);

INSERT seats_status ( schedule_id, seat_category_id, price, total, free)
VALUES ( (SELECT s.id
          FROM schedule s
                   JOIN theatre t on t.id = s.theatre_id
                   JOIN performance p on p.id = s.performance_id
          WHERE t.name = 'Большой театр'
            AND p.name = 'Гордость и Предубеждение'
            And s.date = '2021-10-05' ),
         (SELECT id
          FROM seat_category
          WHERE name = 'VIP'
         ),
         300, 8, 8);

