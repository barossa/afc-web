(function ($) {

    $(document).ready(function () {
        bindActions();
    });

    $(document).ready(
        $('.reqBtn').on('click', function () {
            let data = $(this).attr('name') + "=" + $(this).val();
            alert("That works!" + data);
            $.ajax({
                url: "/Ads_from_Chest_war_exploded/controller",
                type: "POST",
                data: data,

                success(response){
                    $(document.body).html($(response.document.body).html());
                }
            });

        })
    )

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