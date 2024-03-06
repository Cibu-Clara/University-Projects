<?php

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

$conn = new mysqli("localhost", "root", "", "db_website_documents", 3307);

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
    if ($table_name == 'website')
        $sql = "SELECT website.id, website.url, COUNT(document.id) AS document_count
                FROM website
                LEFT JOIN document ON website.id = document.id_website
                GROUP BY website.id";
    else
        $sql = "SELECT * from $table_name";
    $result = $conn->query($sql);

    $data = array();
    while ($row = $result->fetch_assoc()) // retrieves each row as an associative array
    {
        $data[] = $row;
    }
    return $data;
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

    if($conn->query($sql) === true)
        return "Data updated successfully";
    else
        return "Error updating record: " . $conn->error;
}
// Find documents with exactly 3 keyword matches
function find_documents($conn, $keywords)
{
    // Split the keywords into an array
    $keywordArray = explode(" ", $keywords);

    // Generate the SQL query dynamically based on the number of keywords
    $sql = "SELECT * FROM Document WHERE (";
    foreach ($keywordArray as $keyword) {
        $sql .= "keyword1='$keyword' OR keyword2='$keyword' OR keyword3='$keyword' OR ";
        $sql .= "keyword4='$keyword' OR keyword5='$keyword' OR ";
    }
    $sql = rtrim($sql, " OR "); // Remove the trailing " OR "
    $sql .= ")"; // Filter for exactly 3 keyword matches

    $result = $conn->query($sql);

    $matchingDocuments = array();
    while ($row = $result->fetch_assoc()) {
        $documentKeywords = array($row['keyword1'], $row['keyword2'], $row['keyword3'], $row['keyword4'], $row['keyword5']);
        $matches = 0;
        foreach ($keywordArray as $keyword) {
            if (in_array($keyword, $documentKeywords)) {
                $matches++;
            }
        }
        if ($matches === 3) {
            $matchingDocuments[] = $row;
        }
    }

    return $matchingDocuments;
}

if (isset($_GET['action']) && isset($_GET['table_name'])) {
    if($_GET['action'] == 'select_data') {
        echo json_encode(select_data($conn, $_GET['table_name']));
    }
}

if (isset($_POST['action']) && isset($_POST['table_name']) && isset($_POST['id']) && isset($_POST['data'])) {
    if ($_POST['action'] == 'update_data') {
        echo json_encode(update_data($conn, $_POST['table_name'], $_POST['id'], $_POST['data']));
    }
}

if (isset($_POST['action']) && isset($_POST['keywords'])) {
    if ($_POST['action'] == 'find_documents') {
        echo json_encode(find_documents($conn, $_POST['keywords']));
    }
}

mysqli_close($conn);