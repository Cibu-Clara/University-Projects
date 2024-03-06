$(function () {
    const url = new URL(window.location.href);
    const parameterValue = url.searchParams.get('skill');
    const dataTable = $("#developersTable");
    function getData(){
        $.ajax({
            url: 'http://localhost:8080/SoftwareDeveloperProjects_JSP_war_exploded/filter-developers',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                dataTable.empty();
                const header = "<thead><th>ID</th><th>Name</th><th>Age</th><th>Skills</th></thead>";
                dataTable.append(header);

                data.forEach(function (item) {
                    if (item.skills.indexOf(parameterValue +" ") !== -1 || (item.skills.indexOf(parameterValue) !== -1 && item.skills.indexOf(parameterValue) === item.skills.length - parameterValue.length)){
                        const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.name + "</td><td>" + item.age + "</td><td>" + item.skills + "</td></tr>";
                        dataTable.append(row);
                    }
                });
            },
            error: function (){
                console.log('Failed to filter developers by skill!');
            }
        });
    }
    getData();
});