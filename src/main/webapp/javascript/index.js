/**main**/
$(document).ready(function () {
    showBtnMoveSlide();
});

function showBtnMoveSlide() {
    const numberOfSlides = $('.container-slider .carousel .carousel-inner .slide').length;
    if (numberOfSlides > 1) return;
    $('.container-slider .carousel .carousel-inner .pre-next-slide').addClass('d-none');
    $('.container-slider .carousel .carousel-inner .slick-dots ').addClass('d-none');
}
