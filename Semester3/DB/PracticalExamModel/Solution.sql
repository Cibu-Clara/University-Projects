--============================================= MODEL 1 ===================================================
use TrainSchedules
-- I
-- 1 c
-- 2 c
-- 3 b

-- II
-- 1
CREATE TABLE TrainTypes(
    TrainTypeID INT PRIMARY KEY,
    TTName varchar(50),
    TTDescription varchar(50)
)

CREATE TABLE Trains(
    TrainID INT PRIMARY KEY,
    TName varchar(50),
    TrainTypeID INT references TrainTypes(TrainTypeID)
)

CREATE TABLE Routes(
    RouteID INT PRIMARY KEY,
    RName varchar(50) UNIQUE ,
    TrainID INT references Trains(TrainID)
)

CREATE TABLE Stations(
    StationID INT PRIMARY KEY,
    SName varchar(50) UNIQUE
)

CREATE TABLE RoutesStations(
    RouteID INT references Routes(RouteID),
    StationID INT references Stations(StationID),
    Arrival TIME,
    Departure TIME,
    PRIMARY KEY (RouteID, StationID)
)

-- 2
CREATE OR ALTER PROCEDURE uspUpdateStationOnRoute
(@RName varchar(50), @SName varchar(50), @Arrival TIME, @Departure TIME)
AS
    DECLARE @RouteID INT, @StationID INT

    IF NOT EXISTS (SELECT * FROM Routes WHERE RName = @RName)
    BEGIN
        RAISERROR ('Invalid route name.', 16, 1)
    END

    IF NOT EXISTS (SELECT * FROM Stations WHERE SName = @SName)
    BEGIN
        RAISERROR ('Invalid station name.', 16, 1)
    END

    SELECT @RouteID = (SELECT RouteID FROM Routes WHERE RName = @RName),
           @StationID = (SELECT StationID FROM Stations WHERE SName = @SName)

    IF EXISTS (SELECT * FROM RoutesStations WHERE RouteID = @RouteID AND StationID = @StationID)
        UPDATE RoutesStations
        SET Arrival = @Arrival, Departure = @Departure
        WHERE RouteID = @RouteID AND StationID = @StationID
    ELSE
        INSERT RoutesStations(RouteID, StationID, Arrival, Departure)
        VALUES (@RouteID, @StationID, @Arrival, @Departure)
GO

INSERT TrainTypes VALUES (1, 'tt1', 'd1'), (2, 'tt2', 'd2')
INSERT Trains VALUES (1, 't1', 1), (2, 't2', 1), (3, 't3', 1)
INSERT Routes VALUES (1, 'r1', 1), (2, 'r2', 2), (3, 'r3', 3)
INSERT Stations VALUES (1, 's1'), (2, 's2'), (3, 's3')

--ERRORS for:
--exec uspUpdateStationOnRoute 'r4', 's1', '7:00 AM', '7:10 AM'
--exec uspUpdateStationOnRoute 'r1', 's4', '7:00 AM', '7:10 AM'

EXEC uspUpdateStationOnRoute 'r1', 's1', '7:00 AM', '7:10 AM'
EXEC uspUpdateStationOnRoute 'r1', 's2', '7:20 AM', '7:30 AM'
EXEC uspUpdateStationOnRoute 'r1', 's3', '7:40 AM', '7:50 AM'

EXEC uspUpdateStationOnRoute 'r2', 's1', '8:00 AM', '8:10 AM'
EXEC uspUpdateStationOnRoute 'r2', 's2', '8:20 AM', '8:30 AM'
EXEC uspUpdateStationOnRoute 'r2', 's3', '8:40 AM', '8:50 AM'

EXEC uspUpdateStationOnRoute 'r3', 's1', '9:00 AM', '9:10 AM'
EXEC uspUpdateStationOnRoute 'r3', 's2', '9:20 AM', '9:30 AM'

-- 3
CREATE OR ALTER VIEW vRoutesAllStations
AS
SELECT R.RName
FROM Routes R
WHERE NOT EXISTS
    (SELECT StationID
    FROM Stations
    EXCEPT
    SELECT StationID
    FROM RoutesStations
    WHERE RouteID = R.RouteID)
GO

--4
CREATE OR ALTER FUNCTION ufFilterStationsByNoRoutes(@R INT)
RETURNS TABLE
RETURN
    SELECT SName
    FROM Stations
    WHERE StationID IN
        (SELECT StationID
        FROM RoutesStations
        GROUP BY StationID
        HAVING COUNT(*) > @R)

SELECT * FROM ufFilterStationsByNoRoutes (2)

