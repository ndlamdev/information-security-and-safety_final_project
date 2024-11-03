$(document).ready(function () {
    for (let year = 2023; year <= 2200; year++) {
        $("#input-product-year").html($("#input-product-year").html() +
            `<option value="${year}">${year}</option>`
        );
        $("#input-category-year").html($("#input-category-year").html() +
            `<option value="${year}">${year}</option>`
        );
    }

    for (let month = 1; month <= 12; month++) {
        $("#input-product-month").html($("#input-product-month").html() +
            `<option value="${month}">Tháng ${month}</option>`
        );
    }

    getProducts();

    const labelMonth = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
    const labelQuarter = ['Quý 1', 'Quý 2', 'Quý 3', 'Quý 4'];

    const chartProduct = createChart($("#chart-product"), 'line');
    const chartCategory = createChart($("#chart-category"), 'bar');
    let colorCategory = [];
    let colorCategoryGroup = [];

    $(".number-list-by-category").change(function () {
        const dataSend = {
            "action": "number-list-by-category",
            "category": $("#input-category").val(),
            "quarter": $("#input-category-quarter").val(),
            "year": $("#input-category-year").val(),
        }


        $.ajax({
            url: "dashboard",
            dataType: "json",
            data: dataSend,
            method: "POST",
            success: function (data) {
                const arrayData = formatDataToArray(data.data);
                let dataset;
                if (dataSend.category == 0) {
                    colorCategory = createColor(arrayData, colorCategory);
                    dataset = formatDataForChart(arrayData, colorCategory);
                } else {
                    colorCategoryGroup = createColor(arrayData, colorCategoryGroup);
                    dataset = formatDataForChart(arrayData, colorCategoryGroup);
                }
                if (dataSend.quarter == 0) chartSell(chartCategory, labelMonth, dataset);
                else chartSell(chartCategory, labelQuarter, dataset);
            },
            error: function (jqXHR, textStatus, errorTh) {
                console.error("Lỗi");
            }
        });
    });

    $(".number-list-by-product").change(function () {
        const dataSend = {
            "action": "number-list-by-product",
            "product-id": $("#input-product").val(),
            "month": $("#input-product-month").val(),
            "year": $("#input-product-year").val(),
        }

        $.ajax({
            url: "dashboard",
            dataType: "json",
            data: dataSend,
            method: "POST",
            success: function (data) {
                updateChartProduct(chartProduct, $("#input-product option:selected").text(), data.lengthOfMonth, data.data);
            },
            error: function (jqXHR, textStatus, errorTh) {
                console.error("Lỗi");
            }
        });
    });

    $("#input-category").change();
});

function chartSell(chart, label, data) {
    chart.data.datasets = data;
    chart.data.labels = label;
    chart.update();
}

function getProducts() {
    $.ajax({
        url: "dashboard",
        dataType: 'json',
        method: 'GET',
        data: {
            action: "products",
        },
        success: function (data) {
            let html = ``;
            const products = data.products;
            products.forEach(function (product) {
                    const optionProduct = `<option value="${product.id}">${product.name}</option>`
                    html += optionProduct;
                }
            )

            $("#input-product").html(html);
            $("#input-product").change();
            $("#input-product").select2();
        }
    })
}

function formatDataToArray(data) {
    return Object.keys(data).map(key => ({
        label: key,
        data: data[key].map(entry => entry.quantity),
    }));
}

function formatDataForChart(data, colors) {
    let index = 0;
    data.map(function (entry) {
        entry['backgroundColor'] = colors[index++];
    });

    return data;
}

function createColor(data, colors) {
    if (colors.length === data.length) return colors
    data.map(function (entry) {
        colors.push(randomColor());
    });

    return colors;
}

function createChart(ctx, type) {
    return new Chart(ctx, {
        type: type,
        data: {
            labels: [],
            datasets: [],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function randomColor() {
    // Tạo một giá trị ngẫu nhiên cho mỗi thành phần màu (R, G, B)
    const randomComponent = () => Math.floor(Math.random() * 256);

    // Tạo một màu RGB ngẫu nhiên
    const color = `rgb(${randomComponent()}, ${randomComponent()}, ${randomComponent()})`;

    return color;
}

function updateChartProduct(chart, productName, lengthOfMonth, data) {
    let label = [];
    for (let day = 0; day < lengthOfMonth; day++) label[day] = "Ngày " + (day + 1);
    const dataset = [{
        label: productName,
        data: data.map(entry => entry.quantity)
    }];
    chart.type = 'line';
    chart.data.labels = label;
    chart.data.datasets = dataset;
    chart.update();
}




