$(document).ready(function () {
    $('#lightSlider').lightSlider({
        slideMargin: 10,
        slideWidth: 545,
        mode: 'slide',
        useCSS: true,
        cssEasing: 'ease',
        item: 2,
        auto: true,
        loop: true,
        pauseOnHover: true,
        slideMove: 1,
        easing: 'cubic-bezier(0.25, 0, 0.25, 1)',
        speed: 600,
        enableTouch: true,
        responsive: [
            {
                breakpoint: 800,
                settings: {
                    item: 1,
                    slideMove: 1,
                    slideMargin: 6,
                }
            },
            {
                breakpoint: 520,
                settings: {
                    item: 1,
                    slideMove: 1
                }
            }
        ]
    });
});