-- ============================================ LAB 4 =========================================================
use untold

-- Table with a single-column primary key and no foreign keys: Stages
-- View with a SELECT statement operating on one table: view on Stages

exec addToTables 'Stages'

create or alter view vStagesNameAndLocation as
    select stage_name, location from Stages

exec addToViews 'vStagesNameAndLocation'
exec addToTests 'test1'
exec connectTableToTest 'Stages', 'test1', 1000, 1
exec connectViewToTest 'vStagesNameAndLocation', test1

create or alter procedure populateTableStages(@rows int) as
    while @rows > 0
        begin
            insert into Stages(SID, stage_name, location)
            values (@rows, replace(concat('stage_name_', str(@rows)), ' ', ''), replace(concat('location_', str(@rows)), ' ', ''))
            set @rows = @rows - 1
        end
go

exec runTest test1

delete from TestRuns
delete from TestRunTables
delete from TestRunViews
delete from TestTables
delete from TestViews
delete from Tables
delete from Views
delete from Tests
