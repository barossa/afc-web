(function ($) {

    $(document).ready(function () {
        $('.command').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/controller?command=" + $(this).val())
        })
        $('.href').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/" + $(this).val())
        })
        /*DISABLE BACK BUTTON*/
        window.history.pushState(null, "", window.location.href);
        window.onpopstate = function () {
            window.history.pushState(null, "", window.location.href);
        };

        /*DISABLE BACK BUTTON*/
    });
})(jQuery)
