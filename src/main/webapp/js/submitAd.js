(function ($) {

    var plugin = $('#file-input').data("maxFileSize=\'5120\'\n'" +
        "maxTotalFileCount=\'5\'");

    $(function(){

        $("#categoriesDropdown li a").on("click", function(){
            let bt = $("#chooseCtButton");
            bt.text($(this).text());
            bt.val($(this).text());

        });

        $("#regionsDropdown li a").on("click", function(){
            let bt = $("#chooseRgButton");
            bt.text($(this).text());
            bt.val($(this).text());

        });

    });

})(jQuery);