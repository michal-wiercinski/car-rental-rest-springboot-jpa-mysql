DROP DATABASE IF EXISTS carRental ^;
CREATE DATABASE carRental ^;

USE carRental ^;

DROP TABLE IF EXISTS address ^;
DROP TABLE IF EXISTS body_type ^;
DROP TABLE IF EXISTS car ^;
DROP TABLE IF EXISTS car_model ^;
DROP TABLE IF EXISTS car_parameter ^;
DROP TABLE IF EXISTS car_status ^;
DROP TABLE IF EXISTS location ^;
DROP TABLE IF EXISTS brand ^;
DROP TABLE IF EXISTS rental ^;
DROP TABLE IF EXISTS rental_status ^;
DROP TABLE IF EXISTS rental_details ^;
DROP TABLE IF EXISTS role ^;
DROP TABLE IF EXISTS user ^;
DROP TABLE IF EXISTS user_details ^;
DROP TABLE IF EXISTS user_roles ^;
DROP VIEW IF EXISTS details_fleet_for_admin ^;
DROP VIEW IF EXISTS details_fleet_for_user^;
DROP VIEW IF EXISTS rental_view ^;

CREATE TABLE address
(
    PK_address   BIGINT AUTO_INCREMENT PRIMARY KEY,
    city         VARCHAR(255) NOT NULL,
    street       VARCHAR(255) NOT NULL,
    house_number VARCHAR(10)  NOT NULL,
    zip_code     VARCHAR(6)   NOT NULL
) ^;

CREATE TABLE body_type
(
    PK_body_type BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_name    VARCHAR(15) NOT NULL
) ^;

CREATE TABLE brand
(
    PK_brand   BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(30) NOT NULL
) ^;

CREATE TABLE rental_status
(
    PK_status   BIGINT AUTO_INCREMENT PRIMARY KEY,
    status_desc VARCHAR(15) NOT NULL
) ^;

CREATE TABLE car_model
(
    PK_car_model   BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_model_name VARCHAR(30) NOT NULL,
    FK_brand       BIGINT      NOT NULL,
    FOREIGN KEY (FK_brand) REFERENCES brand (PK_brand)
) ^;

CREATE TABLE car_status
(
    PK_status_code     VARCHAR(3)  NOT NULL PRIMARY KEY,
    status_description VARCHAR(15) NOT NULL
) ^;

CREATE TABLE car_parameter
(
    PK_car_parameter BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_mileage  INT        NOT NULL,
    engine_size      INT        NOT NULL,
    power            INT        NOT NULL,
    year_of_prod     INT        NOT NULL,
    fuel_consumption DOUBLE     NOT NULL,
    daily_rate       DOUBLE     NOT NULL,
    FK_body_type     BIGINT     NOT NULL,
    FK_car_status    VARCHAR(3) NOT NULL,
    FOREIGN KEY (FK_body_type) REFERENCES body_type (PK_body_type),
    FOREIGN KEY (FK_car_status) REFERENCES car_status (PK_status_code)
) ^;

CREATE TABLE location
(
    PK_location   BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(255) NOT NULL,
    FK_address    BIGINT,
    FOREIGN KEY (FK_address) REFERENCES address (PK_address)

) ^;

CREATE TABLE car
(
    PK_car              BIGINT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(7)    NOT NULL,
    FK_location         BIGINT        NOT NULL,
    FK_car_parameter    BIGINT UNIQUE NOT NULL,
    FK_car_model        BIGINT        NOT NULL,
    FOREIGN KEY (FK_car_model) REFERENCES car_model (PK_car_model),
    FOREIGN KEY (FK_car_parameter) REFERENCES car_parameter (PK_car_parameter),
    FOREIGN KEY (FK_location) REFERENCES location (PK_location)
) ^;

CREATE TABLE role
(
    PK_role   BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(15) NOT NULL
) ^;

CREATE TABLE user_details
(
    PK_user_details BIGINT AUTO_INCREMENT PRIMARY KEY,
    FK_address      BIGINT NOT NULL,
    FOREIGN KEY (FK_address) REFERENCES address (PK_address)
) ^;

CREATE TABLE user
(
    PK_user         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(30)   NOT NULL,
    last_name       VARCHAR(30)   NOT NULL,
    password        VARCHAR(255)  NOT NULL,
    email           VARCHAR(50)   NOT NULL,
    FK_user_details BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (FK_user_details) REFERENCES user_details (PK_user_details)
) ^;


CREATE TABLE rental_details
(
    PK_rental_details BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date        TIMESTAMP                   NULL,
    end_date          TIMESTAMP                   NULL,
    rental_cost       DECIMAL(10, 2) DEFAULT 0.00 NULL,
    distance          INT            DEFAULT 0    NULL
) ^;

CREATE TABLE rental
(
    PK_rental         BIGINT AUTO_INCREMENT PRIMARY KEY,
    FK_user           BIGINT NULL,
    FK_status         BIGINT NOT NULL,
    FK_car            BIGINT NOT NULL,
    FK_rental_details BIGINT NOT NULL,
    FOREIGN KEY (FK_user) REFERENCES user (PK_user),
    FOREIGN KEY (FK_car) REFERENCES car (PK_car),
    FOREIGN KEY (FK_status) REFERENCES rental_status (PK_status),
    FOREIGN KEY (FK_rental_details) REFERENCES rental_details (PK_rental_details)
) ^;


CREATE TABLE user_roles
(
    FK_user BIGINT NOT NULL,
    FK_role BIGINT NOT NULL,
    FOREIGN KEY (FK_user) REFERENCES user (PK_user),
    FOREIGN KEY (FK_role) REFERENCES role (PK_role),
    UNIQUE (FK_user, FK_role)
) ^;

CREATE VIEW details_fleet_for_user
AS
SELECT c.PK_car              AS 'car_id',
       br.brand_name         AS 'brand',
       cm.car_model_name     AS 'car_model',
       bt.type_name          AS 'body_type',
       cp.power              AS 'engine_power',
       cp.engine_size        AS 'engine_size',
       cp.year_of_prod       AS 'production_year',
       cp.daily_rate         AS 'daily_rate',
       l.location_name       AS 'location_name',
       cs.status_description AS 'status'

FROM car AS c
         LEFT JOIN car_parameter AS cp
                   on c.FK_car_parameter = cp.PK_car_parameter
         LEFT JOIN car_model AS cm ON c.FK_car_model = cm.PK_car_model
         LEFT JOIN car_status cs ON cp.FK_car_status = cs.PK_status_code
         LEFT JOIN body_type AS bt ON cp.FK_body_type = bt.PK_body_type
         LEFT JOIN location AS l ON l.PK_location = c.FK_location
         LEFT JOIN brand AS br ON cm.FK_brand = br.PK_brand ^;

CREATE VIEW details_fleet_for_admin
AS
SELECT c.PK_car                         AS 'car_id',
       c.registration_number            AS 'registration_number',
       COALESCE(SUM(rd.rental_cost), 0) AS 'income',
       br.brand_name                    AS 'brand',
       cm.car_model_name                AS 'car_model',
       bt.type_name                     AS 'body_type',
       cp.power                         AS 'engine_power',
       cp.engine_size                   AS 'engine_size',
       cp.year_of_prod                  AS 'production_year',
       cp.current_mileage               AS 'current_mileage',
       cp.daily_rate                    AS 'daily_rate',
       cs.status_description            AS 'status',
       cp.fuel_consumption              AS 'avg_fuel_consumption',
       l.location_name                  AS 'location_name'
FROM car AS c
         LEFT JOIN car_parameter AS cp
                   ON c.FK_car_parameter = cp.PK_car_parameter
         LEFT JOIN car_model AS cm ON c.FK_car_model = cm.PK_car_model
         LEFT JOIN car_status cs ON cp.FK_car_status = cs.PK_status_code
         LEFT JOIN brand AS br ON cm.FK_brand = br.PK_brand
         LEFT JOIN body_type AS bt ON cp.FK_body_type = bt.PK_body_type
         LEFT JOIN rental r ON c.PK_car = r.FK_car
         LEFT JOIN rental_details rd ON r.FK_rental_details = rd.PK_rental_details
         LEFT JOIN location l ON c.FK_location = l.PK_location
GROUP BY c.PK_car ^;

CREATE VIEW rental_view
AS
SELECT r.PK_rental           AS 'rental_id',
       u.email               AS 'user_email',
       c.PK_car              AS 'car_id',
       c.registration_number AS 'registration_number',
       rs.status_desc        AS 'rental_status',
       br.brand_name         AS 'brand_name',
       cm.car_model_name     AS 'model_name',
       cp.daily_rate         AS 'daily_rate',
       rd.start_date         AS 'start_date',
       rd.end_date           AS 'end_date',
       rd.distance           AS 'distance',
       rd.rental_cost        AS 'rental_cost'
FROM rental AS r
         JOIN car c on r.FK_car = c.PK_car
         JOIN rental_status rs on r.FK_status = rs.PK_status
         JOIN car_model cm on c.FK_car_model = cm.PK_car_model
         JOIN brand br on cm.FK_brand = br.PK_brand
         JOIN car_parameter cp on c.FK_car_parameter = cp.PK_car_parameter
         JOIN rental_details rd on r.FK_rental_details = rd.PK_rental_details
         JOIN user u on r.FK_user = u.PK_user ^;
