$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

function onError() {

}

function onSuccess(data, status) {
    console.log(data);

    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.createdTime,
        data.contents, data.question.id, data.id);

    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}

$("a.link-delete-article button[type='submit']").click(deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault(); // problem

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest(".article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
