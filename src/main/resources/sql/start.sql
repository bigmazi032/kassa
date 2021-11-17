CREATE TABLE theatre
(id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50) UNIQUE
);

CREATE TABLE performance
(id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50) UNIQUE
);

CREATE TABLE schedule
(id INT PRIMARY KEY AUTO_INCREMENT,
 theatre_id INT,
 performance_id INT,
 date DATE,
 CONSTRAINT schedule_theatre_fk
     FOREIGN KEY (theatre_id)  REFERENCES theatre (id),
 CONSTRAINT schedule_performance_fk
     FOREIGN KEY (performance_id)  REFERENCES performance (id)
);

CREATE TABLE seat_category
(id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(50)
);

CREATE TABLE seats_status
(id INT PRIMARY KEY AUTO_INCREMENT,
 schedule_id INT,
 seat_category_id INT,
 price INT,
 total INT,
 free INT,
 CONSTRAINT price_schedule_fk
     FOREIGN KEY (schedule_id)  REFERENCES schedule (id),
 CONSTRAINT price_seat_category_fk
     FOREIGN KEY (seat_category_id)  REFERENCES seat_category (id)
);

CREATE TABLE receipt
(id INT PRIMARY KEY AUTO_INCREMENT,
 date DATE,
 summa INT
);

CREATE TABLE receipt_item
(id INT PRIMARY KEY AUTO_INCREMENT,
 receipt_id INT,
 seats_status_id INT,
 quantity INT,
 summa INT,
 CONSTRAINT receipt_item_receipt_fk
     FOREIGN KEY (receipt_id)  REFERENCES receipt (id),
 CONSTRAINT receipt_item_seats_status_fk
     FOREIGN KEY (seats_status_id)  REFERENCES seats_status (id)
);



# CREATE TABLE receipt_return
# (id INT PRIMARY KEY AUTO_INCREMENT,
#   receipt_id INT,
#   date_return DATE,
#   summa_return INT,
# CONSTRAINT receipt_return_receipt_fk
#     FOREIGN KEY (receipt_id) REFERENCES receipt (id)
# );
#
# CREATE TABLE receipt_item_return
# (id INT PRIMARY KEY AUTO_INCREMENT,
#  receipt_return_id INT,
#  receipt_item_id INT,
#  quantity INT,
#  summa_item_return INT,
#  CONSTRAINT receipt_item_return_receipt_fk
#      FOREIGN KEY (receipt_item_id) REFERENCES receipt_item (id),
#  CONSTRAINT receipt_item_return_receipt_return_fk
#      FOREIGN KEY (receipt_return_id) REFERENCES receipt_return (id)
# );
