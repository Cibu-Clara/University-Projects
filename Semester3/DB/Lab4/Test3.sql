-- ============================================ LAB 4 =========================================================
use UNTOLD

-- Table with a multicolumn primary key: works_at
-- View with a SELECT statement that has GROUP BY clause and operates on at least 2 tables: view on works_at and Departments

exec addToTables 'works_at'
exec addToTables 'Departments'
create or alter view vVolunteersWorkAtDepartments as
    select W.CNP, D.name
    from works_at W
    full join Departments D on D.DID = W.DID
    group by W.CNP, D.name

exec addToViews 'vVolunteersWorkAtDepartments'
exec addToTests 'test3'
exec connectTableToTest 'Departments', 'test3', 1000, 4
exec connectTableToTest 'works_at', 'test3', 1000, 5
exec connectViewToTest 'vVolunteersWorkAtDepartments', 'test3'

create or alter procedure populateTableDepartments (@rows int) as
    while @rows > 0
        begin
            insert into Departments(DID, name, tasks)
            values (@rows, replace(concat('department_name_', str(@rows)), ' ', ''), replace(concat('task_', str(@rows)), ' ', ''))
            set @rows = @rows - 1
        end
go

create or alter procedure populateTableworks_at (@rows int) as
    while @rows > 0
        begin
            insert into works_at(CNP, DID)
            values ((rand()*89 + 10)*10000, @rows)
            set @rows = @rows - 1
        end
go

exec runTest 'test3'