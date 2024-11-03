$(document).ready(function() {
    maxLengthInputNumber($("#day"), 2);
    maxLengthInputNumber($("#year"), 4);
});

function maxLengthInputNumber(element, maxLength) {
    element.on('input', function(event) {
        if (element.val().length > maxLength) { // Giới hạn độ dài chuỗi là 5 ký tự
            element.val(Number.parseInt(this.value.slice(0, maxLength))); // Nếu vượt quá 5 ký tự, cắt bớt đi
        }
    });
}