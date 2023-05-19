<?php

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

$conn = new mysqli("localhost", "root", "", "db_hotel_chain");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
    exit();
}

// Select data from the database
function select_data($conn, $table_name)
{
    $sql = "SELECT * FROM $table_name";
    $result = $conn->query($sql);

    $data = array();
    while ($row = $result->fetch_assoc()) // retrieves each row as an associative array
    {
        $data[] = $row; // appends the fetched row to the array
    }
    return $data;
}

// Insert data into the database
function insert_data($conn, $table_name, $data)
{
    $columns = implode(", ", array_keys($data));
    $values = "'" . implode("', '", array_values($data)) . "'";
    $sql = "INSERT INTO $table_name($columns) VALUES ($values)";

    if ($conn->query($sql) === true)
        return "Data inserted successfully";
    else
        return "Error: " . $sql . "<br>" . $conn->error;
}

// Delete data from the database
function delete_data($conn, $table_name, $id)
{
    $sql = "DELETE FROM $table_name WHERE id=$id";

    if ($conn->query($sql) === true)
        return "Data deleted successfully";
    else
        return "Error deleting record: " . $conn->error;
}

// Update data in the database
function update_data($conn, $table_name, $id, $data)
{
    $values = "";
    foreach ($data as $key => $value) {
        $values .= "$key='$value', ";
    }
    $values = rtrim($values, ", ");
    $sql = "UPDATE $table_name SET $values WHERE id=$id";

    if ($conn->query($sql) === true)
        return "Data updated successfully";
    else
        return "Error updating record: " . $conn->error;
}

if (isset($_GET['action']) && isset($_GET['table_name'])) {
    if ($_GET['action'] == 'select_data') {
        echo json_encode(select_data($conn, $_GET['table_name']));
    }
}

if (isset($_POST['action']) && isset($_POST['table_name']) && isset($_POST['data'])) {
    if ($_POST['action'] == 'insert_data') {
        echo json_encode(insert_data($conn, $_POST['table_name'], $_POST['data']));
    }
}

if (isset($_POST['action']) && isset($_POST['table_name']) && isset($_POST['id'])) {
    if ($_POST['action'] == 'delete_data') {
        echo json_encode(delete_data($conn, $_POST['table_name'], $_POST['id']));
    }
}

if (isset($_POST['action']) && isset($_POST['table_name']) && isset($_POST['id']) && isset($_POST['data'])) {
    if ($_POST['action'] == 'update_data') {
        echo json_encode(update_data($conn, $_POST['table_name'], $_POST['id'], $_POST['data']));
    }
}

mysqli_close($conn);