DROP DATABASE IF EXISTS carRental ^;
CREATE DATABASE carRental ^;

USE carRental ^;

DROP TABLE IF EXISTS address ^;
DROP TABLE IF EXISTS body_type ^;
DROP TABLE IF EXISTS car ^;
DROP TABLE IF EXISTS car_model ^;
DROP TABLE IF EXISTS car_parameter ^;
DROP TABLE IF EXISTS car_status ^;
DROP TABLE IF EXISTS drive_train ^;
DROP TABLE IF EXISTS engine ^;
DROP TABLE IF EXISTS location ^;
DROP TABLE IF EXISTS brand ^;
DROP TABLE IF EXISTS rental ^;
DROP TABLE IF EXISTS rental_status ^;
DROP TABLE IF EXISTS rental_details ^;
DROP TABLE IF EXISTS role ^;
DROP TABLE IF EXISTS user ^;
DROP TABLE IF EXISTS user_roles ^;


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
    PK_body_type      BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_name         ENUM ('HATCHBACK', 'SEDAN', 'SUV', 'COUPE','WAGON','VAN', 'JEEP') NOT NULL,
    number_of_seats   INT                                                               NOT NULL,
    number_of_doors   INT                                                               NOT NULL,
    fuel_tank_volume  INT                                                               NOT NULL,
    volume_of_luggage INT                                                               NOT NULL
) ^;

CREATE TABLE brand
(
    PK_brand   BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(30) NOT NULL
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
    PK_status_code     VARCHAR(3)                        NOT NULL PRIMARY KEY,
    status_description ENUM ('AVAILABLE', 'UNAVAILABLE') NOT NULL
) ^;

CREATE TABLE drive_train
(
    PK_drive_train  BIGINT AUTO_INCREMENT PRIMARY KEY,
    wheel_drive     ENUM ('FRONT_WHEEL_DRIVE', 'REAR_WHEEL_DRIVE', 'FOUR_WHEEL_DRIVE' ) NOT NULL,
    gearbox_type    ENUM ('MANUAL', 'AUTOMATIC')                                        NOT NULL,
    number_of_gears INT                                                                 NOT NULL
) ^;

CREATE TABLE engine
(
    PK_engine        BIGINT AUTO_INCREMENT PRIMARY KEY,
    power            INT                                        NOT NULL,
    capacity         INT                                        NOT NULL,
    fuel_type        ENUM ('DIESEL', 'HYBRID', 'PETROL', 'CNG') NOT NULL,
    fuel_consumption DOUBLE                                     NOT NULL

) ^;

CREATE TABLE rental_status
(
    PK_status   BIGINT AUTO_INCREMENT PRIMARY KEY,
    status_desc ENUM ('CANCELED' , 'RENTED') NOT NULL
) ^;

CREATE TABLE car_parameter
(
    PK_car_parameter BIGINT AUTO_INCREMENT PRIMARY KEY,
    year_of_prod     INT                 NOT NULL,
    daily_rate       DOUBLE              NOT NULL,
    color            VARCHAR(20) /*NOT*/ NULL,
    current_mileage  INT                 NOT NULL,
    FK_body_type     BIGINT              NOT NULL,
    FK_drive_train   BIGINT /*NOT*/      NULL,
    FK_engine        BIGINT /*NOT*/      NULL,
    FOREIGN KEY (FK_body_type) REFERENCES body_type (PK_body_type),
    FOREIGN KEY (FK_drive_train) REFERENCES drive_train (PK_drive_train),
    FOREIGN KEY (FK_engine) REFERENCES engine (PK_engine)

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
    FK_car_status       VARCHAR(3)    NOT NULL,
    FOREIGN KEY (FK_car_model) REFERENCES car_model (PK_car_model),
    FOREIGN KEY (FK_car_parameter) REFERENCES car_parameter (PK_car_parameter),
    FOREIGN KEY (FK_location) REFERENCES location (PK_location),
    FOREIGN KEY (FK_car_status) REFERENCES car_status (PK_status_code)
) ^;

CREATE TABLE role
(
    PK_role   BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name ENUM ('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER') NOT NULL
) ^;


CREATE TABLE user
(
    PK_user         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(30)   NOT NULL,
    last_name       VARCHAR(30)   NOT NULL,
    password        VARCHAR(255)  NOT NULL,
    email           VARCHAR(50)   NOT NULL,
    FK_address      BIGINT NOT NULL,
    FOREIGN KEY (FK_address) REFERENCES address (PK_address)
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