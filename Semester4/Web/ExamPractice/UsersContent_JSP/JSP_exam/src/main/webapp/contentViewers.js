
function getData() {
    $.ajax({
        async:true,
        url: "http://localhost:8081/JSP_exam_war_exploded/display-4",
        type:"GET",
        dataType: "json",
        success: function (result) {
            animateList(result);
        },
        error: function () {
            console.log("Fail");
        }
    });
}

function animateList(data) {
    var tableBody = document.querySelector('#myTable tbody');
    var currentIndex = 0;

    function displayNextObject() {
        if (currentIndex >= data.length) {
            currentIndex = 0;
        }

        var obj = data[currentIndex];
        var row = document.createElement('tr');
        row.innerHTML = `
      <td>${obj.Id}</td>
      <td>${obj.user_id}</td>
      <td>${obj.date}</td>
      <td>${obj.title}</td>
      <td>${obj.description}</td>
      <td>${obj.url}</td>
    `;
        tableBody.appendChild(row);

        currentIndex++;

        setTimeout(function() {
            row.remove(); // Remove the displayed object after a delay
            displayNextObject(); // Call recursively to display the next object
        }, 2000); // Delay in milliseconds before removing the object
    }

    displayNextObject();
}

getData()


