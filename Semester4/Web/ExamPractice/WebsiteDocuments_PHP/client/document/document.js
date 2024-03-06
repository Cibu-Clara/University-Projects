$(function (){
    const tableName = "document";
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
                const header = "<thead><th>ID</th><th>Name</th><th>Keyword 1</th><th>Keyword 2</th>" +
                    "<th>Keyword 3</th><th>Keyword 4</th><th>Keyword 5</th><th>Website ID</th></thead>";
                dataTable.append(header);

                result.forEach(function (item) {
                    const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.name + "</td><td>" +
                        item.keyword1 + "</td><td>" + item.keyword2 + "</td><td>" + item.keyword3 + "</td><td>" +
                        item.keyword4 + "</td><td>" + item.keyword5 + "</td><td>" + item.id_website + "</td></tr>";
                    dataTable.append(row);
                });

                let rows = document.querySelectorAll(".table_row");
                rows.forEach(function (item) {
                    item.addEventListener("click", function () {
                        document.getElementById("id-input").value = item.getElementsByTagName("td")[0].innerHTML;
                        document.getElementById("keyword-1-input").value = item.getElementsByTagName("td")[2].innerHTML;
                        document.getElementById("keyword-2-input").value = item.getElementsByTagName("td")[3].innerHTML;
                        document.getElementById("keyword-3-input").value = item.getElementsByTagName("td")[4].innerHTML;
                        document.getElementById("keyword-4-input").value = item.getElementsByTagName("td")[5].innerHTML;
                        document.getElementById("keyword-5-input").value = item.getElementsByTagName("td")[6].innerHTML;
                    });
                });
            },
            error: function () {
                console.log("Fail");
            }
        });
    }

    function clear() {
        let inputs = document.querySelectorAll("input");
        inputs.forEach(function (item) {
            item.value = "";
        });
    }

    showTable();

    // Update data in the database
    $("#update-button").on("click", function () {
        const keyword1 = $("#keyword-1-input");
        const keyword2 = $("#keyword-2-input");
        const keyword3 = $("#keyword-3-input");
        const keyword4 = $("#keyword-4-input");
        const keyword5 = $("#keyword-5-input");

        if (keyword1.val() === "" || keyword2.val() === "" || keyword3.val() === "" || keyword4.val() === "" ||
        keyword5.val() === "") {
            alert("Invalid input");
            return;
        }

        const id = $("#id-input").val();
        if (id === "") {
            alert("No selected element");
            return;
        }

        const data = {
            keyword1: keyword1.val(),
            keyword2: keyword2.val(),
            keyword3: keyword3.val(),
            keyword4: keyword4.val(),
            keyword5: keyword5.val(),
        };

        $.ajax({
            async: true,
            url: "http://localhost/wp/WebsiteDocuments_PHP/server/index.php",
            type: "POST",
            data: {action: "update_data", table_name: tableName, id: id, data: data},
            dataType: "text",
            success: function (result) {
                showTable();
                clear();
            },
            error: function () {
                console.error("ERROR");
            }
        });
    });

    const searchResultsTable = $("#search-results-table");
    // Perform search for documents
    $("#search-button").on("click", function () {
        const keywords = $("#search-keywords-input").val();

        if (keywords === "") {
            alert("Please enter keywords to search.");
            return;
        }

        $.ajax({
            async: true,
            url: "http://localhost/wp/WebsiteDocuments_PHP/server/index.php",
            type: "POST",
            data: { action: "find_documents", keywords: keywords },
            dataType: "json",
            success: function (result) {
                searchResultsTable.empty();
                const header = "<thead><th>ID</th><th>Name</th><th>Keyword 1</th><th>Keyword 2</th>" +
                    "<th>Keyword 3</th><th>Keyword 4</th><th>Keyword 5</th><th>Website ID</th></thead>";
                searchResultsTable.append(header);

                result.forEach(function (item) {
                    const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.name + "</td><td>" +
                        item.keyword1 + "</td><td>" + item.keyword2 + "</td><td>" + item.keyword3 + "</td><td>" +
                        item.keyword4 + "</td><td>" + item.keyword5 + "</td><td>" + item.id_website + "</td></tr>";
                    searchResultsTable.append(row);
                });
            },
            error: function () {
                console.error("ERROR");
            }
        });
    });

    $("#clear-button").on("click", clear);
});
