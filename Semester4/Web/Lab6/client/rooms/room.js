let offset = 0;
let size;
let secondOffset = 0;
let secondOffsetArray = [];
$(function () {
    const tableName = "room";
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
                size = result.length;
                dataTable.empty();

                const header = "<thead><th>ID</th><th>Type</th><th>Category</th><th>Price</th><th>Hotel ID</th></thead>";
                dataTable.append(header);

                const search = $("#filter-input");

                if(search.val() === "") {
                    for (let i = 0; i < 4; ++i) {
                        if (offset + i < size) {
                            const row = "<tr class=\"table_row\"><td>" + result[offset + i].id + "</td><td>" +
                                result[offset + i].type + "</td><td>" + result[offset + i].category + "</td><td>" +
                                result[offset + i].price + "</td><td>" + result[offset + i].hotel_id + "</td></tr>";
                            dataTable.append(row);
                        }
                    }
                }
                else {
                    let  i = 0;
                    while (i < 4) {
                        if (secondOffset + i < size) {
                            let row = "<tr class=\"table_row\"><td>" + result[secondOffset + i].id + "</td><td>" +
                                result[secondOffset + i].type + "</td><td>" + result[secondOffset + i].category + "</td><td>" +
                                result[secondOffset + i].price + "</td><td>" + result[secondOffset + i].hotel_id + "</td></tr>";
                            if (row.includes(search.val())) {
                                dataTable.append(row);
                                ++i;
                            } else {
                                ++secondOffset;
                            }
                        }   else
                                i = 4;
                        }
                        if (secondOffset + i < size)
                            secondOffsetArray.push(secondOffset + i);
                    }

                let rows = document.querySelectorAll(".table_row");
                rows.forEach(function (item) {
                    item.addEventListener("click", function () {
                        document.getElementById("id-input").value = item.getElementsByTagName("td")[0].innerHTML;
                        document.getElementById("type-input").value = item.getElementsByTagName("td")[1].innerHTML;
                        document.getElementById("category-input").value = item.getElementsByTagName("td")[2].innerHTML;
                        document.getElementById("price-input").value = item.getElementsByTagName("td")[3].innerHTML;
                        document.getElementById("hotel-id-input").value = item.getElementsByTagName("td")[4].innerHTML;
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

    function filter() {
        offset = 0;
        secondOffset = 0;
        secondOffsetArray = [];
        showTable();
    }

    function isHotelIdValid(hotelId, callback) {
        $.ajax({
            async: true,
            url: "http://localhost/wp/Lab6_PHP/server/index.php",
            type: "GET",
            data: {action: "select_data", table_name: "hotel"},
            dataType: "json",
            success: function (result) {
                const hotelIds = result.map(hotel => hotel.id);
                const isValid = hotelIds.includes(hotelId);
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
        const type = $("#type-input");
        const category = $("#category-input");
        const price = $("#price-input");
        const hotel_id = $("#hotel-id-input");

        if (category.val() === "" || type.val() === "" || price.val() === "" || hotel_id.val() === "") {
            alert("Invalid input");
            return;
        }

        const data = {
            type: type.val(),
            category: category.val(),
            price: price.val(),
            hotel_id: hotel_id.val()
        };

        // Check if the hotel ID exists in the hotel table
        isHotelIdValid(hotel_id.val(), function (isValid) {
            if (!isValid) {
                alert("Hotel ID does not exist in the hotel table");
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
        const type = $("#type-input");
        const category = $("#category-input");
        const price = $("#price-input");
        const hotel_id = $("#hotel-id-input");

        if (category.val() === "" || type.val() === "" || price.val() === "" || hotel_id.val() === "") {
            alert("Invalid input");
            return;
        }

        const id = $("#id-input").val();
        if (id === "") {
            alert("No selected element");
            return;
        }

        const data = {
            category: category.val(), type: type.val(), price: price.val(), hotel_id: hotel_id.val()
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

        $("#next-page").on("click", function () {
            const search = $("#filter-input").val();
            if (search === "") {
                if (offset + 4 < size) {
                    offset += 4;
                }
            } else {
                if (secondOffsetArray.length === 0) {
                    secondOffsetArray = [];
                    secondOffset = 0;
                } else {
                    secondOffset = secondOffsetArray.at(secondOffsetArray.length - 1);
                }
                offset = 0;
            }
            showTable();
        });

        $("#prev-page").on("click", function () {
            const search = $("#filter-input").val();
            if (search === "") {
                if (offset >= 4) {
                    offset -= 4;
                }
            } else {
                console.log(secondOffsetArray);
                if (secondOffsetArray.length === 1) {
                    secondOffsetArray = [];
                    secondOffset = 0;
                } else {
                    secondOffset = secondOffsetArray.at(secondOffsetArray.length - 2);
                }
                offset = 0;
            }
            showTable();
        });

        $("#filter-button").on("click", filter);
    }
);
