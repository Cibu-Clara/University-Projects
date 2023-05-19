$(function () {
    const tableName = "client";
    const dataTable = $("#data-table");

    // Retrieve data from the database
    function showTable() {
        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "GET",
            data: {action: "select_data", table_name: tableName},
            dataType: "json",
            success: function (result) {
                dataTable.empty();

                const header = "<thead><th>ID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Phone Number</th></thead>";
                dataTable.append(header);

                result.forEach(function (item) {
                    const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.first_name +
                        "</td><td>" + item.last_name + "</td><td>" + item.email + "</td><td>" + item.phone_number + "</td></tr>";
                    dataTable.append(row);
                });

                let rows = document.querySelectorAll(".table_row");
                rows.forEach(function (item) {
                    item.addEventListener("click", function () {
                        document.getElementById("id-input").value = item.getElementsByTagName("td")[0].innerHTML;
                        document.getElementById("first-name-input").value = item.getElementsByTagName("td")[1].innerHTML;
                        document.getElementById("last-name-input").value = item.getElementsByTagName("td")[2].innerHTML;
                        document.getElementById("email-input").value = item.getElementsByTagName("td")[3].innerHTML;
                        document.getElementById("phone-input").value = item.getElementsByTagName("td")[4].innerHTML;
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

    // Insert data into the database
    $("#insert-button").on("click", function () {
        const first_name = $("#first-name-input")
        const last_name = $("#last-name-input")
        const email = $("#email-input")
        const phone_number = $("#phone-input")

        if (first_name.val() === "" || last_name.val() === "" || email.val() === "" || phone_number.val() === "") {
            alert("Invalid input");
            return;
        }

        const data = {
            first_name: first_name.val(),
            last_name: last_name.val(),
            email: email.val(),
            phone_number: phone_number.val()
        };

        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "POST",
            dataType: 'text',
            data: {action: "insert_data", table_name: tableName, data: data},
            success: function (result) {
                alert(result);
                showTable();
                clear();
            },
            error: function () {
                console.error("ERROR");
            }
        });
    });

    // Delete data from the database
    $("#delete-button").on("click", function () {
        const id = $("#id-input").val();
        if (id === "") {
            alert("No selected element");
            return;
        }
        // Ask for confirmation
        const confirmation = confirm("Are you sure you want to delete this item?");
        if (confirmation) {
            $.ajax({
                async: true,
                url: "http://localhost/wp/Lab6_PHP/server/index.php",
                type: "POST",
                data: {action: "delete_data", table_name: tableName, id: id},
                dataType: 'text',
                success: function (result) {
                    alert(result);
                    showTable();
                    clear();
                }
            });
        }
    });

    // Update data in the database
    $("#update-button").on("click", function () {
        const first_name = $("#first-name-input")
        const last_name = $("#last-name-input")
        const email = $("#email-input")
        const phone_number = $("#phone-input")

        if (first_name.val() === "" || last_name.val() === "" || email.val() === "" || phone_number.val() === "") {
            alert("Invalid input");
            return;
        }

        const id = $("#id-input").val();

        if (id === "") {
            alert("No selected element");
            return;
        }

        const data = {
            first_name: first_name.val(),
            last_name: last_name.val(),
            email: email.val(),
            phone_number: phone_number.val()
        };

        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "POST",
            data: {action: "update_data", table_name: tableName, id: id, data: data},
            dataType: 'text',
            success: function (result) {
                alert(result);
                showTable();
                clear();
            },
            error: function () {
                console.log("ERROR");
            }
        });
    });

    $("#clear-button").on("click", clear);

});