(function ($) {

    $(document).ready(function () {
        bindActions();
    });

    function bindActions() {
        $('#eng_bt').on('click', function (){
            sendLocale("en_US")
        });

        $('#rus_bt').on('click', function (){
            sendLocale("ru_RU")
        });
    }

    function sendLocale(locale) {
        let xhr = $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: "command=change_locale&locale=" + locale,

            success: function (response) {
                document.body.innerHTML = response;
                /*bindActions();*/
                document.location.reload();
            }
        });
    }

})(jQuery);