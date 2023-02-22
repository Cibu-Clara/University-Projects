-- ============================================ LAB 1 =========================================================
USE UNTOLD

CREATE TABLE ARTISTS
(AID int not null primary key
)

ALTER TABLE Artists
add artist_name varchar(30)

ALTER TABLE Artists
add first_name varchar(30),
    last_name varchar(30),
    UNIQUE (first_name, last_name)

CREATE TABLE Stages
(SID int not null primary key,
stage_name varchar(30),
location varchar(50))

ALTER TABLE Artists
add scheduled datetime,
    SID int,
    FOREIGN KEY (SID) references Stages(SID)
        on update cascade
        on delete cascade

CREATE TABLE Bars
(BID int not null primary key,
 location varchar(50),
 menu varchar(1000))

CREATE TABLE Bartenders
(CNP char(15) not null primary key,
first_name varchar(30),
last_name varchar(30),
job varchar(30),
BID int not null CONSTRAINT FK_Bartenders_BID FOREIGN KEY references Bars(BID)
    on update cascade
    on delete cascade)

CREATE TABLE Restaurants
(RID int not null primary key,
 name varchar(50),
 location varchar(50),
 menu varchar(1000))

ALTER TABLE Volunteers
ALTER COLUMN job varchar(300)

CREATE TABLE Employees
(CNP char(15) not null CONSTRAINT PK_Employees primary key,
first_name varchar(30),
last_name varchar(30),
job varchar(30),
RID int not null,
FOREIGN KEY (RID) references Restaurants(RID)
    on update cascade
    on delete cascade)

CREATE TABLE FoodCourt
(FID int not null primary key,
 location varchar(50))

ALTER TABLE Restaurants
add FID int,
    FOREIGN KEY (FID) references FoodCourt(FID)
    on update cascade
    on delete cascade

CREATE TABLE Volunteers
(CNP char(15) not null primary key,
 first_name varchar(30),
 last_name varchar(30),
 job varchar(30))

CREATE TABLE Departments
(DID int not null primary key,
 name varchar(50),
 tasks varchar(500))

CREATE TABLE works_at
(CNP char(15) not null,
 DID int not null,
  FOREIGN KEY (CNP) references Volunteers(CNP)
    on update cascade
    on delete cascade,
  FOREIGN KEY (DID) references Departments(DID)
    on update cascade
    on delete cascade)

CREATE TABLE Sponsors
(SID int not null primary key,
 company_name varchar(50),
 amount_of_money decimal(8,2) default null)

ALTER TABLE Bartenders
add remuneration decimal(5,2) default null

ALTER TABLE Employees
add remuneration decimal(5,2) default null

CREATE TABLE Organisers
(OID int not null primary key,
first_name varchar(30),
last_name varchar(30),
tasks varchar(500))

ALTER TABLE Sponsors
add OID int,
    FOREIGN KEY (OID) references Organisers(OID)
    on update cascade
    on delete cascade

ALTER TABLE Volunteers
add OID int,
    FOREIGN KEY (OID) references Organisers(OID)
    on update cascade
    on delete cascade

ALTER TABLE Bartenders
add OID int,
    FOREIGN KEY (OID) references Organisers(OID)
    on update cascade
    on delete cascade

ALTER TABLE Employees
add OID int,
    FOREIGN KEY (OID) references Organisers(OID)
    on update cascade
    on delete cascade

ALTER TABLE Artists
add OID int,
    FOREIGN KEY (OID) references Organisers(OID)
    on update cascade
    on delete cascade

ALTER TABLE Restaurants
DROP COLUMN location

CREATE TABLE Untolders
(CNP char(15) not null primary key,
 first_name varchar(30),
 last_name varchar(30))

CREATE TABLE goes_to
(CNP char(15) not null,
 SID int not null,
  FOREIGN KEY (CNP) references Untolders(CNP)
    on update cascade
    on delete cascade,
  FOREIGN KEY (SID) references Stages(SID)
    on update cascade
    on delete cascade
)

CREATE TABLE eats_at
(CNP char(15) not null,
 RID int not null,
  FOREIGN KEY (CNP) references Untolders(CNP)
    on update cascade
    on delete cascade,
  FOREIGN KEY (RID) references Restaurants(RID)
    on update cascade
    on delete cascade)

CREATE TABLE drinks_at
(CNP char(15) not null,
 BID int not null,
  FOREIGN KEY (CNP) references Untolders(CNP)
    on update cascade
    on delete cascade,
  FOREIGN KEY (BID) references Bars(BID)
    on update cascade
    on delete cascade)