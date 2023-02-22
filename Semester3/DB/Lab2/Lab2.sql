-- ============================================ LAB 2 =========================================================
use untold

INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (1, 'David', 'Vesa', 'contacts sponsors')
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (1, 'Kaufland', 5, 1)
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (221, 'Kaufland', 5, 1)
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (332, 'Kaufland', 5, 1)
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (331, 'Kaufland', 5, 1)
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (312, 'Kaufland', 5000, 1)
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (45, 'Tujamo',
'Matthias', 'Richter', '2022-08-05 05:00', 1, 3) -- violates referential integrity constraint (FK - SID)
INSERT INTO Stages(SID, stage_name, location) VALUES (6, 'Galaxy', 'Polyvalent hall')
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (45, 'Tujamo', 'Matthias',
'Richter', '2022-08-05 05:00', 1, 6)
DELETE FROM Artists WHERE last_name = 'Richter'
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (33, 'Boris Brejcha',
'Boris', 'Brejcha', '2022-08-04 03:00', 1, 6)
DELETE FROM Sponsors WHERE  amount_of_money BETWEEN 4 AND 7
INSERT INTO Sponsors (SID, company_name, amount_of_money, OID) VALUES (227, 'Orange', 2000, 1)
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (2, 'Andreea', 'Popescu', 'contacts artists')
INSERT INTO Stages(SID, stage_name, location) VALUES (1, 'Main', 'Arena')
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (20, 'G-Eazy', 'Gerald Earl',
'Gillum', '2022-08-04 10:00 PM', 2, 1)
UPDATE Artists SET OID = 2 WHERE first_name LIKE 'B%'
INSERT INTO Sponsors(SID, company_name, OID) VALUES (10, 'Telekom', null)
UPDATE Sponsors SET amount_of_money = 3000 WHERE company_name is null OR OID is null
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (3, 'Adrian', 'Hasa', 'trains and supervises the volunteers')
INSERT INTO Volunteers(CNP, first_name, last_name, job, OID) VALUES ('6010822014667', 'Karla', 'Stan', 'bracelets', 3)
INSERT INTO Stages(SID, stage_name) VALUES (4, 'Alchemy')
UPDATE Stages SET SID = SID + 1 WHERE location is null
UPDATE Stages SET SID = SID - 1 WHERE stage_name in ('Alchemy', 'Dream')
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (4, 'Emilia', 'Andrei', 'contacts artists')
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (31, 'Amelie Lens',
'Amelie', 'Lens', '2022-08-04 03:00', 4, 6)
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (45, 'Tujamo', 'Matthias',
'Richter', '2022-08-05 05:00', 2, 1)
INSERT INTO Artists(AID, artist_name, first_name, last_name, scheduled, OID, SID) VALUES (60, 'Wilkinson', 'Mark',
'Wilkinson', '2022-08-05 20:00', 4, 4)
UPDATE Artists SET scheduled = '2022-08-05 02:00' WHERE artist_name = 'Amelie Lens'
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (5, 'Larisa', 'Vesa', 'contacts sponsors')
UPDATE Sponsors SET OID = 5 WHERE OID is null
INSERT INTO Departments(DID, name, tasks) VALUES (1, 'Angels', 'leading the artists, making sure everything is going well')
INSERT INTO Departments(DID, name, tasks) VALUES (2, 'VIP', 'leading the VIPs, reporting any problem')
INSERT INTO Departments(DID, name, tasks) VALUES (3, 'entrance', 'verifying tickets, putting on bracelets, scanning' +
                                                                 'bracelets, controlling forbidden items')
UPDATE Volunteers SET job = 'putting on bracelets' WHERE job = 'bracelets'
UPDATE Departments SET tasks = 'verifying tickets, putting on bracelets, scanning bracelets, controlling forbidden items'
WHERE tasks = 'verifying tickets, putting on bracelets, scanningbracelets, controlling forbidden items'
INSERT INTO Volunteers(CNP, first_name, last_name, job, OID) VALUES ('6990427023452', 'Vlad', 'Pop', 'leading artists' +
' from the airport, entrance VIP', 6)
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (6, 'Luiza', 'Carpinisan', 'trains and supervises the volunteers')
INSERT INTO Departments(DID, name, tasks) VALUES (4, 'Security', 'steps in in case of any violation of the festival terms' +
                                                                 ' and conditions')
UPDATE Departments SET name = 'Entrance' WHERE name = 'entrance'
UPDATE Volunteers SET CNP = '5990427023452' WHERE CNP = '6990427023452'
INSERT INTO Volunteers(CNP, first_name, last_name, job, OID) VALUES ('5980228019664', 'Marian', 'Lazar', 'guarding the festival' +
                                                                                                         ' exit', 6)
INSERT INTO works_at(CNP, DID) VALUES ('6010822014667', 3)
INSERT INTO works_at(CNP, DID) VALUES ('5990427023452', 1)
INSERT INTO works_at(CNP, DID) VALUES ('5990427023452', 2)
INSERT INTO works_at(CNP, DID) VALUES ('5980228019664', 4)
INSERT INTO works_at(CNP, DID) VALUES ('6010315015662', 3)
INSERT INTO Volunteers(CNP, first_name, last_name, job, OID) VALUES ('6010315015662', 'Ana', 'Pastiu', 'verifying tickets', 3)
INSERT INTO FoodCourt(FID, location) VALUES (1, 'BT Arena')
INSERT INTO FoodCourt(FID, location) VALUES (2, 'Relaxation area')
INSERT INTO Restaurants(RID, name, menu, FID) VALUES (10, 'Noodle Pack', 'noodles, rice, shrimps, crispy, spring rolls', 1)
INSERT INTO Restaurants(RID, name, menu, FID) VALUES (8, 'Spartan', 'kebab, burger, wrap', 2)
INSERT INTO Restaurants(RID, name, menu, FID) VALUES (6, 'Lemnul verde', 'cakes, ice cream, cookies, donuts, muffins', 1)
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (7, 'Paul', 'Hategan', 'supervises restaurant employees')
INSERT INTO Employees(CNP, first_name, last_name, job, RID, remuneration, OID) VALUES ('6021008556781', 'Maria', 'Ilinca',
'shift manager', 10, 200, 7)
INSERT INTO Employees(CNP, first_name, last_name, job, RID, remuneration, OID) VALUES ('5020508546791', 'Rares', 'Bretfelean',
'cashier', 8, 200, 7)
UPDATE Employees SET remuneration = 400 WHERE remuneration = 200
INSERT INTO Bars(BID, location, menu) VALUES (55, 'BT Arena', 'coffee')
INSERT INTO Bars(BID, location, menu) VALUES (1, 'Galaxy stage', 'shots and long drinks')
INSERT INTO Organisers(OID, first_name, last_name, tasks) VALUES (8, 'Lukas', 'Horvath', 'supervises bartenders')
INSERT INTO Bartenders(CNP, first_name, last_name, job, BID, remuneration, OID) VALUES ('5020315661780', 'Sergiu', 'Sicoe',
'shift manager', 1, 400, 8)
INSERT INTO Bartenders(CNP, first_name, last_name, job, BID, remuneration, OID) VALUES ('6020923014661', 'Clara', 'Cibu',
'barista', 55, 300, 8)
INSERT INTO Volunteers(CNP, first_name, last_name, job, OID) VALUES ('5001222781900', 'Raul', 'Amariei', 'backstage', 6)
INSERT INTO works_at(CNP, DID) VALUES ('5001222781900', 1)
INSERT INTO Untolders(CNP, first_name, last_name) VALUES ('6000922015677', 'Karina', 'Rasinariu')
INSERT INTO Untolders(CNP, first_name, last_name) VALUES ('6000209025699', 'Diana', 'Tincu')
INSERT INTO Untolders(CNP, first_name, last_name) VALUES ('6020320891777', 'Denisa', 'Radu')
INSERT INTO goes_to(CNP, SID) VALUES ('6000922015677', 1)
INSERT INTO goes_to(CNP, SID) VALUES ('6020320891777', 4)
INSERT INTO goes_to(CNP, SID) VALUES ('6020320891777', 6)
INSERT INTO goes_to(CNP, SID) VALUES ('6000209025699', 1)
INSERT INTO goes_to(CNP, SID) VALUES ('6000209025699', 6)
INSERT INTO eats_at(CNP, RID) VALUES ('6000922015677', 8)
INSERT INTO eats_at(CNP, RID) VALUES ('6000209025699', 6)
INSERT INTO drinks_at(CNP, BID) VALUES ('6020320891777', 55)
INSERT INTO drinks_at(CNP, BID) VALUES ('6000209025699 ', 1)
INSERT INTO Employees(CNP, first_name, last_name, job, RID) VALUES ('5021120561667','Mihai', 'Cancescu', 'cook', 10)
INSERT INTO Volunteers(CNP, first_name, last_name) VALUES ('6030415671889', 'Laura', 'Serban')
UPDATE Employees SET remuneration = 200 WHERE remuneration is null
UPDATE Employees SET OID = 7 WHERE OID is null
UPDATE Employees SET remuneration = 200 WHERE first_name = 'Rares'
INSERT INTO Bars(BID, location, menu) VALUES (43, 'Relaxation area', 'wine')
INSERT INTO Bartenders(CNP, first_name, last_name, job, BID) VALUES ('6010708918771', 'Alexandra', 'Socea', 'shift manager', 55)
INSERT INTO Departments(DID, name, tasks) VALUES (5, 'advertising', 'promoting products, being persuassive and creative')
UPDATE Departments SET name = 'Advertising' WHERE name = 'advertising'
INSERT INTO Bars(BID, location, menu) VALUES (2, 'Galaxy stage', 'beer')
INSERT INTO Bartenders(CNP, first_name, last_name, job, BID) VALUES ('5020610917865', 'Vlad', 'Marti', 'barman', 1)
UPDATE Bartenders SET remuneration = 250, OID = 8 WHERE last_name = 'Marti'
INSERT INTO Bartenders(CNP, first_name, last_name, job, BID, remuneration, OID) VALUES ('6030321819441', 'Anca', 'Duca',
                                                                                        'draftier', 2, 250, 8)
UPDATE Bartenders SET job = 'barman' where job = 'draftier'
INSERT INTO Employees(CNP, first_name, last_name, job, RID, remuneration, OID) VALUES ('6010708917234', 'Sorana', 'Cibu',
                                                                                       'shift manager', 8, 500, 7)
UPDATE Bartenders SET remuneration = 400 WHERE last_name = 'Socea'
INSERT INTO Volunteers(CNP, first_name, last_name) VALUES ('6010516671889', 'Larisa', 'Popovici')
INSERT INTO Volunteers(CNP, first_name, last_name) VALUES ('5010516671889', 'Mihai', 'Andrei')

-- a)
-- 1. Find the organisers whose last name is Vesa or whose task is to contact artists
SELECT O1.* FROM Organisers O1
WHERE O1.last_name = 'Vesa'
UNION
SELECT O2.* FROM Organisers O2
WHERE O2.tasks = 'contacts artists'

-- 2. Find the name of the artists who perform either on Alchemy or Galaxy and sort them ascending by their schedule
SELECT A.artist_name FROM Artists A, Stages S
WHERE A.SID = S.SID AND (S.stage_name = 'Alchemy' OR S.stage_name = 'Galaxy')
ORDER BY A.scheduled

-- b)
-- 1. Find the name of the artists who perform on Main Stage and were contacted by Andreea Popescu
SELECT A1.artist_name FROM Artists A1, Stages S
WHERE A1.SID = S.SID AND S.stage_name = 'Main'
INTERSECT
SELECT A2.artist_name FROM Artists A2, Organisers O
WHERE A2.OID = O.OID AND O.first_name = 'Andreea' AND O.last_name = 'Popescu'

-- 2. Find the name of the volunteers assigned at the entrance who were contacted by Adrian Hasa
SELECT V.first_name, V.last_name FROM Volunteers V
WHERE V.CNP IN
      (SELECT W.CNP
       FROM works_at W
       WHERE W.DID IN
             (SELECT D.DID
              FROM Departments D
              WHERE D.name = 'Entrance'
              )
       )
AND V.OID IN
    (SELECT O.OID
     FROM Organisers O
     WHERE O.first_name = 'Adrian'
       AND O.last_name = 'Hasa'
     )

-- c)
-- 1. Find the name of the volunteers who work at Angels, but not at VIP
SELECT V1.first_name, V1.last_name FROM Volunteers V1, works_at W1, Departments D1
WHERE V1.CNP = W1.CNP AND W1.DID = D1.DID AND D1.name = 'Angels'
EXCEPT
SELECT V2.first_name, V2.last_name FROM Volunteers V2, works_at W2, Departments D2
WHERE V2.CNP = W2.CNP AND W2.DID = D2.DID AND D2.name = 'VIP'

-- 2. Find the untolders who are going to Main stage, but not to Galaxy stage
SELECT U.* FROM Untolders U, goes_to G1, Stages S1
WHERE U.CNP = G1.CNP AND G1.SID = S1.SID AND S1.stage_name = 'Main'
AND U.CNP NOT IN (SELECT G2.CNP FROM goes_to G2, Stages S2
                                WHERE G2.SID = S2.SID AND S2.stage_name = 'Galaxy')

-- d)
-- 1. Show where the untolders eat and what they drink
SELECT U.first_name, U.last_name, R.name, B.menu FROM Untolders U
INNER JOIN eats_at E ON U.CNP = E.CNP
INNER JOIN drinks_at D ON E.CNP = D.CNP
INNER JOIN Bars B ON B.BID = D.BID
INNER JOIN Restaurants R ON R.RID = E.RID

-- 2. Show all bars with their bartenders (bars with no bartenders should also appear in the result set)
SELECT B1.menu, B2.first_name, B2.last_name FROM Bars B1
LEFT JOIN Bartenders B2 on B1.BID = B2.BID

-- 3. Show all employees and the restaurants they work at, include restaurants with no employees
SELECT E.first_name, E.last_name, R.name FROM Employees E
RIGHT JOIN Restaurants R ON E.RID = R.RID

-- 4. Show in which department do the volunteers work (volunteers who don't work and departments
-- with no volunteers should also appear in the result set)
SELECT V.first_name, V.last_name, D.name FROM Volunteers V
FULL OUTER JOIN works_at W ON W.CNP = V.CNP
FULL OUTER JOIN Departments D ON D.DID = W.DID

-- e)
-- 1. Find the name of the artists who perform on the stage with id = 6
SELECT A.artist_name FROM Artists A
WHERE A.SID IN
    (SELECT S.SID FROM Stages S
    WHERE S.SID = 6)

-- 2. Find the name of the employees who work in the relaxation area
SELECT E.first_name, E.last_name FROM Employees E
WHERE E.RID IN
      (SELECT R.RID
       FROM Restaurants R
       WHERE R.FID IN
             (SELECT F.FID
              FROM FoodCourt F
              WHERE F.location = 'Relaxation area'
              )
       )

-- f)
-- 1. Find the jobs of the bartenders who work at Galaxy Stage
SELECT DISTINCT B1.job FROM Bartenders B1
WHERE EXISTS (SELECT * FROM Bars B2
              WHERE B2.BID = B1.BID AND B2.location = 'Galaxy stage')

-- 2. Find the employees who work at Noodle Pack
CREATE VIEW [Noodle Pack employees] AS
SELECT * FROM Employees E
WHERE EXISTS (SELECT * FROM Restaurants R
              WHERE E.RID = R.RID AND R.name = 'Noodle Pack')

SELECT * from [Noodle Pack employees]
DROP VIEW [Noodle Pack employees]

-- g)
-- 1. Find the bartenders leaded by the organiser with id = 8 who don't have the remuneration 250.
SELECT *
FROM (SELECT * FROM Bartenders B1
      WHERE B1.OID = 8) B2
WHERE NOT B2.remuneration = 250

-- 2. Find the artists who perform on Main Stage
SELECT A.* FROM Artists A INNER JOIN
(SELECT *
FROM Stages S
WHERE S.stage_name = 'Main') S1
ON S1.SID = A.SID

-- h)
-- 1. Find the salary of the worst-paid employee for each restaurant
SELECT R.name, MIN(E.remuneration)
FROM Employees E
INNER JOIN Restaurants R on R.RID = E.RID
GROUP BY R.name

-- 2. Find the average amount of money collected by each organiser who contacts at least 2 sponsors
SELECT O.OID, AVG(S.amount_of_money)
FROM Organisers O
INNER JOIN Sponsors S on S.OID = O.OID
GROUP BY O.OID
HAVING COUNT(*) >= 1

-- 3. Find the salary of the most well-paid bartender for each bar with at least 2 bartenders
SELECT B.BID, MAX(B.remuneration)
FROM Bartenders B
GROUP BY B.BID
HAVING 1 < (SELECT COUNT(*)
FROM Bartenders B2
WHERE B2.BID = B.BID)

-- 4. Find the total amount of money used for paying the employees for each restaurant with at least 2 employees
SELECT E.RID, SUM(E.remuneration)
FROM Employees E
INNER JOIN Restaurants R on R.RID = E.RID
GROUP BY E.RID
HAVING 2 <= (SELECT COUNT(*)
FROM Employees E2
WHERE E2.RID = E.RID)

-- i)
-- 1. Find employees whose remuneration is greater than the remuneration of any bartender
SELECT first_name, last_name, remuneration
FROM Employees
WHERE remuneration > ANY (SELECT remuneration
                          from Bartenders)

-- rewritten
SELECT first_name, last_name, remuneration
FROM Employees
WHERE remuneration > (SELECT MIN(remuneration)
                          from Bartenders)

-- 2. Find employees whose remuneration is greater than the remuneration of all bartenders
SELECT first_name, last_name, remuneration
FROM Employees
WHERE remuneration > ALL (SELECT remuneration
                          from Bartenders)

-- rewritten
SELECT first_name, last_name, remuneration
FROM Employees
WHERE remuneration > (SELECT MAX(remuneration)
                          from Bartenders)

-- 3. Find the volunteers whose organiser has id = 3 (top 1 by CNP)
SELECT TOP 1 V.* FROM Volunteers V
WHERE V.OID = ANY
(SELECT O.OID FROM Organisers O
WHERE O.OID = 3)

-- rewritten
SELECT V.* FROM Volunteers V
WHERE V.OID IN
(SELECT O.OID FROM Organisers O
WHERE O.OID = 3)

-- 4. Find the volunteers whose organiser has not id = 3 (top 1 ordered descending by name)
SELECT TOP 1 V.* FROM Volunteers V
WHERE V.OID <> ALL
(SELECT O.OID
FROM Organisers O
WHERE O.OID = 3)
ORDER BY V.first_name DESC

-- rewritten (ordered by name)
SELECT V.* FROM Volunteers V
WHERE V.OID NOT IN
(SELECT O.OID
FROM Organisers O
WHERE O.OID = 3)
ORDER BY V.first_name


-- EXTRA
-- For all sponsors, retrieve the following data: amount of money, amount*2
SELECT S.amount_of_money, amount = S.amount_of_money*2
FROM Sponsors S

-- For all bartenders, retrieve the following data: remuneration, remuneration+100
SELECT B.remuneration, B.remuneration+100 as rem2
FROM Bartenders B

-- For all employees, retrieve the following data: remuneration, remuneration-100
SELECT E.remuneration, E.remuneration-100 as rem2
FROM Employees E

-- Find the volunteers who work at department 1 or 2
SELECT DISTINCT V.*
FROM Volunteers V, works_at
WHERE (works_at.DID = 1 OR works_at.DID = 2) and V.CNP = works_at.CNP

-- Find the untolders who go at stages Alchemy or Galaxy
SELECT DISTINCT U.*
FROM Untolders U, goes_to, Stages S
WHERE U.CNP = goes_to.CNP AND goes_to.SID = S.SID AND (S.stage_name = 'Alchemy' OR S.stage_name = 'Galaxy')