$(function () {
    var jsson = [];

    $("#submit-button").on("click", function () {
        $.ajax({
            async: true,
            url: "http://localhost:8081/JSP_exam_war_exploded/insert-multiple",
            type: "POST",
            data: JSON.stringify(jsson),
            success: function () {
            },
            error: function () {
                console.error("ERROR");

            }
        });
    });
    $("#insert-button").on("click", function () {
        const title = $("#title");
        const description = $("#description");
        const url = $("#url");

        var newObj = {
            title: title.val(),
            description: description.val(),
            url: url.val()
        };


        jsson.push(newObj);
        clear()
        alert("pushed")
    });

    function clear() {
        let inputs = document.querySelectorAll("input");
        inputs.forEach(function (item) {
            item.value = "";
        });
    }
});