$(function () {
    const tableName = "booking";
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

                const header = "<thead><th>ID</th><th>Client ID</th><th>Room ID</th><th>CheckIn</th><th>CheckOut</th></thead>";
                dataTable.append(header);

                result.forEach(function (item) {
                    const row = "<tr class=\"table_row\"><td>" + item.id + "</td><td>" + item.client_id +
                        "</td><td>" + item.room_id + "</td><td>" + item.checkin + "</td><td>" + item.checkout + "</td></tr>";
                    dataTable.append(row);
                });

                let rows = document.querySelectorAll(".table_row");
                rows.forEach(function (item) {
                    item.addEventListener("click", function () {
                        document.getElementById("id-input").value = item.getElementsByTagName("td")[0].innerHTML;
                        document.getElementById("client-id-input").value = item.getElementsByTagName("td")[1].innerHTML;
                        document.getElementById("room-id-input").value = item.getElementsByTagName("td")[2].innerHTML;
                        document.getElementById("checkin-input").value = item.getElementsByTagName("td")[3].innerHTML;
                        document.getElementById("checkout-input").value = item.getElementsByTagName("td")[4].innerHTML;
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

    function isClientIdValid(clientId, callback) {
        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "GET",
            data: {action: "select_data", table_name: "client"},
            dataType: "json",
            success: function (result) {
                const clientIds = result.map(client => client.id);
                const isValid = clientIds.includes(clientId);
                callback(isValid);
            },
            error: function () {
                console.log("Fail");
                callback(false);
            }
        });
    }

    function isRoomIdValid(roomId, callback) {
        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "GET",
            data: {action: "select_data", table_name: "room"},
            dataType: "json",
            success: function (result) {
                const roomIds = result.map(room => room.id);
                const isValid = roomIds.includes(roomId);
                callback(isValid);
            },
            error: function () {
                console.log("Fail");
                callback(false);
            }
        });
    }

        showTable();

        // Insert data into the database
        $("#insert-button").on("click", function () {
            const client_id = $("#client-id-input")
            const room_id = $("#room-id-input")
            const checkin = $("#checkin-input")
            const checkout = $("#checkout-input")

            if (client_id.val() === "" || room_id.val() === "" || checkin.val() === "" || checkout.val() === "") {
                alert("Invalid input");
                return;
            }

            const data = {
                client_id: client_id.val(),
                room_id: room_id.val(),
                checkin: checkin.val(),
                checkout: checkout.val()
            };

            isClientIdValid(client_id.val(), function (isValid) {
                if (!isValid) {
                    alert("Client ID does not exist in the client table");
                    return;
                }

                    isRoomIdValid(room_id.val(), function (isValid) {
                        if (!isValid) {
                            alert("Roomd ID does not exist in the client table");
                            return;
                        }

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
                    dataType: "text",
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
            const client_id = $("#client-id-input");
            const room_id = $("#room-id-input");
            const checkin = $("#checkin-input");
            const checkout = $("#checkout-input");

            if (client_id.val() === "" || room_id.val() === "" || checkin.val() === "" || checkout.val() === "") {
                alert("Invalid input");
                return;
            }

            const id = $("#id-input").val();
            if (id === "") {
                alert("No selected element");
                return;
            }

            const data = {
                client_id: client_id.val(),
                room_id: room_id.val(),
                checkin: checkin.val(),
                checkout: checkout.val()
            };

            $.ajax({
                async: true,
                url: "http://localhost/wp/Lab6_PHP/server/index.php",
                type: "POST",
                data: {action: "update_data", table_name: tableName, id: id, data: data},
                dataType: "text",
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

        $("#clear-button").on("click", clear);
    });