$(function (){
    const tableName = "website";
    const dataTable = $("#data-table");

    // Retrieve data from the database
    function showTable() {
        $.ajax({
            async: true,
            url:"http://localhost/wp/WebsiteDocuments_PHP/server/index.php",
            type: "GET",
            data: {action: "select_data", table_name: tableName},
            dataType: "json",
            success: function (result) {
                dataTable.empty();
                const header = "<thead><th>ID</th><th>URL</th><th>No. of documents</th></thead>";
                dataTable.append(header);

                result.forEach(function (item) {
                    const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.url + "</td><td>" +
                        item.document_count + "</td></tr>";
                    dataTable.append(row);
                });
            },
            error: function () {
                console.log("Fail");
            }
        });
    }
    showTable();
});
