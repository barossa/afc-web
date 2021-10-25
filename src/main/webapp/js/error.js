(function ($) {

    $(document).ready(function () {
        $('.href').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/" + $(this).val())
        })
        /*DISABLE BACK BUTTON*/
        $(document).ready(function() {
            window.history.pushState(null, "", window.location.href);
            window.onpopstate = function() {
                window.history.pushState(null, "", window.location.href);
            };
        });
        /*DISABLE BACK BUTTON*/
    })

})(jQuery);