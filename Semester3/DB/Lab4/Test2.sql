-- ============================================ LAB 4 =========================================================
use untold

-- Table with a single-column primary key and at least one foreign key: Sponsors
-- View with a SELECT statement operating on at least 2 tables: view on Sponsors and Organisers

exec addToTables 'Sponsors'
exec addToTables 'Organisers'

create or alter view vOrganisersAndSponsors as
    select O.first_name, O.last_name, S.company_name
    from Organisers O
    full join Sponsors S on O.OID = S.OID

exec addToViews vOrganisersAndSponsors
exec addToTests 'test2'
exec connectTableToTest 'Organisers', 'test2', 1000, 2
exec connectTableToTest 'Sponsors', 'test2', 1000, 3
exec connectViewToTest 'vOrganisersAndSponsors', 'test2'

create or alter procedure populateTableOrganisers(@rows int) as
    while @rows > 0
        begin
            insert into Organisers(OID, first_name, last_name, tasks)
            values(@rows, replace(concat('first_name_', str(@rows)), ' ', ''), replace(concat('last_name_', str(@rows)), ' ',''),
                   replace(concat('task_', str(@rows)), ' ', ''))
            set @rows = @rows - 1
        end
go

create or alter procedure populateTableSponsors(@rows int) as
    while @rows > 0
        begin
            insert into Sponsors(SID, company_name, amount_of_money, OID)
            values(@rows, replace(concat('company_name_', str(@rows)), ' ', ''), (rand()*89 + 10)*100, @rows)
            set @rows = @rows - 1
        end
go

exec runTest 'test2'