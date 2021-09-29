(function ($) {
    "use strict";

    /*==================================================================
    [ Validate ]*/
    let input = $('.input');

    $(document).ready(function () {
        $('#searchForm').on('submit', function () {
            /*let search = $('#searchRequest').attr('value');*/
            let validationResult = validate();
            alert(validationResult);
            if (validationResult) {
                searchAction();
            }
            return false;
        });
    });

    function searchAction() {
        let filtersForm = $('#filtersForm').serialize();
        let searchForm = $('#searchForm').serialize();
        $.extend(filtersForm, searchForm);

        var xhr = $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: $('#filtersForm, #searchForm').serialize(),
            /*dataType: "xml/html",*/

            success: function (response) {
            },

            error: function (response) {
            }
        });
    }

    function validate() {
        let search = $("#searchRequest")
        let min = $("#rangeMin");
        let max = $("#rangeMax");

        let flag = search.val().match("^(?=.*[A-Za-zА-Яа-я]$)([A-Za-zА-Яа-я]+_?)+$") != null;

        if(flag){
            let minVal = $(min).val();
            let maxVal = $(max).val();
            flag = minVal.match("^\\d*$") != null && maxVal.match("^\\d*$") != null;
        }

        let searchMaxLength = 30;
        if (flag) {
            let length = search.length;
            return length <= searchMaxLength && length > 0;
        } else {
            return false; // don't meet regex
        }

    }

})(jQuery);