-- ============================================ LAB 3 =========================================================
USE UNTOLD

CREATE PROCEDURE uspModifyColumnOrganiserTasks
AS
ALTER TABLE Organisers
ALTER COLUMN tasks varchar(200)
GO
EXEC uspModifyColumnOrganiserTasks

CREATE PROCEDURE uspRevertColumnOrganiserTasks
AS
ALTER TABLE Organisers
ALTER COLUMN tasks varchar(500)
GO
EXEC uspRevertColumnOrganiserTasks

CREATE PROCEDURE uspDeleteColumnEmployeesJob
AS
ALTER TABLE Employees
DROP COLUMN job
GO
EXEC uspDeleteColumnEmployeesJob

CREATE PROCEDURE uspAddColumnEmployeesJob
AS
ALTER TABLE Employees
ADD job varchar(30)
GO
EXEC uspAddColumnEmployeesJob

CREATE PROCEDURE uspAddDefaultConstraintVolunteersOID
AS
ALTER TABLE Volunteers
ADD CONSTRAINT DF_OID DEFAULT 6 FOR OID
GO
EXEC uspAddDefaultConstraintVolunteersOID

CREATE PROCEDURE uspDeleteDefaultConstraintVolunteersOID
AS
ALTER TABLE Volunteers
DROP CONSTRAINT DF_OID
GO
EXEC uspDeleteDefaultConstraintVolunteersOID

alter table works_at
drop constraint FK__works_at__CNP__73BA3083

alter table works_at
drop constraint FK__works_at__DID__74AE54BC

CREATE PROCEDURE uspDeletePrimaryKeyEmployeesCNP
AS
ALTER TABLE Employees
DROP CONSTRAINT PK_Employees
GO
EXEC uspDeletePrimaryKeyEmployeesCNP

CREATE PROCEDURE uspAddPrimaryKeyEmployeesCNP
AS
ALTER TABLE Employees
ADD CONSTRAINT PK_Employees primary key (CNP)
GO
EXEC uspAddPrimaryKeyEmployeesCNP

CREATE PROCEDURE uspAddCandidateKeyStages
AS
ALTER TABLE Stages
ADD CONSTRAINT UQ_Stages UNIQUE (stage_name, location)
GO
EXEC uspAddCandidateKeyStages

CREATE PROCEDURE uspDeleteCandidateKeyStages
AS
ALTER TABLE Stages
DROP CONSTRAINT UQ_Stages
GO
EXEC uspDeleteCandidateKeyStages

CREATE PROCEDURE uspDeleteForeignKeyBartendersBID
AS
ALTER TABLE Bartenders
DROP CONSTRAINT FK_Bartenders_BID
GO
EXEC uspDeleteForeignKeyBartendersBID

CREATE PROCEDURE uspAddForeignKeyBartendersBID
AS
ALTER TABLE Bartenders
ADD CONSTRAINT FK_Bartenders_BID FOREIGN KEY (BID) references Bars(BID)
GO
EXEC uspAddForeignKeyBartendersBID

CREATE PROCEDURE uspDropTableGoesTo
AS
DROP TABLE goes_to
GO
EXEC uspDropTableGoesTo

CREATE PROCEDURE uspCreateTableGoesTo
AS
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
GO
EXEC uspCreateTableGoesTo

CREATE TABLE Versions
(
    current_version int
)

DELETE FROM Versions WHERE current_version BETWEEN 0 AND 7
INSERT INTO Versions(current_version) VALUES (0)

CREATE PROCEDURE uspSelectProcedure(@currentVersion int)
    AS
    IF @currentVersion = 1
        EXEC uspModifyColumnOrganiserTasks
    ELSE IF @currentVersion = 2
        EXEC uspDeleteColumnEmployeesJob
    ELSE IF @currentVersion = 3
        EXEC uspAddDefaultConstraintVolunteersOID
    ELSE IF @currentVersion = 4
        EXEC uspDeletePrimaryKeyEmployeesCNP
    ELSE IF @currentVersion = 5
        EXEC uspAddCandidateKeyStages
    ELSE IF @currentVersion = 6
        EXEC uspDeleteForeignKeyBartendersBID
    ELSE IF @currentVersion = 7
        EXEC uspDropTableGoesTo
GO

CREATE PROCEDURE uspSelectUndoProcedure(@currentVersion int)
    AS
    IF @currentVersion = 1
        EXEC uspRevertColumnOrganiserTasks
    ELSE IF @currentVersion = 2
        EXEC uspAddColumnEmployeesJob
    ELSE IF @currentVersion = 3
        EXEC uspDeleteDefaultConstraintVolunteersOID
    ELSE IF @currentVersion = 4
        EXEC uspAddPrimaryKeyEmployeesCNP
    ELSE IF @currentVersion = 5
        EXEC uspDeleteCandidateKeyStages
    ELSE IF @currentVersion = 6
        EXEC uspAddForeignKeyBartendersBID
    ELSE IF @currentVersion = 7
        EXEC uspCreateTableGoesTo
GO

CREATE OR ALTER PROCEDURE uspSelectVersion(@selectedVersion int)
    AS
	BEGIN
		DECLARE @currentVersion INT
		SET @currentVersion = (SELECT current_version
							   FROM Versions)
		IF @selectedVersion < 0 OR @selectedVersion > 7
			BEGIN
				PRINT('The version chosen must be between 0 and 7')
			END
		ELSE
			BEGIN
				IF @selectedVersion > @currentVersion
				BEGIN
					WHILE @selectedVersion > @currentVersion
					BEGIN
						SET @currentVersion = @currentVersion+1
						EXEC uspSelectProcedure @currentVersion
					END
				END
				ELSE
				BEGIN
					WHILE @selectedVersion < @currentVersion
					BEGIN
						BEGIN
							EXEC uspSelectUndoProcedure @currentVersion
						END
						set @currentVersion=@currentVersion-1
					END
				END
				TRUNCATE TABLE Versions
				INSERT INTO Versions(current_version) VALUES (@selectedVersion)
			END
	END
GO
EXEC uspSelectVersion 0

SELECT o.name, c.name, c.max_length, c.precision, c.scale, t.name
FROM SYS.OBjects o INNER JOIN sys.columns c ON o.object_id = c.object_id
  inner join sys.types t on c.system_type_id = t.system_type_id
where type = 'U'

SELECT *
FROM SYS.objects
WHERE TYPE = 'PK'