(function ($) {

    $(document).ready(function () {
        $('.href').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/" + $(this).val())
        })
    })

})(jQuery);